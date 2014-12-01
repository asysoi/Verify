package cci.repository.purchase;

import java.util.List;
import java.util.Map;

import cci.model.Client;
import cci.model.cert.Company;
import cci.model.purchase.Product;
import cci.model.purchase.Purchase;
import cci.service.FilterCondition;
import cci.web.controller.purchase.ViewPurchase;

public interface PurchaseDAO {
    
	public List<Purchase> findNextPage(int page, int pagesize);

	public List<Product> findProducts();

	public List<Company> findDepartments();

	public List<Client> findCompanies();

	public Purchase findPurchaseByID(long id);

	public void savePurchase(Purchase purchase);
	
	public void updatePurchase(Purchase purchase);

	public void saveProduct(Product product);

	public void saveCompany(Company company);
	
	public ViewPurchase findPurchaseViewByID(long id);
	
	public List<ViewPurchase> findViewNextPage(int page, int pagesize, String orderby, String order, FilterCondition filter);

	public int  getViewPageCount(FilterCondition filter);   
}  

