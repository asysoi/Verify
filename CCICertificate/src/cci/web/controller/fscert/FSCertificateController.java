package cci.web.controller.fscert;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import cci.config.fscert.ExportFSCertConfig;
import cci.model.Client;
import cci.model.Employee;
import cci.model.fscert.Expert;
import cci.model.fscert.Exporter;
import cci.model.fscert.FSCertificate;
import cci.model.fscert.Producer;
import cci.model.fscert.Signer;
import cci.repository.SQLBuilder;
import cci.repository.fscert.SQLBuilderFSCertificate;
import cci.service.FieldType;
import cci.service.Filter;
import cci.service.cert.CertFilter;
import cci.service.cert.CertService;
import cci.service.cert.XSLWriter;
import cci.service.client.ClientService;
import cci.service.fscert.FSCertificateService;
import cci.service.fscert.FSFilter;
import cci.service.staff.EmployeeService;
import cci.web.controller.ViewManager;
import cci.web.controller.cert.CertificateController;


@Controller
@SessionAttributes({ "fscertfilter", "fsmanager","fscert"})
public class FSCertificateController {
	
	private static final Logger LOG=Logger.getLogger(FSCertificateController.class);
	
	@Autowired
	private FSCertificateService fsCertService;
	
	@Autowired
	private CertService certService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmployeeService employeeService;


	// ---------------------------------------------------------------------------------------
	//  Main Request - Get List of FS Certificates
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fscerts.do", method = RequestMethod.GET)
	public String listcerts(
			// HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			Authentication aut,
			ModelMap model) {

		long start = System.currentTimeMillis();
		LOG.info("< =========================== GET FS CERT LIST =================================== >");

		ViewManager vmanager = (ViewManager) model.get("fsmanager");

		if (vmanager == null) {
			vmanager = initViewManager(model);
		}

		if (orderby == null || orderby.isEmpty())
			orderby = "datecert";
		if (order == null || order.isEmpty())
			order = ViewManager.ORDASC;
		if (onfilter == null)
			onfilter = false;

		vmanager.setPage(page == null ? 1 : page);
		vmanager.setPagesize(pagesize == null ? 10 : pagesize);
		vmanager.setOrderby(orderby);
		vmanager.setOrder(order);
		vmanager.setOnfilter(onfilter);
		vmanager.setUrl("fscerts.do");

		Filter filter = null;
		
		if (onfilter) {
			filter = vmanager.getFilter();

			if (filter == null) {
				if (model.get("fscertfilter") != null) {
					filter = (Filter) model.get("fscertfilter");
				} else {
					filter = new FSFilter();
					model.addAttribute("fscertfilter", filter);
				}
				vmanager.setFilter(filter);
			}
		} 
		
		// ACL needs: use filter to restrict access to documents  
		if (filter == null) {
			filter = new FSFilter();
		}
		
		
		Iterator iterator = aut.getAuthorities().iterator(); 
		while (iterator.hasNext()) {
			
		      String roleName = ((GrantedAuthority) iterator.next()).getAuthority();
		      
		      if  (certService.getACL().containsKey(roleName)) {      
			      filter.setConditionValue("OTD_ID", "OTD_ID", "=", 
			    		  certService.getACL().get(roleName), FieldType.NUMBER);
		      }
		}
		
		SQLBuilder builder = new SQLBuilderFSCertificate();
		builder.setFilter(filter);
		
		long step2 = System.currentTimeMillis();
		vmanager.setPagecount(fsCertService.getViewPageCount(builder));
		
		long step3 = System.currentTimeMillis();
		
		List<ViewFSCertificate> certs = fsCertService.readCertificatesPage(
				vmanager.getOrdnames(),
				vmanager.getPage(), vmanager.getPagesize(), vmanager.getPagecount(),  
				vmanager.getOrderby(), vmanager.getOrder(), builder);
		
		long step4 = System.currentTimeMillis();
		vmanager.setElements(certs);
        
		
		model.addAttribute("fsmanager", vmanager);
		model.addAttribute("certs", certs);
		model.addAttribute("next_page", vmanager.getNextPageLink());
		model.addAttribute("prev_page", vmanager.getPrevPageLink());
		model.addAttribute("last_page", vmanager.getLastPageLink());
		model.addAttribute("first_page", vmanager.getFirstPageLink());
		model.addAttribute("pages", vmanager.getPagesList());
		model.addAttribute("sizes", vmanager.getSizesList());
		model.addAttribute("timeduration", 
				" " + (step3 - step2) + " + " + (step4 - step3) + " = " + (System.currentTimeMillis() - start));

		return "listfscertificates";
	}
	
	//------------------------------------------------------------------------------
	//  Init viewmanager 
	//------------------------------------------------------------------------------
	private ViewManager initViewManager(ModelMap model) {
		ViewManager vmanager = new ViewManager();
		vmanager.setHnames(new String[] {"Номер Сертификата",  "Экспортер", 
				"Производитель", "Дата", "Кол. лист"});
		vmanager.setOrdnames(new String[] { "certnumber", "exportername", "producername",
				"datecert", "listscount"});
		vmanager.setWidths(new int[] { 10, 35, 35, 10, 10,  });
		model.addAttribute("fsmanager", vmanager);

		return vmanager;
	}
	
  	// ---------------------------------------------------------------------------------------
	//    FS Certificate filter handling Method GET
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fscertfilter.do", method = RequestMethod.GET)
	public String openFilter(
				@ModelAttribute("fscertfilter") FSFilter fc, ModelMap model) {

			if (fc == null) {
				fc = new FSFilter();
				LOG.debug("New filterFSCertificate created in GET method");
				model.addAttribute("fscertfilter", fc);
			} else {
				LOG.debug("Found FilterFSCertificate in GET : ");
			}

			ViewFSCertificateFilter vf = new ViewFSCertificateFilter(
					((FSFilter) fc).getViewcertificate(),
					((FSFilter) fc).getCondition());
			model.addAttribute("viewfsfilter", vf);
			return "fs/fsfilter";
	}
		
	// ---------------------------------------------------------------------------------------
	//   FS Certificate filter handling Method POST
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fscertfilter.do", method = RequestMethod.POST)
	public String submitFilter(
				@ModelAttribute("viewfsfilter") ViewFSCertificateFilter viewfilter,
				@ModelAttribute("fscertfilter") FSFilter fc,
				BindingResult result, SessionStatus status, ModelMap model) {

			if (fc == null) {
				fc = new FSFilter();
				LOG.debug
						("New filterFSCertificate created in the POST method");
			} else {
				LOG.debug("Found FilterFSCertificate in POST");
			}

			fc.loadViewcertificate(viewfilter.getViewcertificate());
			fc.loadCondition(viewfilter.getCondition());

			model.addAttribute("fscertfilter", fc);
			return "fs/fsfilter";
	}
	
	// ---------------------------------------------------------------------------------------
	// View fs certs table config  for Export certificates to Excel format
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fscertconfig.do", method = RequestMethod.GET)
	public String openConfig(ModelMap model) {
		ViewManager vmanager = (ViewManager) model.get("fsmanager");

		if (vmanager == null) {
			vmanager = initViewManager(model);
		}
       
		if (vmanager.getDownloadconfig() == null) {
			vmanager.setDownloadconfig(new ExportFSCertConfig());
		}
		
		model.addAttribute("downloadconfig", vmanager.getDownloadconfig());
		model.addAttribute("headermap", vmanager.getDownloadconfig().getHeadermap());
		return "fs/config";
	}

	// ---------------------------------------------------------------------------------------
	//   Post fs certs table config  for Export certificates to Excel format
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fscertconfig.do", method = RequestMethod.POST)
	public String submitConfig(@ModelAttribute("downloadconfig") ExportFSCertConfig config,
			BindingResult result, SessionStatus status, ModelMap model) {
		
		ViewManager vmanager = (ViewManager) model.get("fsmanager");
		vmanager.setDownloadconfig(config);
                
		model.addAttribute("downloadconfig", vmanager.getDownloadconfig());
		model.addAttribute("headermap", config.getHeadermap());
		return "fs/config";
	}
	
	// ---------------------------------------------------------------------------------------
	//  Download list of FS Certificate as Excel file
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fscertdownload.do", method = RequestMethod.GET)
	public void XSLFileDownload(HttpSession session,
				HttpServletResponse response, ModelMap model) {
			try {
				
	            LOG.debug("Download started...");   
				ViewManager vmanager = (ViewManager) model.get("fsmanager");

				Filter filter = vmanager.getFilter();
				if (filter == null) {
					if (model.get("fscertfilter") != null) {
						filter = (Filter) model.get("fscertfilter");
					} else {
						filter = new FSFilter();
						model.addAttribute("fscertfilter", filter);
					}
					vmanager.setFilter(filter);
				}

				if (vmanager.getDownloadconfig() == null) {
					vmanager.setDownloadconfig(new ExportFSCertConfig());
				}

				SQLBuilder builder = new SQLBuilderFSCertificate();
				builder.setFilter(filter);
				List certs = fsCertService.readCertificates(vmanager.getDownloadconfig().getFields(),
						vmanager.getOrderby(), vmanager.getOrder(), builder);
				
				LOG.debug("Download. Certificates loaded from database..."); 
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

				response.setHeader("Content-Disposition",
						"attachment; filename=fscertificates.xlsx");
				(new XSLWriter()).makeWorkbook(certs,
						vmanager.getDownloadconfig().getHeaders(),  
						vmanager.getDownloadconfig().getFields(), "Лист Сертификатов").write(
						response.getOutputStream());
				response.flushBuffer();
				LOG.debug("Download finished...");

			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	// ---------------------------------------------------------------------------------------
	//   View FS Certificate as HTML page 
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fscert.do",  method = RequestMethod.GET)
	public String viewHTML(@RequestParam(value = "certid", required = true) Integer certid,
				ModelMap model) {
			try {
			     FSCertificate cert = fsCertService.getFSCertificateById(certid);
			     model.addAttribute("fscert", cert);
			     LOG.info(cert); 
			} catch (Exception ex) {
				model.addAttribute("error", ex.getMessage());
				return "error";
			}
			return "fs/viewfscertificate";
	}
	
	
	// ---------------------------------------------------------------------------------------
	//   EDIT FS Certificate as HTML page 
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fsedit.do",  method = RequestMethod.GET)
	public String edit(@RequestParam(value = "certid", required = true) Integer certid,
				ModelMap model) {
			try {
			     FSCertificate cert = fsCertService.getFSCertificateById(certid);
			     model.addAttribute("fscert", cert);
			     LOG.info(cert); 
			} catch (Exception ex) {
				model.addAttribute("error", ex.getMessage());
				return "error";
			}
			return "editfscertificate";
	}

	// ---------------------------------------------------------------------------------------
	//   SAVE/ FS Certificate as HTML page 
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "fsedit.do",  method = RequestMethod.POST)
	public String save(FSCertificate fscert,
			BindingResult result, SessionStatus status, ModelMap model) {
		    
		    FSCertificate storedCert = (FSCertificate)model.get("fscert");
		    
		    if (storedCert == null) {
			    model.addAttribute("error", "Информация о редактируемом сертификате потеряна. Перезегрузите сертификат.");		       	
		    } else {
		    	fscert.setId(storedCert.getId());
		    	fscert.setCertnumber(storedCert.getCertnumber());
		    	fscert.setBranch(storedCert.getBranch());
		    	fscert.setExporter(storedCert.getExporter());
		    	fscert.setProducer(storedCert.getProducer());
		    	fscert.setExpert(storedCert.getExpert());
		    	fscert.setSigner(storedCert.getSigner());
		    	fscert.setBlanks(storedCert.getBlanks());
		    	fscert.setProducts(storedCert.getProducts());
		    	try {
		    		fsCertService.save(fscert);
					model.remove("error");		    		
		    	} catch (Exception ex) {
					model.addAttribute("error", ex.getMessage());
		    	}
		    	model.addAttribute("fscert", fscert);
		    	storedCert = null;
		    }
			return "editfscertificate";
	}

	// ---------------------------------------------------------------------------------------
	//   Link Exporter to FS certificate  
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "selclient.do",  method = RequestMethod.GET)
	public void linkClientToFSCertificate(
			@RequestParam(value = "id", required = true) Long certid,
			@RequestParam(value = "clienttype", required = true) String clienttype,
			HttpSession session, HttpServletResponse response, ModelMap model) {
		
			try {
				  LOG.info("Exporter ID: " + certid);
				  FSCertificate cert = (FSCertificate)model.get("fscert");
				  Client client = clientService.readClient(certid.longValue());
				 
				  if (client!=null && cert!=null) {
					  if ("exporter".equals(clienttype)) {
						Exporter exporter = new Exporter();
						exporter.init(client);
						cert.setExporter(exporter);
					  } else if ("producer".equals(clienttype)) {
						Producer producer = new Producer();
						producer.init(client);
						cert.setProducer(producer);
					  } 
				
					  response.setContentType("text/html; charset=UTF-8");
					  response.setCharacterEncoding("UTF-8");
					  response.getWriter().println(client.getName() + "; " + client.getAddress());
					  response.flushBuffer();
					  LOG.info("Linked exporter to certificate: " + cert);
				  } else {	 
					  model.addAttribute("error", "Сертификат или экспортер не найдены");
				  }
			} catch (Exception ex) {
				ex.printStackTrace();
				LOG.info("Ошибка: " + ex.getMessage());
				model.addAttribute("error", ex.getMessage());
			}
	}
	

	// ---------------------------------------------------------------------------------------
	//   Link Exporter to FS certificate  
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "selemployee.do",  method = RequestMethod.GET)
	public void linkEmployeeToFSCertificate(
			@RequestParam(value = "id", required = true) Long eid,
			@RequestParam(value = "employeetype", required = true) String etype,
			HttpSession session, HttpServletResponse response, ModelMap model) {
		
			try {
				  LOG.info("Employee ID: " + eid);
				  FSCertificate cert = (FSCertificate)model.get("fscert");
				  Employee emp = employeeService.readEmployee(eid.longValue());
				 
				  if (emp!=null && cert!=null) {
					  if ("expert".equals(etype)) {
						Expert expert = new Expert();
						expert.init(emp);
						cert.setExpert(expert);
					  } else if ("signer".equals(etype)) {
						Signer signer = new Signer();
						signer.init(emp);
						cert.setSigner(signer);
					  } 
				
					  response.setContentType("text/html; charset=UTF-8");
					  response.setCharacterEncoding("UTF-8");
					  response.getWriter().println(emp.getJob() + " " + emp.getName());
					  response.flushBuffer();
					  LOG.info("Linked employee to certificate: " + cert);
				  } else {	 
					  model.addAttribute("error", "Сертификат или сотрудник не найдены");
				  }
			} catch (Exception ex) {
				ex.printStackTrace();
				LOG.info("Ошибка: " + ex.getMessage());
				model.addAttribute("error", ex.getMessage());
			}
	}

	
	
	// ---------------------------------------------------------------------------------------
	//   Reload Template of certification field
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "rldconfirm.do",  method = RequestMethod.GET)
	public void reloadConfirmationField(
			@RequestParam(value = "lang", required = true) String lang,
			HttpSession session, HttpServletResponse response, ModelMap model) {
			
			try {
				  FSCertificate cert = (FSCertificate)model.get("fscert");
				  
				  String template = fsCertService.getTemplate("confirmation", lang);
				  String replacement = cert.getProducer() != null ? 
						           cert.getProducer().getName() != null ? 
						           cert.getProducer().getName() + ", " + cert.getProducer().getAddress() : "" : "";
				  template = template.replaceAll("\\[producer\\]", replacement);				  
				  cert.setConfirmation(template);
				 
				  response.setContentType("text/html; charset=UTF-8");
				  response.setCharacterEncoding("UTF-8");
  			      response.getWriter().println(cert.getConfirmation());
				  response.flushBuffer();
				  
			} catch (Exception ex) {
					ex.printStackTrace();
					LOG.info("Ошибка: " + ex.getMessage());
					model.addAttribute("error", ex.getMessage());
			}
	}

	
	// ---------------------------------------------------------------------------------------
	//   Reload Declaration from template
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "rlddecl.do",  method = RequestMethod.GET)
	public void reloadDeclarationField(
			@RequestParam(value = "lang", required = true) String lang,
			HttpSession session, HttpServletResponse response, ModelMap model) {
			
			try {
				  FSCertificate cert = (FSCertificate)model.get("fscert");
				  
				  String template = fsCertService.getTemplate("declaration", lang);
				  cert.setConfirmation(template);
				 
				  response.setContentType("text/html; charset=UTF-8");
				  response.setCharacterEncoding("UTF-8");
  			      response.getWriter().println(cert.getConfirmation());
				  response.flushBuffer();
				  
			} catch (Exception ex) {
					ex.printStackTrace();
					LOG.info("Ошибка: " + ex.getMessage());
					model.addAttribute("error", ex.getMessage());
			}
	}


	// ---------------------------------------------------------------------------------------
	// Fill in lists 
	// ---------------------------------------------------------------------------------------
	@ModelAttribute("countries")
	public Map<String, String> populateCountryRuList() {
		return certService.getCountriesList("RU");
	}

	@ModelAttribute("countriesen")
	public Map<String, String> populateCountryEnList() {
		return certService.getCountriesList("EN");
	}
	
	@ModelAttribute("departments")
	public Map<String, String> populateDepartmentssList() {
		return certService.getBranchesList();
	}
	
	@ModelAttribute("languages")
	public Map<String, String> populateLanguageList() {
		return certService.getLanguageList();
	}

}
