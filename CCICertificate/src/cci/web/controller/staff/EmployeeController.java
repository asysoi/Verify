package cci.web.controller.staff;

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

import cci.config.staff.ExportEmployeeConfig;
import cci.model.Employee;
import cci.repository.SQLBuilder;
import cci.repository.staff.SQLBuilderEmployee;
import cci.service.Filter;
import cci.service.cert.XSLWriter;
import cci.service.staff.EmployeeFilter;
import cci.service.staff.EmployeeService;
import cci.web.controller.ViewManager;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@Controller
@SessionAttributes({ "employeemanager", "employeefilter", "employee"})
public class EmployeeController {
	
	private static final Logger LOG = Logger.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;


	// ---------------------------------------------------------------
	// Get Employee List
	// ---------------------------------------------------------------
	@RequestMapping(value = "employees.do", method = RequestMethod.GET)
	public String listemployees(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			ModelMap model) {

		LOG.info("=========================== GET STAFF LIST =================================== >");

		ViewManager viewmanager = (ViewManager) model.get("employeemanager");

		if (viewmanager == null) {
			viewmanager = initViewManager(model);
		}

		if (orderby == null || orderby.isEmpty())
			orderby = "name";
		if (order == null || order.isEmpty())
			order = ViewManager.ORDASC;
		if (onfilter == null)
			onfilter = false;

		viewmanager.setPage(page == null ? 1 : page);
		viewmanager.setPagesize(pagesize == null ? 10 : pagesize);
		viewmanager.setOrderby(orderby);
		viewmanager.setOrder(order);
		viewmanager.setOnfilter(onfilter);
		viewmanager.setUrl("employees.do");

		Filter filter = null;
		if (onfilter) {
			filter = viewmanager.getFilter();

			if (filter == null) {
				if (model.get("employeefilter") != null) {
					filter = (Filter) model.get("employeefilter");
				} else {
					filter = new EmployeeFilter();
					model.addAttribute("employeefilter", filter);
				}
				viewmanager.setFilter(filter);
			}
		}

		SQLBuilder builder = new cci.repository.staff.SQLBuilderEmployee();
		builder.setFilter(filter);
		viewmanager.setPagecount(employeeService.getViewPageCount(builder));
		List<ViewEmployee> employees = employeeService.readEmployeesPage(
				viewmanager.getPage(), viewmanager.getPagesize(),
				viewmanager.getOrderby(), viewmanager.getOrder(), builder);
		viewmanager.setElements(employees);

		model.addAttribute("viewmanager", viewmanager);
		model.addAttribute("employees", employees);
		model.addAttribute("next_page", viewmanager.getNextPageLink());
		model.addAttribute("prev_page", viewmanager.getPrevPageLink());
		model.addAttribute("last_page", viewmanager.getLastPageLink());
		model.addAttribute("first_page", viewmanager.getFirstPageLink());
		model.addAttribute("pages", viewmanager.getPagesList());
		model.addAttribute("sizes", viewmanager.getSizesList());

		model.addAttribute("jspName", "employee/employee_include.jsp");
		return "window";
	}

	  
	private ViewManager initViewManager(ModelMap model) {
		ViewManager viewmanager = new ViewManager();
		viewmanager.setHnames(new String[] { "ФИО", "Должность",
				"Структурное подразделение", "Отделение", "Телефон" });
		viewmanager.setOrdnames(new String[] { "name", "job", "departmentname", "otd_name",
				"phone" });
		viewmanager.setWidths(new int[] { 25, 25, 25, 15, 10 });
		model.addAttribute("viewmanager", viewmanager);
		return viewmanager;
	}

	// ---------------------------------------------------------------
	// Get Employee List
	// ---------------------------------------------------------------
	@RequestMapping(value = "selemployees.do", method = RequestMethod.GET)
	public String selectemployees(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			@RequestParam(value = "employeetype", defaultValue = "employee", required = false) String employeetype,
			ModelMap model) {

		LOG.info("=========================== GET STAFF LIST =================================== >");
		
		ViewManager viewmanager = (ViewManager) model.get("viewmanager");

		if (viewmanager == null) {
			viewmanager = initViewManager(model);
		}

		if (orderby == null || orderby.isEmpty())
			orderby = "name";
		if (order == null || order.isEmpty())
			order = ViewManager.ORDASC;
		if (onfilter == null)
			onfilter = false;

		viewmanager.setPage(page == null ? 1 : page);
		viewmanager.setPagesize(pagesize == null ? 10 : pagesize);
		viewmanager.setOrderby(orderby);
		viewmanager.setOrder(order);
		viewmanager.setOnfilter(onfilter);
		viewmanager.setUrl("semployees.do");

		Filter filter = null;
		if (onfilter) {
			filter = viewmanager.getFilter();

			if (filter == null) {
				if (model.get("employeefilter") != null) {
					filter = (Filter) model.get("employeefilter");
				} else {
					filter = new EmployeeFilter();
					model.addAttribute("employeefilter", filter);
				}
				viewmanager.setFilter(filter);
			}
		}

		SQLBuilder builder = new SQLBuilderEmployee();
		builder.setFilter(filter);
		viewmanager.setPagecount(employeeService.getViewPageCount(builder));
		List<ViewEmployee> employees = employeeService.readEmployeesPage(
				viewmanager.getPage(), viewmanager.getPagesize(),
				viewmanager.getOrderby(), viewmanager.getOrder(), builder);
		viewmanager.setElements(employees);

		model.addAttribute("viewmanager", viewmanager);
		model.addAttribute("employees", employees);
		model.addAttribute("next_page", viewmanager.getNextPageLink());
		model.addAttribute("prev_page", viewmanager.getPrevPageLink());
		model.addAttribute("last_page", viewmanager.getLastPageLink());
		model.addAttribute("first_page", viewmanager.getFirstPageLink());
		model.addAttribute("pages", viewmanager.getPagesList());
		model.addAttribute("sizes", viewmanager.getSizesList());
		model.addAttribute("employeetype", employeetype);

		return "employee/employee";
	}


	
	
	// ---------------------------------------------------------------
	// Get Employee Filter Window
	// ---------------------------------------------------------------
	@RequestMapping(value = "employeefilter.do", method = RequestMethod.GET)
	public String openFilter(@ModelAttribute("employeefilter") EmployeeFilter fc,
			ModelMap model) {

		if (fc == null) {
			fc = new EmployeeFilter();
			LOG.debug("New filterEmployee created in GET method");
			model.addAttribute("employeefilter", fc);
		} else {
			LOG.debug("Found FilterEmployee in GET : ");
		}

		ViewEmployeeFilter vf = new ViewEmployeeFilter(
				((EmployeeFilter) fc).getViewemployee(),
				((EmployeeFilter) fc).getCondition());
		model.addAttribute("viewfilter", vf);
		return "staff/cfilter";
	}

	// ---------------------------------------------------------------
	// Set Employee Filter properties
	// ---------------------------------------------------------------
	@RequestMapping(value = "employeefilter.do", method = RequestMethod.POST)
	public String submitFilter(
			@ModelAttribute("viewfilter") ViewEmployeeFilter viewfilter,
			@ModelAttribute("employeefilter") EmployeeFilter fc,
			BindingResult result, SessionStatus status, ModelMap model) {

		if (fc == null) {
			fc = new EmployeeFilter();
			System.out.println("New filterEmployee created in the POST method");
		} else {
			LOG.debug("Found FilterEmployee in POST");
		}

		fc.loadViewEmployee(viewfilter.getViewemployee());
		fc.loadCondition(viewfilter.getCondition());

		model.addAttribute("employeefilter", fc);
		return "staff/cfilter";
	}

	// ---------------------------------------------------------------
	// View Employee
	// ---------------------------------------------------------------
	@RequestMapping(value = "employeeview.do")
	public String employeeView(
			@RequestParam(value = "id", required = true) Long id, ModelMap model) {
		Employee employee = employeeService.readEmployeeView(id);
		model.addAttribute("employee", employee);

		return "staff/employeeview";
	}

	// ---------------------------------------------------------------
	// Add Employee POST
	// ---------------------------------------------------------------
	@RequestMapping(value = "employeeadd.do", method = RequestMethod.POST)
	public String addEmployee(@ModelAttribute("employee") Employee employee,
			BindingResult result, SessionStatus status, ModelMap model) {

		// status.setComplete();
		employeeService.saveEmployee(employee);
		return "staff/employeeform";
	}

	// ---------------------------------------------------------------
	// Add Employee GET
	// ---------------------------------------------------------------
	@RequestMapping(value = "employeeadd.do", method = RequestMethod.GET)
	public String addEmployeeInit(ModelMap model) {

		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "staff/employeeform";
	}

	// ---------------------------------------------------------------
	// Update Employee POST
	// ---------------------------------------------------------------
	@RequestMapping(value = "employeeedit.do", method = RequestMethod.POST)
	public String updateEmployee(@ModelAttribute("employee") Employee employee,
			BindingResult result, SessionStatus status, ModelMap model) {

		// status.setComplete();
		employeeService.updateEmployee(employee);
		return "staff/employeeform";
	}

	// ---------------------------------------------------------------
	// Update Employee GET
	// ---------------------------------------------------------------
	@RequestMapping(value = "employeeedit.do", method = RequestMethod.GET)
	public String updateEmployeeInit(
			@RequestParam(value = "id", required = true) Long id, ModelMap model) {

		Employee employee = employeeService.readEmployee(id);

		model.addAttribute("employee", employee);
		return "staff/employeeform";
	}

	// ---------------------------------------------------------------
	// Export Employee List to XSL
	// ---------------------------------------------------------------
	@RequestMapping(value = "employeesexport.do", method = RequestMethod.GET)
	public void exportEmployeesToExcel(HttpSession session,
			HttpServletResponse response, ModelMap model) {
		try {

			LOG.debug("Download started...");
			ViewManager vmanager = (ViewManager) model.get("employeemanager");

			Filter filter = vmanager.getFilter();
			if (filter == null) {
				if (model.get("employeefilter") != null) {
					filter = (Filter) model.get("employeefilter");
				} else {
					filter = new EmployeeFilter();
					model.addAttribute("employeefilter", filter);
				}
				vmanager.setFilter(filter);
			}

			ExportEmployeeConfig dconfig = new ExportEmployeeConfig();

			SQLBuilder builder = new SQLBuilderEmployee();
			builder.setFilter(filter);
			List employees = employeeService.readEmployees(vmanager.getOrderby(),
					vmanager.getOrder(), builder);

			LOG.debug("Download. Employees loaded from database...");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition",
					"attachment; filename=companies.xlsx");

			(new XSLWriter()).makeWorkbook(employees, dconfig.getHeaders(),
					dconfig.getFields(), "Список контрагентов").write(
					response.getOutputStream());

			response.flushBuffer();
			LOG.debug("Download finished...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
