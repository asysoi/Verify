package cci.web.controller.client;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
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

import cci.config.client.ExportClientConfig;
import cci.model.Client;
import cci.repository.SQLBuilder;
import cci.repository.client.SQLBuilderClient;
import cci.service.Filter;
import cci.service.cert.CertFilter;
import cci.service.cert.XSLWriter;
import cci.service.client.ClientService;
import cci.service.client.ClientFilter;
import cci.web.controller.ViewManager;
import cci.web.validator.ClientValidator;

@Controller
@SessionAttributes({ "clientfilter", "cmanager" })
public class ClientController {

	private static final Logger LOG = Logger.getLogger(ClientController.class);
	private ClientValidator clientValidator;

	@Autowired
	private ClientService clientService;

	@Autowired
	public ClientController(ClientValidator clientValidator) {
		this.clientValidator = clientValidator;
	}

	// ---------------------------------------------------------------
	// Get Clients List
	// ---------------------------------------------------------------
	@RequestMapping(value = "clients.do", method = RequestMethod.GET)
	public String listclients(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			ModelMap model) {

		System.out
				.println("=========================== GET CLIENT LIST =================================== >");

		ViewManager cmanager = (ViewManager) model.get("cmanager");

		if (cmanager == null) {
			cmanager = initViewManager(model);
		}

		if (orderby == null || orderby.isEmpty())
			orderby = "name";
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
				if (model.get("clientfilter") != null) {
					filter = (Filter) model.get("clientfilter");
				} else {
					filter = new ClientFilter();
					model.addAttribute("clientfilter", filter);
				}
				cmanager.setFilter(filter);
			}
		}

		SQLBuilder builder = new SQLBuilderClient();
		builder.setFilter(filter);
		cmanager.setPagecount(clientService.getViewPageCount(builder));
		List<ViewClient> clients = clientService.readClientsPage(
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

		model.addAttribute("jspName", "client/clients_include.jsp");
		return "window";
	}

	  
	private ViewManager initViewManager(ModelMap model) {
		ViewManager cmanager = new ViewManager();
		cmanager.setHnames(new String[] { "Наименование компании", "Адрес",
				"УНП", "Банк", "Контактный телефон" });
		cmanager.setOrdnames(new String[] { "name", "address", "unp", "bname",
				"work_phone" });
		cmanager.setWidths(new int[] { 25, 20, 15, 20, 20 });
		model.addAttribute("cmanager", cmanager);
		return cmanager;
	}

	// ---------------------------------------------------------------
	// Get Clients List
	// ---------------------------------------------------------------
	@RequestMapping(value = "sclients.do", method = RequestMethod.GET)
	public String selectclients(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			@RequestParam(value = "clienttype", defaultValue = "client", required = false) String clienttype,
			ModelMap model) {

		System.out
				.println("=========================== GET CLIENT SELECTION =================================== >");

		ViewManager cmanager = (ViewManager) model.get("cmanager");

		if (cmanager == null) {
			cmanager = initViewManager(model);
		}

		if (orderby == null || orderby.isEmpty())
			orderby = "name";
		if (order == null || order.isEmpty())
			order = ViewManager.ORDASC;
		if (onfilter == null)
			onfilter = false;

		cmanager.setPage(page == null ? 1 : page);
		cmanager.setPagesize(pagesize == null ? 10 : pagesize);
		cmanager.setOrderby(orderby);
		cmanager.setOrder(order);
		cmanager.setOnfilter(onfilter);
		cmanager.setUrl("sclients.do");

		Filter filter = null;
		if (onfilter) {
			filter = cmanager.getFilter();

			if (filter == null) {
				if (model.get("clientfilter") != null) {
					filter = (Filter) model.get("clientfilter");
				} else {
					filter = new ClientFilter();
					model.addAttribute("clientfilter", filter);
				}
				cmanager.setFilter(filter);
			}
		}

		SQLBuilder builder = new SQLBuilderClient();
		builder.setFilter(filter);
		cmanager.setPagecount(clientService.getViewPageCount(builder));
		List<ViewClient> clients = clientService.readClientsPage(
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
		model.addAttribute("clienttype", clienttype);

		return "client/clients";
	}


	
	
	// ---------------------------------------------------------------
	// Get Client Filter Window
	// ---------------------------------------------------------------
	@RequestMapping(value = "clientfilter.do", method = RequestMethod.GET)
	public String openFilter(@ModelAttribute("clientfilter") ClientFilter fc,
			ModelMap model) {

		if (fc == null) {
			fc = new ClientFilter();
			LOG.debug("New filterClient created in GET method");
			model.addAttribute("clientfilter", fc);
		} else {
			LOG.debug("Found FilterClient in GET : ");
		}

		ViewClientFilter vf = new ViewClientFilter(
				((ClientFilter) fc).getViewclient(),
				((ClientFilter) fc).getCondition());
		model.addAttribute("viewfilter", vf);
		return "client/cfilter";
	}

	// ---------------------------------------------------------------
	// Set Client Filter properties
	// ---------------------------------------------------------------
	@RequestMapping(value = "clientfilter.do", method = RequestMethod.POST)
	public String submitFilter(
			@ModelAttribute("viewfilter") ViewClientFilter viewfilter,
			@ModelAttribute("clientfilter") ClientFilter fc,
			BindingResult result, SessionStatus status, ModelMap model) {

		if (fc == null) {
			fc = new ClientFilter();
			System.out.println("New filterClient created in the POST method");
		} else {
			LOG.debug("Found FilterClient in POST");
		}

		fc.loadViewclient(viewfilter.getViewclient());
		fc.loadCondition(viewfilter.getCondition());
		
		model.addAttribute("clientfilter", fc);
		return "client/cfilter";
	}

	// ---------------------------------------------------------------
	// View Client
	// ---------------------------------------------------------------
	@RequestMapping(value = "clientview.do")
	public String clientView(
			@RequestParam(value = "id", required = true) Long id, ModelMap model) {
		Client client = clientService.readClientView(id);
		model.addAttribute("client", client);

		return "client/clientview";
	}

	// ---------------------------------------------------------------
	// Add Client POST
	// ---------------------------------------------------------------
	@RequestMapping(value = "clientadd.do", method = RequestMethod.POST)
	public String addClient(@ModelAttribute("client") Client client,
			BindingResult result, SessionStatus status, ModelMap model) {

		// clientValidator.validate(client, result);

		// status.setComplete();
		clientService.saveClient(client);
		return "client/clientform";
	}

	// ---------------------------------------------------------------
	// Add Client GET
	// ---------------------------------------------------------------
	@RequestMapping(value = "clientadd.do", method = RequestMethod.GET)
	public String addClientInit(ModelMap model) {

		Client client = new Client();
		model.addAttribute("client", client);
		return "client/clientform";
	}

	// ---------------------------------------------------------------
	// Update Client POST
	// ---------------------------------------------------------------
	@RequestMapping(value = "clientedit.do", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("client") Client client,
			BindingResult result, SessionStatus status, ModelMap model) {

		// status.setComplete();
		clientService.updateClient(client);
		return "client/clientform";
	}

	// ---------------------------------------------------------------
	// Update Client GET
	// ---------------------------------------------------------------
	@RequestMapping(value = "clientedit.do", method = RequestMethod.GET)
	public String updateClientInit(
			@RequestParam(value = "id", required = true) Long id, ModelMap model) {

		Client client = clientService.readClient(id);

		model.addAttribute("client", client);
		return "client/clientform";
	}

	// ---------------------------------------------------------------
	// Export Client List to XSL
	// ---------------------------------------------------------------
	@RequestMapping(value = "clientsexport.do", method = RequestMethod.GET)
	public void exportClientsToExcel(HttpSession session,
			HttpServletResponse response, ModelMap model) {
		try {

			LOG.debug("Download started...");
			ViewManager vmanager = (ViewManager) model.get("cmanager");

			Filter filter = vmanager.getFilter();
			if (filter == null) {
				if (model.get("clientfilter") != null) {
					filter = (Filter) model.get("clientfilter");
				} else {
					filter = new CertFilter();
					model.addAttribute("clientfilter", filter);
				}
				vmanager.setFilter(filter);
			}

			ExportClientConfig dconfig = new ExportClientConfig();

			SQLBuilder builder = new SQLBuilderClient();
			builder.setFilter(filter);
			List clients = clientService.readClients(vmanager.getOrderby(),
					vmanager.getOrder(), builder);

			LOG.debug("Download. Clients loaded from database...");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition",
					"attachment; filename=companies.xlsx");

			(new XSLWriter()).makeWorkbook(clients, dconfig.getHeaders(),
					dconfig.getFields(), "Список контрагентов").write(
					response.getOutputStream());

			response.flushBuffer();
			LOG.debug("Download finished...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// Get Countries List
	// ---------------------------------------------------------------
	@ModelAttribute("countries")
	public Map<String, String> populateCountryList() {
		return clientService.getCountriesList("RU");
	}
}
