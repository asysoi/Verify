package cci.web.controller.purchase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cci.model.Client;
import cci.model.cert.Company;
import cci.model.purchase.Product;
import cci.model.purchase.Purchase;
import cci.repository.SQLBuilder;
import cci.repository.client.SQLBuilderClient;
import cci.repository.purchase.SQLBuilderPurchase;
import cci.service.Filter;
import cci.service.FilterCondition;
import cci.service.client.FilterClient;
import cci.service.purchase.PurchaseService;
import cci.web.controller.HeaderTableView;
import cci.web.controller.ViewManager;
import cci.web.controller.client.ClientController;
import cci.web.controller.client.ViewClient;
import cci.web.validator.purchase.PurchaseValidator;

@Controller
@SessionAttributes({ "purchasefilter", "pmanager" })
public class PurchaseController {

	public static Logger LOG = LogManager.getLogger(PurchaseController.class);
	private PurchaseValidator purchaseValidator;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	public PurchaseController(PurchaseValidator purchaseValidator) {
		this.purchaseValidator = purchaseValidator;
	}

	// ----------------------------------------------------------------------------
	// Purchase List Pagination
	// ----------------------------------------------------------------------------	
	@RequestMapping(value="purchases.do")
	public String listpurchases (
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean onfilter,
			ModelMap model) {

		System.out
				.println("=========================== GET PURCHASES LIST =================================== >");

		ViewManager vmanager = (ViewManager) model.get("pmanager");

		if (vmanager == null) {
			vmanager = initViewManager(model);
		}

		if (orderby == null || orderby.isEmpty())
			orderby = "pchdate";
		if (order == null || order.isEmpty())
			order = ViewManager.ORDASC;
		if (onfilter == null)
			onfilter = false;

		vmanager.setPage(page == null ? 1 : page);
		vmanager.setPagesize(pagesize == null ? 10 : pagesize);
		vmanager.setOrderby(orderby);
		vmanager.setOrder(order);
		vmanager.setOnfilter(onfilter);
		vmanager.setUrl("purchases.do");

		Filter filter = null;
		if (onfilter) {
			filter = vmanager.getFilter();

			if (filter == null) {
				if (model.get("purchasefilter") != null) {
					filter = (Filter) model.get("purchasefilter");
				} else {
					filter = new FilterClient();
					model.addAttribute("purchasefilter", filter);
				}
				vmanager.setFilter(filter);
			}
		}

		SQLBuilder builder = new SQLBuilderPurchase();
		builder.setFilter(filter);
		vmanager.setPagecount(purchaseService.getViewPageCount(builder));
		List<ViewPurchase> purchases = purchaseService.readClientsPage(
				vmanager.getPage(), vmanager.getPagesize(),
				vmanager.getOrderby(), vmanager.getOrder(), builder);
		vmanager.setElements(purchases);

		model.addAttribute("pmanager", vmanager);
		model.addAttribute("purchases", purchases);
		model.addAttribute("next_page", vmanager.getNextPageLink());
		model.addAttribute("prev_page", vmanager.getPrevPageLink());
		model.addAttribute("last_page", vmanager.getLastPageLink());
		model.addAttribute("first_page", vmanager.getFirstPageLink());
		model.addAttribute("pages", vmanager.getPagesList());
		model.addAttribute("sizes", vmanager.getSizesList());
        	    
	    
		model.addAttribute("jspName", "pch/purchaselist.jsp");
		return "window";
	}
	
	private ViewManager initViewManager(ModelMap model) {
		ViewManager vmanager = new ViewManager();
		vmanager.setHnames(new String[] {"Дата закупки", "Товар", "Продавец", "Цена", "Объем", "Покупатель"});
		vmanager.setOrdnames(new String[] {"pchdate", "product", "company", "price", "volume", "department"});
		vmanager.setWidths(new int[] {15, 15, 20, 10, 10, 30});
		model.addAttribute("pmanager", vmanager);
		return vmanager;
	}

	
	// ----------------------------------------------------------------------------
	//
	// ----------------------------------------------------------------------------
	@RequestMapping(value="purchaseview.do")
	public String purchaseView(@RequestParam(value = "id", required = true) Long id,
						       @RequestParam(value = "popup", required = false) Boolean popup,	
	                          ModelMap model) {
        ViewPurchase purchaseView = purchaseService.readPurchaseView(id);
        model.addAttribute("purchase", purchaseView);
        
        if (popup!=null && popup) {
        	return "pch/purchaseviewpopup";
        } else {
        	model.addAttribute("jspName", "pch/purchaseview.jsp");
    		return "window";
        }
	}
	
	// ----------------------------------------------------------------------------
	//
	// ----------------------------------------------------------------------------
	@RequestMapping(value="purchaseadd.do", method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("purchase") ViewPurchase purchaseView,
		BindingResult result, SessionStatus status, ModelMap model) {
		
		purchaseValidator.validate(purchaseView, result);

		if (result.hasErrors()) {  
			model.addAttribute("jspName", "pch/purchaseform.jsp");
		} else {
			status.setComplete();
			purchaseService.savePurchase(purchaseView);
			model.addAttribute("jspName", "pch/purchaseview.jsp");
		}
		return "window";
	}

	// ----------------------------------------------------------------------------
	//
	// ----------------------------------------------------------------------------
	@RequestMapping(value="purchaseadd.do", method = RequestMethod.GET)
	public String initForm(ModelMap model) {

		ViewPurchase purchase = new ViewPurchase();
		model.addAttribute("purchase", purchase);
		model.addAttribute("jspName", "pch/purchaseform.jsp");
		return "window";
	}


	// ----------------------------------------------------------------------------
	// Initial Process
	// ----------------------------------------------------------------------------
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	// ----------------------------------------------------------------------------
	// Get Department List
	// ----------------------------------------------------------------------------
	@ModelAttribute("departmentList")
	public Map<Long, String>  populateDepartmentList() {
		
		for(Company company : purchaseService.readDepartments()) {
			departmentList.put(new Long((long) company.getId()), company.getOtd_name());	
		}
		return departmentList;
	}

	// ----------------------------------------------------------------------------
	// Get ProductList
	// ----------------------------------------------------------------------------
	@ModelAttribute("productList")
	public Map<Long, String> populateProductList() {
		
		for(Product product : purchaseService.readProducts()) {
			productList.put(new Long((long) product.getId()), product.getName());	
		}
		return productList;
	}
	
	// ----------------------------------------------------------------------------
	// Get Clients list
	// ----------------------------------------------------------------------------
	@ModelAttribute("companyList")
	public Map<Long, String> populateCompanyList() {

		for(Client client : purchaseService.readCompanies()) {
			companyList.put(new Long((long) client.getId()), client.getName());	
		}	
		return companyList;
	}
	
}