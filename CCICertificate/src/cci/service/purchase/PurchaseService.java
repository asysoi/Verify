package cci.service.purchase;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.model.Client;
import cci.model.cert.Company;
import cci.model.purchase.Product;
import cci.model.purchase.Purchase;
import cci.repository.SQLBuilder;
import cci.repository.purchase.PurchaseDAO;
import cci.web.controller.client.ViewClient;
import cci.web.controller.purchase.ViewPurchase;

@Component
public class PurchaseService {

	@Autowired
	private PurchaseDAO purchaseDAO;

	// ---------------------------------------------------------------
	// Get Purchases in list
	// ---------------------------------------------------------------
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

	// ---------------------------------------------------------------
	// Get Purchase object by ID
	// ---------------------------------------------------------------
	public Purchase readPurchase(long id) {
		Locale.setDefault(new Locale("en", "en"));

		Purchase purchase = null;
		try {
			purchase = purchaseDAO.findPurchaseByID(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return purchase;
	}

	// ---------------------------------------------------------------
	// Get View Purchase object by ID
	// ---------------------------------------------------------------
	public ViewPurchase readViewPurchase(Long id) {
		Locale.setDefault(new Locale("en", "en"));

		ViewPurchase purchase = null;
		try {
			purchase = purchaseDAO.findPurchaseViewByID(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return purchase;
	}

	// ---------------------------------------------------------------
	// Get Purchases count in list
	// ---------------------------------------------------------------
	public int getViewPurchasePageCount(SQLBuilder builder) {

		Locale.setDefault(new Locale("en", "en"));
		int counter = 0;
		try {
			counter = purchaseDAO.getViewPageCount(builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return counter;
	}

	// ---------------------------------------------------------------
	// Get Purchases in list
	// ---------------------------------------------------------------
	public List<ViewPurchase> readViewPurchasePage(int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<ViewPurchase> list = null;

		try {
			list = purchaseDAO.findViewNextPage(page, pagesize, orderby, order,
					builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

	// ---------------------------------------------------------------
	// save new Purchase
	// ---------------------------------------------------------------
	public void savePurchase(Purchase purchase) {

		Locale.setDefault(new Locale("en", "en"));

		try {
			purchaseDAO.savePurchase(purchase);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// Update Client
	// ---------------------------------------------------------------
	public void updatePurchase(Purchase purchase) {
		Locale.setDefault(new Locale("en", "en"));

		try {
			purchaseDAO.updateClient(purchase);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// save new product
	// ---------------------------------------------------------------
	public void saveProduct(Product product) {

		Locale.setDefault(new Locale("en", "en"));

		try {
			purchaseDAO.saveProduct(product);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// Save new company
	// ---------------------------------------------------------------
	public void saveCompany(Company company) {

		Locale.setDefault(new Locale("en", "en"));

		try {
			purchaseDAO.saveCompany(company);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// Get list of products
	// ---------------------------------------------------------------
	public Map<Long, String> readProducts() {
		Locale.setDefault(new Locale("en", "en"));

		Map<Long, String> products = null;

		try {
			products = purchaseDAO.findProducts();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return products;
	}

	// ---------------------------------------------------------------
	// Get list of departments
	// ---------------------------------------------------------------
	public Map<Long, String> readDepartments() {
		Locale.setDefault(new Locale("en", "en"));

		Map<Long, String> departments = null;

		try {
			departments = purchaseDAO.findDepartments();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return departments;
	}

	// ---------------------------------------------------------------
	// Get lidt of companies
	// ---------------------------------------------------------------
	public Map<Long, String> readCompanies() {
		Locale.setDefault(new Locale("en", "en"));

		Map<Long, String> companies = null;

		try {
			companies = purchaseDAO.findCompanies();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return companies;
	}

	// ---------------------------------------------------------------
	// Get CLients List
	// ---------------------------------------------------------------
	public List<ViewPurchase> readPurchases(String orderby, String order,
			SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<ViewPurchase> items = null;

		try {
			items = purchaseDAO.readViewPurchases(orderby, order, builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return items;
	}

}
