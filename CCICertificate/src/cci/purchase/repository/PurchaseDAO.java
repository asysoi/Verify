package cci.purchase.repository;

import java.util.List;
import java.util.Map;

import cci.purchase.model.Product;
import cci.purchase.model.Company;
import cci.purchase.model.Purchase;
import cci.purchase.service.FilterCondition;
import cci.purchase.web.controller.PurchaseView;

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

