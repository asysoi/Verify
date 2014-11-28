package cci.web.controller.purchase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.support.SessionStatus;

import cci.model.purchase.Company;
import cci.model.purchase.Product;
import cci.model.purchase.Purchase;
import cci.service.FilterCondition;
import cci.service.purchase.PurchaseService;
import cci.web.validator.purchase.PurchaseValidator;

@Controller
public class PurchaseController {

	private PurchaseValidator purchaseValidator;
	private Map<Long, String> departmentList = new LinkedHashMap<Long, String>();
	private Map<Long, String> productList = new LinkedHashMap<Long, String>();
	private Map<Long, String> companyList = new LinkedHashMap<Long, String>();
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	public PurchaseController(PurchaseValidator purchaseValidator) {
		this.purchaseValidator = purchaseValidator;
	}
	
	
	@RequestMapping(value="purchaselist.do")
	public String purchaseList(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "orderby", required = false) String orderby,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "filter", required = false) Boolean filter,			
			@RequestParam(value = "filterfield", required = false) String filterfield,
			@RequestParam(value = "filteroperator", required = false) String filteroperator,
			@RequestParam(value = "filtervalue", required = false) String filtervalue,
			ModelMap model) {
        
		String[] hnames = {"Дата закупки", "Товар", "Продавец", "Цена", "Объем", "Покупатель"};
		String[] ordnames = {"pchdate", "product", "company", "price", "volume", "department"};
	    int[] widths = {15, 15, 20, 10, 10, 30};
		String ordasc = "asc";
        String orddesc = "desc";
        
		int page_index = (page == null ? 1 : page);
		int page_size = (pagesize == null ? 20 : pagesize);
		
		if (orderby == null || orderby.isEmpty()) orderby = "pchdate";
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
		
		model.addAttribute("jspName", "pch/purchaselist.jsp");
		return "window";
	}
	
	
	private FilterCondition getFilter(Boolean filter, String field,
			String operator, String value) {
		FilterCondition pf  = new FilterCondition();
		pf.setField(field);
		pf.setOperator(operator);
		pf.setValue(value);
		pf.setOnfilter(filter);
		return pf;
	}


	private List<Integer> getSizesList() {
		List<Integer> sizes = new ArrayList<Integer>();
        sizes.add(new Integer(5));
        sizes.add(new Integer(10));
        sizes.add(new Integer(15));
        sizes.add(new Integer(20));
        sizes.add(new Integer(50));
		return sizes;
	}


	private List<Integer> getPagesList(int page, int page_size, int prcount) {
		List<Integer> pages = new ArrayList<Integer>();
		
		int pagelast = (prcount + (page_size -1))/page_size;
		int pagestart = page - 5; 
		if (pagestart < 1) pagestart = 1;
		
		int pageend = pagestart + 9;
		if (pageend > pagelast) pageend = pagelast;
		if (pagelast - 9 < pagestart && pagelast > 10) pagestart = pagelast - 9; 
		
		for (int i = pagestart; i <= pageend; i++) {
			pages.add(new Integer(i));
		}
        return pages;
	}


	private String getPrevPageLink(int page_index, int page_size, int prcount, String orderby, String order) {
		String link = "#";
		
		if (page_index > 1) {
		   link  = "purchaselist.do?page=" + (page_index -1)+"&pagesize="+ page_size + "&orderby="+orderby+"&order="+order;
		} 
 
		return link;
	}
	
	private String getNextPageLink(int page_index, int page_size, int prcount, String orderby, String order) {
		String link = "#";
		
	    if ((page_size * page_index) < prcount) {
		   link  = "purchaselist.do?page=" + (page_index + 1)+"&pagesize="+ page_size + "&orderby="+orderby+"&order="+order;
	    }
 
		return link;
	}


	private HeaderTableView makeHeaderTableView(int width, String name, int page, int pagesize,
			String orderby, String order, boolean selected) {
		
        HeaderTableView header = new HeaderTableView();
        header.setWidth(width);
        header.setName(name);
        header.setDbfield(orderby);
        header.setLink("purchaselist.do?pagesize=" + pagesize + "&orderby=" + orderby + "&order=" + order);
        header.setSelected(selected);
        header.setSelection(selected ? (order.equals("asc") ?  "▲" : "▼") : "");
		return header;
	}


	@RequestMapping(value="purchaseview.do")
	public String purchaseView(@RequestParam(value = "id", required = true) Long id,
						       @RequestParam(value = "popup", required = false) Boolean popup,	
	                          ModelMap model) {
        PurchaseView purchaseView = purchaseService.readPurchaseView(id);
        model.addAttribute("purchase", purchaseView);
        
        if (popup!=null && popup) {
        	return "pch/purchaseviewpopup";
        } else {
        	model.addAttribute("jspName", "pch/purchaseview.jsp");
    		return "window";
        }
	}
	

	@RequestMapping(value="purchaseadd.do", method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("purchase") PurchaseView purchaseView,
		BindingResult result, SessionStatus status, ModelMap model) {
		
		purchaseValidator.validate(purchaseView, result);

		if (result.hasErrors()) {  
			model.addAttribute("jspName", "pch/purchaseform.jsp");
		} else {
			status.setComplete();
			purchaseService.savePurchase(pupulatePurchase(purchaseView));
			populatePurchaseViewByNames(purchaseView);
			model.addAttribute("jspName", "pch/purchaseview.jsp");
		}
		return "window";
	}


	@RequestMapping(value="purchaseadd.do", method = RequestMethod.GET)
	public String initForm(ModelMap model) {

		PurchaseView purchase = new PurchaseView();
		model.addAttribute("purchase", purchase);
		model.addAttribute("jspName", "pch/purchaseform.jsp");
		return "window";
	}


	private PurchaseView pupulatePurchaseView(Purchase purchase) {
		PurchaseView purchaseView = new PurchaseView();
		purchaseView.setPchDate(purchaseView.getPchDate());
		purchaseView.setPrice(purchaseView.getPrice());
		purchaseView.setUnit(purchaseView.getUnit());
		purchaseView.setVolume(purchaseView.getVolume());
		purchaseView.setProductProperty(purchaseView.getProductProperty());
		purchaseView.setId_otd(purchaseView.getId_otd());
		purchaseView.setId_product(purchaseView.getId_product());
		purchaseView.setId_company(purchaseView.getId_company());
		populatePurchaseViewByNames(purchaseView);
		return purchaseView;
	}
	
	private void populatePurchaseViewByNames(PurchaseView purchaseView) {
          purchaseView.setProduct(productList.get(new Long(purchaseView.getId_product())));
          purchaseView.setCompany(companyList.get(new Long(purchaseView.getId_company())));
          purchaseView.setDepartment(departmentList.get(new Long(purchaseView.getId_otd())));
	}
	
	private Purchase pupulatePurchase(PurchaseView purchaseView) {
		Purchase purchase = new Purchase();
		purchase.setPchDate(purchaseView.getPchDate());
		purchase.setPrice(purchaseView.getPrice());
		purchase.setUnit(purchaseView.getUnit());
		purchase.setVolume(purchaseView.getVolume());
		purchase.setProductProperty(purchaseView.getProductProperty());
		purchase.setId_otd(new Long(purchaseView.getId_otd()));
		purchase.setId_product(new Long(purchaseView.getId_product()));
		purchase.setId_company(new Long(purchaseView.getId_company()));
		System.out.println(purchaseView);
		System.out.println(purchase);
		return purchase;
	}


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@ModelAttribute("departmentList")
	public Map<Long, String>  populateDepartmentList() {
		
		for(Company company : purchaseService.readDepartments()) {
			departmentList.put(new Long((long) company.getId()), company.getOtd_name());	
		}
		return departmentList;
	}

	@ModelAttribute("productList")
	public Map<Long, String> populateProductList() {
		
		for(Product product : purchaseService.readProducts()) {
			productList.put(new Long((long) product.getId()), product.getName());	
		}
		return productList;
	}
	
	
	@ModelAttribute("companyList")
	public Map<Long, String> populateCompanyList() {

		for(Company company : purchaseService.readCompanies()) {
			companyList.put(new Long((long) company.getId()), company.getOtd_name());	
		}	

		return companyList;
	}
	
}