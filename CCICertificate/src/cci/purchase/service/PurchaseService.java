package cci.purchase.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.purchase.model.Product;
import cci.purchase.model.Company;
import cci.purchase.model.Purchase;
import cci.purchase.repository.PurchaseDAO;
import cci.purchase.web.controller.PurchaseView;


@Component
public class PurchaseService {

	@Autowired
	private PurchaseDAO  purchaseDAO;
		
	public List<Purchase> readPurchasePage(int page, int pagesize) {
		Locale.setDefault(new Locale("en", "en"));

		List<Purchase> purchases = null;
		
		try {
			purchases = purchaseDAO.findNextPage(page, pagesize);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return purchases;
	}
	
	
	public List<Product> readProducts() {
		Locale.setDefault(new Locale("en", "en"));

		List<Product> products = null;
		
		try {
			products = purchaseDAO.findProducts();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return products;
	}
	

	public List<Company> readDepartments() {
		Locale.setDefault(new Locale("en", "en"));

		List<Company> departments = null;
		
		try {
			departments = purchaseDAO.findDepartments();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return departments;
	}
		
	public List<Company> readCompanies() {
		Locale.setDefault(new Locale("en", "en"));

		List<Company> companies = null;
		
		try {
			companies = purchaseDAO.findCompanies();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return companies;
	}
		
	
	
	public Purchase readPurchase(long id) {
		Locale.setDefault(new Locale("en", "en"));
		
		Purchase purchase = null;
		try {
			purchase= purchaseDAO.findPurchaseByID(id);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
		
		return purchase;
	}
	
    public void savePurchase(Purchase purchase) {
		
		Locale.setDefault(new Locale("en", "en"));

		try {
			purchaseDAO.savePurchase(purchase);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
	}
	
	
	
	public void saveProduct(Product product) {
		
		Locale.setDefault(new Locale("en", "en"));

		try {
			purchaseDAO.saveProduct(product);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
	}

	public void saveCompany(Company company) {
		
		Locale.setDefault(new Locale("en", "en"));

		try {
			purchaseDAO.saveCompany(company);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
	}


	public List<PurchaseView> readPurchaseViewPage(int page, int pagesize, String sortby, String order, FilterCondition filter) {
		Locale.setDefault(new Locale("en", "en"));

		List<PurchaseView> purchases = null;
		
		try {
			purchases = purchaseDAO.findViewNextPage(page, pagesize, sortby, order, filter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return purchases;
	}
	
	public int getPurchaseViewPageCount(FilterCondition filter) {
		Locale.setDefault(new Locale("en", "en"));
        int counter=0;
		try {
			counter = purchaseDAO.getViewPageCount(filter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return counter;
	}
	
	 
	public PurchaseView readPurchaseView(Long id) {
		Locale.setDefault(new Locale("en", "en"));
		
		PurchaseView purchase = null;
		try {
			purchase= purchaseDAO.findPurchaseViewByID(id);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
		
		return purchase;
	}
	
}
