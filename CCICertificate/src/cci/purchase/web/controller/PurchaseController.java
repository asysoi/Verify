package cci.purchase.web.controller;

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

import cci.cert.model.Certificate;
import cci.cert.service.CERTService;
import cci.purchase.model.Company;
import cci.purchase.model.Product;
import cci.purchase.model.Purchase;
import cci.purchase.service.PurchaseService;
import cci.purchase.web.validator.PurchaseValidator;

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
			ModelMap model) {
        
		String[] hnames = {"Дата закупки", "Товар", "Покупатель", "Цена", "Объем", "Продавец"};
		String[] ordnames = {"pchdate", "product", "department", "price", "volume", "company"};
	    int[] widths = {15, 15, 40, 10, 10, 10};
		String ordasc = "asc";
        String orddesc = "desc";
        
		int page_index = (page == null ? 1 : page);
		int page_size = (pagesize == null ? 20 : pagesize);
		
		if (orderby == null || orderby.isEmpty()) orderby = "PCHDATE";
		if (order == null || order.isEmpty()) order = ordasc;
		
        List<HeaderTableView> headers = new ArrayList<HeaderTableView>(); 
        for (int i = 0; i < widths.length ; i++) {
        	if (ordnames[i].equals(orderby)) {
   	           headers.add(makeHeaderTableView(widths[i], hnames[i], ordnames[i], order.equals(ordasc) ? orddesc : ordasc, true));
        	} else {
        	   headers.add(makeHeaderTableView(widths[i], hnames[i], ordnames[i], ordasc, false)); 	
        	}
        }
		
		List<PurchaseView> purchases= purchaseService.readPurchaseViewPage(page_index, page_size, orderby, order);
		model.addAttribute("purchases", purchases);
		model.addAttribute("headers", headers);
		model.addAttribute("orderby", orderby);
		model.addAttribute("order", order);
		model.addAttribute("next_page", "purchaselist.do?page=" + (page_index + 1)+"&pagesize="+ page_size + "&sortby="+orderby+"&order="+order);
		model.addAttribute("prev_page", "purchaselist.do?page=" + (page_index - 1)+"&pagesize="+ page_size + "&sortby="+orderby+"&order="+order);
		model.addAttribute("jspName", "pch/purchaselist.jsp");
		return "window";
	}
	
	
	private HeaderTableView makeHeaderTableView(int width, String name,
			String orderby, String order, boolean selected) {
		
        HeaderTableView header = new HeaderTableView();
        header.setWidth(width);
        header.setName(name);
        header.setLink("purchaselist.do?orderby=" + orderby + "&order=" + order);
        header.setSelected(selected);
        header.setSelection(selected ? (order.equals("asc") ?  "▼" : "▲") : "");
		return header;
	}


	@RequestMapping(value="purchaseview.do")
	public String purchaseView(@RequestParam(value = "id", required = true) Long id,
	                          ModelMap model) {
        PurchaseView purchaseView = purchaseService.readPurchaseView(id);
        model.addAttribute("purchase", purchaseView);
        model.addAttribute("jspName", "pch/purchaseview.jsp");
		return "window";
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
			departmentList.put(new Long((long) company.getId()), company.getName());	
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
			companyList.put(new Long((long) company.getId()), company.getName());	
		}	

		return companyList;
	}
	
}