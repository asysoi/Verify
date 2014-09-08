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
import org.springframework.web.bind.support.SessionStatus;

import cci.cert.model.Certificate;
import cci.cert.service.CERTService;
import cci.cert.service.FTPReader;
import cci.purchase.web.controller.HeaderTableView;
import cci.purchase.web.controller.PurchaseView;

@Controller
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

	@RequestMapping(value = "/certs.do")
	public String listcerts(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean filter,			
			@RequestParam(value = "filterfield", required = false) String filterfield,
			@RequestParam(value = "filteroperator", required = false) String filteroperator,
			@RequestParam(value = "filtervalue", required = false) String filtervalue,
			ModelMap model) {
		
		String[] hnames = {"Номер Сертификата", "Эксперт", "Получатель", "УНП", "Номер бланка", "Дата сертификата"};
		String[] ordnames = {"", "", "", "", "", ""};
	    int[] widths = {15, 15, 20, 10, 10, 30, 10};
		String ordasc = "asc";
        String orddesc = "desc";
        
		int page_index = (page == null ? 1 : page);
		int page_size = (pagesize == null ? 10 : pagesize);
		
		if (orderby == null || orderby.isEmpty()) orderby = "";
		if (order == null || order.isEmpty()) order = ordasc;
		if (filter == null ) filter = false;
		
		
        List<HeaderTableView> headers = new ArrayList<HeaderTableView>(); 
        for (int i = 0; i < widths.length ; i++) {
        	if (ordnames[i].equals(orderby)) {
   	           headers.add(makeHeaderTableView(widths[i], hnames[i], page_index, page_size, ordnames[i], order.equals(ordasc) ? orddesc : ordasc, true));
        	} else {
        	   headers.add(makeHeaderTableView(widths[i], hnames[i], page_index, page_size, ordnames[i], ordasc, false)); 	
        	}
        }
        
	    List<PurchaseView> purchases= purchaseService.readPurchaseViewPage(page_index, page_size, orderby, order, getFilter(filter, filterfield, filteroperator, filtervalue));
		model.addAttribute("purchases", purchases);
		model.addAttribute("headers", headers);
		model.addAttribute("page", page_index);
		model.addAttribute("pagesize", page_size);
		model.addAttribute("orderby", orderby);
		model.addAttribute("order", order);
		model.addAttribute("filterfield", filterfield);
		model.addAttribute("filteroperator", filteroperator);
		model.addAttribute("filtervalue", filtervalue);
		model.addAttribute("filter", filter);
		
		int prcount = purchaseService.getPurchaseViewPageCount(getFilter(filter, filterfield, filteroperator, filtervalue));
		model.addAttribute("next_page", getNextPageLink(page_index, page_size, prcount, orderby, order) );
		model.addAttribute("prev_page", getPrevPageLink(page_index, page_size, prcount, orderby, order) );
		model.addAttribute("pages", getPagesList(page_index, page_size, prcount));
		model.addAttribute("sizes", getSizesList());
				
		
		
		
		
		
		List<Certificate> certs = certService.readCertificatesPage(page_index, 20);
		
		model.addAttribute("certs", certs);
		model.addAttribute("next_page", "certs.do?page=" + (page_index + 1));
		model.addAttribute("prev_page", "certs.do?page=" + (page_index - 1));
		
		return "listcertificates";
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
