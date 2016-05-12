package cci.repository.purchase;

import java.util.List;
import java.util.Map;

import cci.model.Client;
import cci.model.cert.Company;
import cci.model.purchase.Product;
import cci.model.purchase.Purchase;
import cci.repository.SQLBuilder;
import cci.service.FilterCondition;
import cci.web.controller.purchase.ViewPurchase;

public interface PurchaseDAO {
    
	public List<Purchase> findNextPage(int page, int pagesize);

	public Map<Long, String> findProducts();

	public Map<Long, String> findDepartments();

	public Map<Long, String> findCompanies();

	public Purchase findPurchaseByID(long id);

	public void savePurchase(Purchase purchase);
	
	public void updatePurchase(Purchase purchase);

	public long saveProduct(Product product);

	public void saveCompany(Company company);
	
	public ViewPurchase findPurchaseViewByID(long id);
	
	public List<ViewPurchase> findViewNextPage(int page, int pagesize, String orderby, String order, SQLBuilder builder);

	public int  getViewPageCount(SQLBuilder builder);

	public List<ViewPurchase> readViewPurchases(String orderby, String order,
			SQLBuilder builder);   
}  

