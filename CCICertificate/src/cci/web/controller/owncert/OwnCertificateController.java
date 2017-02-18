package cci.web.controller.owncert;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import cci.model.cert.Certificate;
import cci.model.owncert.OwnCertificate;
import cci.repository.SQLBuilder;
import cci.repository.cert.SQLBuilderCertificate;
import cci.service.FieldType;
import cci.service.Filter;
import cci.service.cert.CertFilter;
import cci.service.owncert.OwnCertificateService;
import cci.web.controller.ViewManager;
import cci.web.controller.cert.CertificateController;


@Controller
@SessionAttributes({ "owncertfilter", "ownmanager" })
public class OwnCertificateController {
	
	private static final Logger LOG=Logger.getLogger(CertificateController.class);
	
	@Autowired
	private OwnCertificateService ownCertService;

	// ---------------------------------------------------------------------------------------
	//  Get List of Certificates
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "owncerts.do", method = RequestMethod.GET)
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
		LOG.info("=========================== GET OWN CERT LIST =================================== >");

		ViewManager vmanager = (ViewManager) model.get("ownmanager");

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
		vmanager.setUrl("owncerts.do");
		long step1 = System.currentTimeMillis();

		Filter filter = null;
		if (onfilter) {
			filter = vmanager.getFilter();

			if (filter == null) {
				if (model.get("owncertfilter") != null) {
					filter = (Filter) model.get("owncertfilter");
				} else {
					filter = new CertFilter();
					model.addAttribute("owncertfilter", filter);
				}
				vmanager.setFilter(filter);
			}
		} 
		
		// ACL needs
		if (filter == null) {
			filter = new CertFilter();
			LOG.debug
					("New filterCertificate created bacause of ACL");
		}
		
		/*  -------------------------------------------------------------------
		Iterator iterator = aut.getAuthorities().iterator(); 
		while (iterator.hasNext()) {
			
		      String roleName = ((GrantedAuthority) iterator.next()).getAuthority();
		      
		      if  (ownCertService.getACL().containsKey(roleName)) {      
			      filter.setConditionValue("OTD_ID", "OTD_ID", "=", ownCertService.getACL().get(roleName), FieldType.NUMBER);
		      }
		}
		
		SQLBuilder builder = new SQLBuilderOwnCertificate();
		builder.setFilter(filter);
		
		long step2 = System.currentTimeMillis();
		vmanager.setPagecount(ownCertService.getViewPageCount(builder));
		
		long step3 = System.currentTimeMillis();
		
		List<OwnCertificate> certs = ownCertService.readCertificatesPage(
				vmanager.getOrdnames(),
				vmanager.getPage(), vmanager.getPagesize(), vmanager.getPagecount(),  
				vmanager.getOrderby(), vmanager.getOrder(), builder);
		
		long step4 = System.currentTimeMillis();
		vmanager.setElements(certs);
        
		
		model.addAttribute("ownmanager", vmanager);
		model.addAttribute("certs", certs);
		model.addAttribute("next_page", vmanager.getNextPageLink());
		model.addAttribute("prev_page", vmanager.getPrevPageLink());
		model.addAttribute("last_page", vmanager.getLastPageLink());
		model.addAttribute("first_page", vmanager.getFirstPageLink());
		model.addAttribute("pages", vmanager.getPagesList());
		model.addAttribute("sizes", vmanager.getSizesList());
		model.addAttribute("timeduration", 
				" " + (step3 - step2) + " + " + (step4 - step3) + " = " + (System.currentTimeMillis() - start));
        ---------------------------------------------------------------------------*/

		return "listowncertificates";
	}

	private ViewManager initViewManager(ModelMap model) {
		ViewManager vmanager = new ViewManager();
		vmanager.setHnames(new String[] {"Номер Сертификата",  "Отделение", 
				"Предприятие", "Номер бланка", "Дата", "Доп. лист"});
		vmanager.setOrdnames(new String[] { "nomer", "beltppname", "customername",
				"blanknumber", "datecert", "additionallists"});
		vmanager.setWidths(new int[] { 15, 20, 40, 10, 10, 5 });
		model.addAttribute("vmanager", vmanager);

		return vmanager;
	}
	
	
	
	/* -----------------------------
	 * Exception handling 
	 * ----------------------------- */
	@ExceptionHandler(Exception.class)
	@ResponseBody
    public ResponseEntity<String> handleIOException(Exception ex) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Error-Code", "12345");
		responseHeaders.set("Content-Type", "application/json;charset=utf-8");
		return new ResponseEntity<String>(ex.toString(), responseHeaders,  HttpStatus.BAD_REQUEST);
    }

}
