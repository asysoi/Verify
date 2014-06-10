package cci.cert.web.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import cci.cert.model.Address;
import cci.cert.model.CERTTYPE;
import cci.cert.model.Certificate;
import cci.cert.model.Customer;
import cci.cert.repositiry.CertificateDAO;
import cci.cert.service.CERTService;

@Controller
public class CertController {
		
	@Autowired
	private CertificateDAO certificateDAO;
	
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
    public String print(@RequestParam(value="name", required=false) String name, Model model) {
    	
        return "<html>Hello <p> Name: " + name + "</p></html>";
    }
    
	    
    @RequestMapping(value="/cert.do")
    @ResponseBody 
    public Certificate printcert() {
    	
       Certificate certificate;
   	   certificate = new Certificate();
       return certificate;
    }
    
      
    
    @RequestMapping(value="/certs.do")
    public String listcerts(@RequestParam(value="page", required=false) Integer page, ModelMap model) {
    	int page_index = page == null ? 1 : page;
        List<Certificate> certs = certService.readCertificatesPage(page_index, 20);
        System.out.println(certs.size());
        
    	model.addAttribute("certs", certs);
    	model.addAttribute("next_page", "certs.do?page=" + (page_index + 1));
    	model.addAttribute("prev_page", "certs.do?page=" + (page_index - 1));
        return "listcertificates";
    }
    
    
    @RequestMapping(value="/gocert.do")
    public String gocert(@RequestParam(value="certid", required=true) Integer certid, ModelMap model) {
    	Certificate cert = certService.readCertificate(certid);
    	model.addAttribute("cert", cert);
    	certService.printCertificate(cert);
        return "certificate";
    }
    
    @RequestMapping(value="/check.do")
    public String check(@RequestParam(value="certnum", required=false) String certnum, 
    					@RequestParam(value="certblank", required=false) String certblank,
    					@RequestParam(value="certdate", required=false) String certdate,    					
    					ModelMap model) {
    	String retpage = "check";
    	if (certnum!= null && certblank!=null && certdate!=null) {
    	    Certificate cert = certService.checkCertificate(certnum, certblank, certdate);
    	    if (cert != null) {
    	       model.addAttribute("cert", cert);
    	       retpage = "certificate";
    	    } else {
    	       model.addAttribute("certnum",certnum);
    	       model.addAttribute("certblank", certblank);
    	       model.addAttribute("certdate", certdate);
    	       model.addAttribute("msg", "Сертификат с заданными параметрами не найден");	
    	    }
    	}
        return retpage;
    }
	
    
    
    
    
  //readAllCertificate(context);
  		//service.uploadCertificate(context);
  		//Locale.setDefault(new Locale("en", "us"));
  		//Certificate cert = dao.findByID(13459L);
  		//service.printCertificate(cert);
  		//Certificate cert = new Certificate();
  		//cert.setDatacert("27.07.12");
  		//cert.setNblanka("3350732");
  		//cert.setNomercert("11/515-1");
  		
  		//List<Certificate> certs = dao.findByCertificate(cert);
  		//System.out.println("Найдено сертификатов : " + certs.size());  
}
