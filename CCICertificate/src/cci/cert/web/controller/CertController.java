package cci.cert.web.controller;

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
	public String listcerts(
			@RequestParam(value = "page", required = false) Integer page,
			ModelMap model) {
		int page_index = page == null ? 1 : page;
		List<Certificate> certs = certService.readCertificatesPage(page_index,
				20);
		System.out.println(certs.size());

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
