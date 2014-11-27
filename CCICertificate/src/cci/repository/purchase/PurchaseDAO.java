package cci.repository.purchase;

import java.util.List;
import java.util.Map;

import cci.model.purchase.Company;
import cci.model.purchase.Product;
import cci.model.purchase.Purchase;
import cci.service.purchase.FilterCondition;
import cci.web.controller.purchase.PurchaseView;

public interface PurchaseDAO {
    
	public List<Purchase> findNextPage(int page, int pagesize);

	public List<Product> findProducts();

	public List<Company> findDepartments();

	public List<Company> findCompanies();

	public Purchase findPurchaseByID(long id);

	public void savePurchase(Purchase purchase);
	
	public void updatePurchase(Purchase purchase);

	public void saveProduct(Product product);

	public void saveCompany(Company company);
	
	public PurchaseView findPurchaseViewByID(long id);
	
	public List<PurchaseView> findViewNextPage(int page, int pagesize, String orderby, String order, FilterCondition filter);

	public int  getViewPageCount(FilterCondition filter);   
}  

