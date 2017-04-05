package cci.web.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {
	
	private static final Logger LOG=Logger.getLogger(MainController.class);
	
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String mainpage(ModelMap model, Authentication aut) {
        LOG.debug("User " + aut.getName() + " logged" ); 
		return "welcome";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultpage(ModelMap model, Authentication aut) {
        LOG.debug("User " + aut.getName() + " logged" ); 
		return "welcome";
	}

	@RequestMapping(value = "/help.do", method = RequestMethod.GET)
	public String help(ModelMap model) {

		model.addAttribute("jspName", "help.jsp");
		return "window";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String loginInit(ModelMap model) {
		return "login";
	}
	
	
	@RequestMapping(value = "/members.do", method = RequestMethod.GET)
	public String members() {
		return "listmembers";
	}
}
