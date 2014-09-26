package cci.cert.web.controller;

import java.util.ArrayList;
import java.util.List;

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

import cci.cert.model.Certificate;
import cci.cert.repository.SQLBuilder;
import cci.cert.repository.SQLBuilderCertificate;
import cci.cert.service.CERTService;
import cci.cert.service.FTPReader;
import cci.cert.service.Filter;
import cci.cert.service.FilterCertificate;
import cci.purchase.web.controller.HeaderTableView;
import cci.purchase.web.controller.PurchaseView;

@Controller
@SessionAttributes("certfilter")
public class CertController {
	
	@Autowired
	private CERTService certService;

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String mainpage(ModelMap model) {

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

		if (user.getUserName().equals(user.getPassword())) {
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

	
	
	
	@RequestMapping(value = "/certs.do", method = RequestMethod.GET)
	public String listcerts(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,			
			ModelMap model) {
		
		ViewManager vmanager = new ViewManager();
		vmanager.setHnames(new String[] {"Номер Сертификата", "Отделение", "Грузоотправитель/Экспортер", "Номер бланка", "Дата", "Послед."});
		vmanager.setOrdnames(new String[] {"nomercert", "name", "kontrp", "nblanka", "issuedate", "child"});
		vmanager.setWidths(new int[] {10, 20, 45, 10, 10, 5});
		vmanager.setPage(page == null ? 1 : page);
		vmanager.setPagesize(pagesize == null ? 10 : pagesize);
		
		if (orderby == null || orderby.isEmpty()) orderby = "issuedate";
		if (order == null || order.isEmpty()) order = ViewManager.ORDASC;
		if (onfilter == null ) onfilter = false;
		//if (fullsearchvalue == null ) fullsearchvalue = "";
		
		vmanager.setOrderby(orderby);
		vmanager.setOrder(order);
		vmanager.setOnfilter(onfilter);
		//vmanager.setFullsearchvalue(fullsearchvalue);
		
		vmanager.setUrl("certs.do");
		
		Filter filter = vmanager.getFilter();
		if (filter == null)  {
			if (model.get("certfilter") != null) {
			   filter = (Filter) model.get("certfilter");
			} else {
				filter = new FilterCertificate();
				model.addAttribute("certtfilter", filter);
			}
			vmanager.setFilter(filter);
		}
		System.out.println(filter.getFullsearchvalue());

 		SQLBuilder builder = new SQLBuilderCertificate();
		builder.setFilter(filter);
		
        vmanager.setPagecount(certService.getViewPageCount(builder));
        List<Certificate> certs = certService.readCertificatesPage(vmanager.getPage(), vmanager.getPagesize(), vmanager.getOrderby(), vmanager.getOrder(), builder);
		vmanager.setElements(certs);
	    
		model.addAttribute("vmanager", vmanager);
		model.addAttribute("certs", certs);
		model.addAttribute("next_page", vmanager.getNextPageLink());
		model.addAttribute("prev_page", vmanager.getPrevPageLink());
		model.addAttribute("last_page", vmanager.getLastPageLink());
		model.addAttribute("first_page", vmanager.getFirstPageLink());
		model.addAttribute("pages", vmanager.getPagesList());
		model.addAttribute("sizes", vmanager.getSizesList());
		
		return "listcertificates";
	}

	
	@RequestMapping(value="/filter.do", method = RequestMethod.GET)
	public String openFilter(ModelMap model) {
		Filter fc;
		if (model.get("certfilter") == null) {
			fc = new FilterCertificate();
			model.addAttribute("certtfilter", fc);
			System.out.println("Filter: " + fc);
		} else {
			fc = (Filter) model.get("certfilter");
		}
		System.out.println("filter.do. Step 2");
		model.addAttribute("filter", fc);
      	return "fragments/filter";
	}
	
	@RequestMapping(value="/filter.do", method = RequestMethod.POST)
	public String submitFilter(@ModelAttribute("filter") FilterCertificate fc,
			BindingResult result, SessionStatus status, ModelMap model) {
        model.addAttribute("certfilter", fc);  
		System.out.println("filter.do. Post Step");
		return "fragments/filter";
	}
	

	
	
	
	@RequestMapping(value = "/gocert.do")
	public String gocert(
			@RequestParam(value = "certid", required = true) Integer certid,
			ModelMap model) {
		Certificate cert = certService.readCertificate(certid);
		model.addAttribute("cert", cert);
		certService.printCertificate(cert);
		return "certificate";
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
			BindingResult result, SessionStatus status, ModelMap model) {
		String retpage = "check";

		if (cert.getNomercert() != null && cert.getNblanka() != null
				&& cert.getDatacert() != null) {
			Certificate rcert = certService.checkCertificate(cert);

			if (rcert != null) {
				model.addAttribute("cert", rcert);
				retpage = "certificate";
			} else {
				model.addAttribute("cert", cert);
				model.addAttribute(
						"msg",
						"Сертификат " + cert.getNomercert() + " на бланке "
								+ cert.getNblanka() + " от "
								+ cert.getDatacert() + " не зарегистрирован");
				retpage = "check";
			}
		}
		return retpage;
	}
	
	
	@RequestMapping(value = "/upload.do", method = RequestMethod.GET)
	public String uploadFromFTP(ModelMap model) {
		certService.uploadCertificateFromFTP();          
		return "window";
	}

}
