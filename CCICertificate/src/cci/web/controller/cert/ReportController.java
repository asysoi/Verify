package cci.web.controller.cert;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import cci.model.cert.Certificate;
import cci.service.cert.CertService;
import cci.service.cert.ReportService;
import cci.web.controller.ViewManager;

@Controller
@SessionAttributes({ "reportfilter", "rmanager" })
public class ReportController {
	
	public static Logger LOG=Logger.getLogger(ReportController.class);
	//private static final Logger LOG=Logger.getLogger(CertController.class);
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private CertService certService;
	
	// ---------------------------------------------------------------------------------------
	//  Get List of Certificates
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "reportcerts.do", method = RequestMethod.GET)
	public String reportcerts(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			@RequestParam(value = "datefrom", required = false) String datefrom,
			@RequestParam(value = "dateto", required = false) String dateto,
			Authentication aut,	ModelMap model) {

		ViewManager rmanager = (ViewManager) model.get("rmanager");
		LOG.info("step1");
		
		if (rmanager == null) {
			rmanager = initViewManager(model); }

		if (orderby == null || orderby.isEmpty())
			orderby = "issuedate";
		if (order == null || order.isEmpty())
			order = ViewManager.ORDASC;
		if (onfilter == null)
			onfilter = false;

		rmanager.setPage(page == null ? 1 : page);
		rmanager.setPagesize(pagesize == null ? 10 : pagesize);
		rmanager.setOrderby(orderby);
		rmanager.setOrder(order);
		rmanager.setUrl("reportcerts.do");
		LOG.info("step2");
		
		String otd_name = null;
		Iterator iterator = (aut.getAuthorities()).iterator(); 
		while (iterator.hasNext()) {
			
		      String roleName = ((GrantedAuthority) iterator.next()).getAuthority();
		      
		      if  (certService.getACL().containsKey(roleName)) {      
		    	  otd_name = certService.getACL().get(roleName);
		      }
		}
		
		LOG.info("step3");
		rmanager.setPagecount(reportService.getViewPageCount(datefrom, dateto, otd_name));
		
		List<Certificate> certs = reportService.readCertificatesPage(
				rmanager.getPage(), rmanager.getPagesize(),
				rmanager.getOrderby(), rmanager.getOrder(), datefrom, dateto, otd_name);
		rmanager.setElements(certs);

		model.addAttribute("rmanager", rmanager);
		model.addAttribute("certs", certs);
		model.addAttribute("next_page", rmanager.getNextPageLink());
		model.addAttribute("prev_page", rmanager.getPrevPageLink());
		model.addAttribute("last_page", rmanager.getLastPageLink());
		model.addAttribute("first_page", rmanager.getFirstPageLink());
		model.addAttribute("pages", rmanager.getPagesList());
		model.addAttribute("sizes", rmanager.getSizesList());
		model.addAttribute("datefrom", datefrom);
		model.addAttribute("dateto", dateto);
		
		LOG.info("step4");
		model.addAttribute("jspName", "cert/report_include.jsp");
		return "window";
	}

	private ViewManager initViewManager(ModelMap model) {
		ViewManager rmanager = new ViewManager();
		rmanager.setHnames(new String[] { "Номер Сертификата", "Отделение",
				"Эксперт", "Номер бланка", "Дата серт.",
				"Дата загрузки" });
		rmanager.setOrdnames(new String[] { "nomercert", "otd_name", "eхpert",
				"nblanka", "issuedate", "dateload" });
		rmanager.setWidths(new int[] { 10, 20, 40, 8, 12, 12});
		model.addAttribute("rmanager", rmanager);

		return rmanager;
	}
}

