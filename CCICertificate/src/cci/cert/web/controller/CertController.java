package cci.cert.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import cci.purchase.model.Company;
import cci.purchase.model.Product;
import cci.purchase.web.controller.HeaderTableView;
import cci.purchase.web.controller.PurchaseView;

@Controller
@SessionAttributes({ "certfilter", "vmanager" })
public class CertController {

	@Autowired
	private CERTService certService;

	private Map<String, String> operators = new LinkedHashMap<String, String>();

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
	public String listcerts(
			// HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			ModelMap model) {

		System.out
				.println("=============================================================== >");

		// ViewManager vmanager = (ViewManager)
		// request.getSession().getAttribute("vmanager");
		ViewManager vmanager = (ViewManager) model.get("vmanager");

		if (vmanager == null) {
			vmanager = new ViewManager();
			vmanager.setHnames(new String[] { "Номер Сертификата", "Отделение",
					"Грузоотправитель/Экспортер", "Номер бланка", "Дата",
					"Замена." });
			vmanager.setOrdnames(new String[] { "nomercert", "name", "kontrp",
					"nblanka", "issuedate", "parent_id" });
			vmanager.setWidths(new int[] { 10, 20, 45, 10, 10, 5 });
			model.addAttribute("vmanager", vmanager);
			// request.getSession().setAttribute("vmanager", vmanager);
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

		Filter filter = null;
		if (onfilter) {
			filter = vmanager.getFilter();

			if (filter == null) {
				if (model.get("certfilter") != null) {
					filter = (Filter) model.get("certfilter");
				} else {
					filter = new FilterCertificate();
					model.addAttribute("certfilter", filter);
				}
				vmanager.setFilter(filter);
			}
		}

		SQLBuilder builder = new SQLBuilderCertificate();
		builder.setFilter(filter);
		vmanager.setPagecount(certService.getViewPageCount(builder));
		List<Certificate> certs = certService.readCertificatesPage(
				vmanager.getPage(), vmanager.getPagesize(),
				vmanager.getOrderby(), vmanager.getOrder(), builder);
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

	@RequestMapping(value = "/filter.do", method = RequestMethod.GET)
	public String openFilter(
			@ModelAttribute("certfilter") FilterCertificate fc, ModelMap model) {

		if (fc == null) {
			fc = new FilterCertificate();
			System.out.println("New filterCertificate created in GET method");
			model.addAttribute("certfilter", fc);
		} else {
			System.out.println("Found FilterCertificate in GET : ");
		}

		ViewFilter vf = new ViewFilter(
				((FilterCertificate) fc).getCertificate(),
				((FilterCertificate) fc).getCondition());
		model.addAttribute("viewfilter", vf);
		return "fragments/filter";
	}

	@RequestMapping(value = "/filter.do", method = RequestMethod.POST)
	public String submitFilter(
			@ModelAttribute("viewfilter") ViewFilter viewfilter,
			@ModelAttribute("certfilter") FilterCertificate fc,
			BindingResult result, SessionStatus status, ModelMap model) {

		if (fc == null) {
			fc = new FilterCertificate();
			System.out
					.println("New filterCertificate created in the POST method");
		} else {
			System.out.println("Found FilterCertificate in POST");
		}

		fc.loadCertificate(viewfilter.getCertificate());
		fc.loadCondition(viewfilter.getCondition());

		model.addAttribute("certfilter", fc);
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
				String msg = "Сертификат номер [" + cert.getNomercert() + "] на бланке ["
						+ cert.getNblanka() + "] от [" + cert.getDatacert()
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

	@ModelAttribute("operators")
	public Map<String, String> populateOperatorsList() {
		operators.put("", "");
		operators.put(">", "больше");
		operators.put("<", "меньше");
		operators.put("=", "равно");
		operators.put("like", "включает");
		return operators;
	}

	@ModelAttribute("countryList")
	public Map<String, String> populateCompanyList() {

		return null;
	}

	@ModelAttribute("departments")
	public List<String> populateDepartmentssList() {

		return certService.getDepartmentsList();
	}

}
