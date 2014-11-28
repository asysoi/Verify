package cci.web.controller.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cci.model.cert.Certificate;
import cci.model.purchase.Company;
import cci.repository.SQLBuilder;
import cci.repository.cert.SQLBuilderCertificate;
import cci.repository.client.SQLBuilderClient;
import cci.service.Filter;
import cci.service.cert.CERTService;
import cci.service.cert.FilterCertificate;
import cci.service.client.ClientService;
import cci.service.client.FilterClient;
import cci.web.controller.ViewManager;
import cci.web.controller.cert.CertController;
import cci.web.controller.cert.ViewCertFilter;


@Controller
@SessionAttributes({ "clientfilter", "cmanager" })
public class ClientController {
	
	public static Logger LOG=LogManager.getLogger(ClientController.class);
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/clients.do", method = RequestMethod.GET)
	public String listcerts(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			ModelMap model) {

		long start = System.currentTimeMillis();
		System.out
				.println("=========================== GET CLIENT LIST =================================== >");

		ViewManager cmanager = (ViewManager) model.get("cmanager");

		if (cmanager == null) {
			cmanager = initViewManager(model);
		}

		if (orderby == null || orderby.isEmpty())
			orderby = "otd_name";
		if (order == null || order.isEmpty())
			order = ViewManager.ORDASC;
		if (onfilter == null)
			onfilter = false;

		cmanager.setPage(page == null ? 1 : page);
		cmanager.setPagesize(pagesize == null ? 10 : pagesize);
		cmanager.setOrderby(orderby);
		cmanager.setOrder(order);
		cmanager.setOnfilter(onfilter);
		cmanager.setUrl("clients.do");

		Filter filter = null;
		if (onfilter) {
			filter = cmanager.getFilter();

			if (filter == null) {
				if (model.get("certfilter") != null) {
					filter = (Filter) model.get("certfilter");
				} else {
					filter = new FilterCertificate();
					model.addAttribute("certfilter", filter);
				}
				cmanager.setFilter(filter);
			}
		}

		SQLBuilder builder = new SQLBuilderClient();
		builder.setFilter(filter);
		cmanager.setPagecount(clientService.getViewPageCount(builder));
		List<Company> clients = clientService.readClientsPage(
				cmanager.getPage(), cmanager.getPagesize(),
				cmanager.getOrderby(), cmanager.getOrder(), builder);
		cmanager.setElements(clients);

		model.addAttribute("cmanager", cmanager);
		model.addAttribute("clients", clients);
		model.addAttribute("next_page", cmanager.getNextPageLink());
		model.addAttribute("prev_page", cmanager.getPrevPageLink());
		model.addAttribute("last_page", cmanager.getLastPageLink());
		model.addAttribute("first_page", cmanager.getFirstPageLink());
		model.addAttribute("pages", cmanager.getPagesList());
		model.addAttribute("sizes", cmanager.getSizesList());
		
		//<% session.setAttribute("jspName", "fragments/certs_include.jsp"); %> 
		model.addAttribute("jspName", "client/clients_include.jsp");
		return "window";
		
		// return "listclients";
	}

	private ViewManager initViewManager(ModelMap model) {
		ViewManager cmanager = new ViewManager();
		cmanager.setHnames(new String[] { "Наименование компании", "Адрес",
				"УНП", "Банковские реквизиты" });
		cmanager.setOrdnames(new String[] { "otd_name", "address",
				"unp", "bank" });
		cmanager.setWidths(new int[] { 10, 20, 40, 8, 8, 6, 8 });
		model.addAttribute("cmanager",cmanager);
		return cmanager;
	}

	@RequestMapping(value = "/cfilter.do", method = RequestMethod.GET)
	public String openFilter(
			@ModelAttribute("clientfilter") FilterClient fc, ModelMap model) {

		if (fc == null) {
			fc = new FilterClient();
			LOG.info("New filterClient created in GET method");
			model.addAttribute("clientfilter", fc);
		} else {
			LOG.info("Found FilterClient in GET : ");
		}

		ViewClientFilter vf = new ViewClientFilter(
				((FilterClient) fc).getViewcompany(),
				((FilterClient) fc).getCondition());
		model.addAttribute("viewfilter", vf);
		return "client/cfilter";
	}

	@RequestMapping(value = "/cfilter.do", method = RequestMethod.POST)
	public String submitFilter(
			@ModelAttribute("viewfilter") ViewClientFilter viewfilter,
			@ModelAttribute("clientfilter") FilterClient fc,
			BindingResult result, SessionStatus status, ModelMap model) {

		if (fc == null) {
			fc = new FilterClient();
			System.out
					.println("New filterClient created in the POST method");
		} else {
			LOG.info("Found FilterClient in POST");
		}

		fc.loadViewcompany(viewfilter.getViewcompany());
		fc.loadCondition(viewfilter.getCondition());

		model.addAttribute("clientfilter", fc);
		return "client/cfilter";
	}
	

}
