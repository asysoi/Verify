package cci.web.controller.cert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cci.config.cert.ExportCertConfig;
import cci.model.cert.Certificate;
import cci.pdfbuilder.cert.CertificatePDFBuilder;
import cci.repository.SQLBuilder;
import cci.repository.cert.SQLBuilderCertificate;
import cci.service.CountryConverter;
import cci.service.Filter;
import cci.service.cert.CERTService;
import cci.service.cert.CertFilter;
import cci.service.cert.XSLWriter;
import cci.web.controller.User;
import cci.web.controller.ViewManager;

@Controller
@SessionAttributes({ "certfilter", "vmanager" })
public class CertController {
	
	public static Logger LOG=LogManager.getLogger(CertController.class);
	
	@Autowired
	private CERTService certService;

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String mainpage(ModelMap model) {
        LOG.info("Main Page Loaded..."); 
		return "welcome";
	}

	@RequestMapping(value = "/window.do", method = RequestMethod.GET)
	public String dasboard(ModelMap model) {

		return "window";
	}

	@RequestMapping(value = "/help.do", method = RequestMethod.GET)
	public String help(ModelMap model) {

		model.addAttribute("jspName", "help.jsp");
		return "window";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String loginInit(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}

	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user,
			BindingResult result, SessionStatus status) {
		if (user.getUsername().equals(user.getPassword())) {
			return "welcome";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/print.do")
	@ResponseBody
	public String print(
			@RequestParam(value = "name", required = false) String name,
			Model model) {

		return "<html>Hello <p> Name: " + name + "</p></html>";
	}

	@RequestMapping(value = "/cert.do")
	@ResponseBody
	public Certificate printcert() {

		Certificate certificate;
		certificate = new Certificate();
		return certificate;
	}

	
	@RequestMapping(value = "/config.do", method = RequestMethod.GET)
	public String openConfig(ModelMap model) {
		ViewManager vmanager = (ViewManager) model.get("vmanager");

		if (vmanager == null) {
			vmanager = initViewManager(model);
		}
       
		if (vmanager.getDownloadconfig() == null) {
			vmanager.setDownloadconfig(new ExportCertConfig());
		}
		
		model.addAttribute("downloadconfig", vmanager.getDownloadconfig());
		model.addAttribute("headermap", vmanager.getDownloadconfig().getHeadermap());
		return "fragments/config";
	}

	@RequestMapping(value = "/config.do", method = RequestMethod.POST)
	public String submitConfig(@ModelAttribute("downloadconfig") ExportCertConfig config,
			BindingResult result, SessionStatus status, ModelMap model) {
		
		ViewManager vmanager = (ViewManager) model.get("vmanager");
		vmanager.setDownloadconfig(config);
                
		model.addAttribute("downloadconfig", vmanager.getDownloadconfig());
		model.addAttribute("headermap", config.getHeadermap());
		return "fragments/config";
	}
	
	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	public void XSLFileDownload(HttpSession session,
			HttpServletResponse response, ModelMap model) {
		try {
			
            LOG.info("Download started...");   
			ViewManager vmanager = (ViewManager) model.get("vmanager");

			Filter filter = vmanager.getFilter();
			if (filter == null) {
				if (model.get("certfilter") != null) {
					filter = (Filter) model.get("certfilter");
				} else {
					filter = new CertFilter();
					model.addAttribute("certfilter", filter);
				}
				vmanager.setFilter(filter);
			}

			if (vmanager.getDownloadconfig() == null) {
				vmanager.setDownloadconfig(new ExportCertConfig());
			}

			SQLBuilder builder = new SQLBuilderCertificate();
			builder.setFilter(filter);
			List certs = certService.readCertificates(
					vmanager.getOrderby(), vmanager.getOrder(), builder);
			
			LOG.info("Download. Certificates loaded from database..."); 
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			//response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment; filename=certificates.xlsx");
			(new XSLWriter()).makeWorkbook(certs,
					vmanager.getDownloadconfig().getHeaders(),
					vmanager.getDownloadconfig().getFields(), "Лист Сертификатов").write(
					response.getOutputStream());
			response.flushBuffer();
			LOG.info("Download finished...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/certs.do", method = RequestMethod.GET)
	public String listcerts(
			// HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			ModelMap model) {

		long start = System.currentTimeMillis();
		System.out
				.println("=========================== GET CERT LIST =================================== >");

		// ViewManager vmanager = (ViewManager)
		// request.getSession().getAttribute("vmanager");
		ViewManager vmanager = (ViewManager) model.get("vmanager");

		if (vmanager == null) {
			vmanager = initViewManager(model);
		}

		if (orderby == null || orderby.isEmpty())
			orderby = "issuedate";
		if (order == null || order.isEmpty())
			order = ViewManager.ORDASC;
		if (onfilter == null)
			onfilter = false;

		vmanager.setPage(page == null ? 1 : page);
		vmanager.setPagesize(pagesize == null ? 10 : pagesize);
		vmanager.setOrderby(orderby);
		vmanager.setOrder(order);
		vmanager.setOnfilter(onfilter);
		vmanager.setUrl("certs.do");
		long step1 = System.currentTimeMillis();

		Filter filter = null;
		if (onfilter) {
			filter = vmanager.getFilter();

			if (filter == null) {
				if (model.get("certfilter") != null) {
					filter = (Filter) model.get("certfilter");
				} else {
					filter = new CertFilter();
					model.addAttribute("certfilter", filter);
				}
				vmanager.setFilter(filter);
			}
		}

		long step2 = System.currentTimeMillis();
		SQLBuilder builder = new SQLBuilderCertificate();
		long step21 = System.currentTimeMillis();
		builder.setFilter(filter);
		long step22 = System.currentTimeMillis();
		vmanager.setPagecount(certService.getViewPageCount(builder));
		long step23 = System.currentTimeMillis();
		List<Certificate> certs = certService.readCertificatesPage(
				vmanager.getPage(), vmanager.getPagesize(),
				vmanager.getOrderby(), vmanager.getOrder(), builder);
		long step3 = System.currentTimeMillis();
		vmanager.setElements(certs);

		long step4 = System.currentTimeMillis();
		model.addAttribute("vmanager", vmanager);
		model.addAttribute("certs", certs);
		model.addAttribute("next_page", vmanager.getNextPageLink());
		model.addAttribute("prev_page", vmanager.getPrevPageLink());
		model.addAttribute("last_page", vmanager.getLastPageLink());
		model.addAttribute("first_page", vmanager.getFirstPageLink());
		model.addAttribute("pages", vmanager.getPagesList());
		model.addAttribute("sizes", vmanager.getSizesList());
		model.addAttribute("timeduration", (System.currentTimeMillis() - start)
				+ " = " + (step1 - start) + "+" + (step2 - step1) + "+"
				+ (step21 - step2) + (step22 - step21) + "+"
				+ (step23 - step22) + "+" + (step3 - step23) + "+"
				+ (step4 - step3) + "+" + (System.currentTimeMillis() - step4));

		return "listcertificates";
	}

	private ViewManager initViewManager(ModelMap model) {
		ViewManager vmanager = new ViewManager();
		vmanager.setHnames(new String[] { "Номер Сертификата", "Отделение",
				"Грузоотправитель/Экспортер", "Номер бланка", "Дата",
				"Доп. лист", "Замена для." });
		vmanager.setOrdnames(new String[] { "nomercert", "otd_name", "kontrp",
				"nblanka", "issuedate", "koldoplist", "parentnumber" });
		vmanager.setWidths(new int[] { 10, 20, 40, 8, 8, 6, 8 });
		model.addAttribute("vmanager", vmanager);
		// request.getSession().setAttribute("vmanager", vmanager);

		return vmanager;
	}

	@RequestMapping(value = "/filter.do", method = RequestMethod.GET)
	public String openFilter(
			@ModelAttribute("certfilter") CertFilter fc, ModelMap model) {

		if (fc == null) {
			fc = new CertFilter();
			LOG.info("New filterCertificate created in GET method");
			model.addAttribute("certfilter", fc);
		} else {
			LOG.info("Found FilterCertificate in GET : ");
		}

		ViewCertFilter vf = new ViewCertFilter(
				((CertFilter) fc).getViewcertificate(),
				((CertFilter) fc).getCondition());
		model.addAttribute("viewfilter", vf);
		return "fragments/filter";
	}

	@RequestMapping(value = "/filter.do", method = RequestMethod.POST)
	public String submitFilter(
			@ModelAttribute("viewfilter") ViewCertFilter viewfilter,
			@ModelAttribute("certfilter") CertFilter fc,
			BindingResult result, SessionStatus status, ModelMap model) {

		if (fc == null) {
			fc = new CertFilter();
			System.out
					.println("New filterCertificate created in the POST method");
		} else {
			LOG.info("Found FilterCertificate in POST");
		}

		fc.loadViewcertificate(viewfilter.getViewcertificate());
		fc.loadCondition(viewfilter.getCondition());

		model.addAttribute("certfilter", fc);
		return "fragments/filter";
	}

	@RequestMapping(value = "/gocert.do")
	public String gocert(HttpServletRequest request, 
			@RequestParam(value = "certid", required = true) Integer certid,
			ModelMap model) {
		String relativeWebPath = "/resources";
		String  absoluteDiskPath= request.getSession().getServletContext().getRealPath(relativeWebPath);
		LOG.info("Absolute path: " + absoluteDiskPath);
		
		Certificate cert = certService.readCertificate(certid);
		makepdffile(absoluteDiskPath, cert);
		model.addAttribute("cert", cert);
		return "redirect:" + "/resources/out/" + cert.getCert_id() + ".pdf";
	}

	private void makepdffile(String absoluteDiskPath, Certificate cert) {
		CertificatePDFBuilder builder = new CertificatePDFBuilder();
		String fout = absoluteDiskPath + "/out/" + cert.getCert_id() + ".pdf";
		String fconf = absoluteDiskPath + "/config/pages.xml";
		
		CountryConverter.setCountrymap(certService.getCountriesList());
		
		try {
		   builder.createPdf(fout, cert, fconf);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/check.do", method = RequestMethod.GET)
	public String check(ModelMap model) {
		String retpage = "check";
		Certificate cert = new Certificate();
		model.addAttribute("cert", cert);
		return retpage;
	}

	@RequestMapping(value = "/check.do", method = RequestMethod.POST)
	public String check(@ModelAttribute("cert") Certificate cert,
			BindingResult result, SessionStatus status, ModelMap model, HttpServletRequest request) {
		String retpage = "check";

		if (cert.getNomercert() != null && cert.getNblanka() != null
				&& cert.getDatacert() != null) {
			Certificate rcert = certService.checkCertificate(cert);

			if (rcert != null) {
				//model.addAttribute("cert", rcert);
				//retpage = "certificate";
				
				String relativeWebPath = "/resources";
				String  absoluteDiskPath= request.getSession().getServletContext().getRealPath(relativeWebPath);
				makepdffile(absoluteDiskPath, rcert);
				
				String msg = "<p>Найден сертификат номер [" + cert.getNomercert()
						+ "] на бланке [" + cert.getNblanka() + "] от ["
						+ cert.getDatacert()
						+ "].</p> "+
				        "<p>Воспроизведение бумажной версии сертификата <a href=\"javascript:viewCertificate(\'" +
						//rcert.getCert_id() +
						"resources/out/" + rcert.getCert_id() + ".pdf" +
						"')\">" + rcert.getNomercert() + "</a><p style=\"width:100%\">" + 
			            "<p>Результат воспроизведения может незначительно отличаться по форме и стилю отображения," +
			            "но полностью воспроизводит содержание документа.</p>";
				
				model.addAttribute("msg", msg);
				retpage = "fragments/message";
			} else {
				model.addAttribute("cert", cert);
				String msg = "Сертификат номер [" + cert.getNomercert()
						+ "] на бланке [" + cert.getNblanka() + "] от ["
						+ cert.getDatacert()
						+ "] не найден в центральном хранилище";
				model.addAttribute("msg", msg);
				retpage = "fragments/message";
			}
		}
		return retpage;
	}

	@RequestMapping(value = "/upload.do", method = RequestMethod.GET)
	public String uploadFromFTP(ModelMap model) {
		certService.uploadCertificateFromFTP();
		return "window";
	}

	@ModelAttribute("countries")
	public Map<String, String> populateCompanyList() {
		return certService.getCountriesList();
	}

	@ModelAttribute("departments")
	public List<String> populateDepartmentssList() {
		return certService.getDepartmentsList();
	}

	@ModelAttribute("forms")
	public List<String> populateFormsList() {
		return certService.getFormsList();
	}

}
