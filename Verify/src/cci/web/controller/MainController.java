package cci.web.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cci.model.cert.Certificate;


@Controller
public class MainController {
	
	private static final Logger LOG=Logger.getLogger(MainController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultpage(ModelMap model, Authentication aut) {
        LOG.info("User " + aut.getName() + " logged" ); 
       // Certificate cert = new Certificate();
		//model.addAttribute("cert", cert);
		return "404";
	}

}
