package cci.service.purchase;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.model.Client;
import cci.model.cert.Company;
import cci.model.purchase.Product;
import cci.model.purchase.Purchase;
import cci.repository.SQLBuilder;
import cci.repository.purchase.PurchaseDAO;
import cci.service.FilterCondition;
import cci.web.controller.purchase.ViewPurchase;


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
		
	public List<Client> readCompanies() {
		Locale.setDefault(new Locale("en", "en"));

		List<Client> companies = null;
		
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


	public List<ViewPurchase> readPurchaseViewPage(int page, int pagesize, String sortby, String order, FilterCondition filter) {
		Locale.setDefault(new Locale("en", "en"));

		List<ViewPurchase> purchases = null;
		
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
	
	 
	public ViewPurchase readPurchaseView(Long id) {
		Locale.setDefault(new Locale("en", "en"));
		
		ViewPurchase purchase = null;
		try {
			purchase= purchaseDAO.findPurchaseViewByID(id);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
		
		return purchase;
	}


	public int getViewPageCount(SQLBuilder builder) {
		// TODO Auto-generated method stub
		return 0;
	}


	public List<ViewPurchase> readClientsPage(int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
