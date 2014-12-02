package cci.repository.purchase;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import cci.model.Client;
import cci.model.cert.Company;
import cci.model.purchase.Product;
import cci.model.purchase.Purchase;
import cci.repository.SQLBuilder;
import cci.web.controller.client.ViewClient;
import cci.web.controller.purchase.ViewPurchase;

public class JDBCPurchaseDAO implements PurchaseDAO {

	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	// ---------------------------------------------------------------
	// Get map of products
	// ---------------------------------------------------------------
	public Map<Long, String> findProducts() {
		String sql = "select * from PCH_PRODUCT ORDER BY id";

		Map<Long, String> items = new LinkedHashMap<Long, String>();

		List<Product> list = template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Product>(Product.class));

		for (Product item : list) {
			items.put(item.getId(), item.getName());
		}
		return items;
	}

	// ---------------------------------------------------------------
	// Get map of departments
	// ---------------------------------------------------------------
	public Map<Long, String> findDepartments() {
		String sql = "select * from C_OTD ORDER BY id";

		Map<Long, String> items = new LinkedHashMap<Long, String>();

		List<Company> list = template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Company>(Company.class));

		for (Company item : list) {
			items.put(item.getId(), item.getOtd_name());
		}
		return items;
	}

	// ---------------------------------------------------------------
	// Get map of clients
	// ---------------------------------------------------------------
	public Map<Long, String> findCompanies() {
		String sql = "select * from CCI_CLIENT ORDER BY id";

		Map<Long, String> items = new LinkedHashMap<Long, String>();

		List<Client> list = template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Client>(Client.class));

		for (Client item : list) {
			items.put(item.getId(), item.getName());
		}
		return items;
	}

	// ---------------------------------------------------------------
	// Get next page of purchase list
	// ---------------------------------------------------------------
	public List<Purchase> findNextPage(int page, int pagesize) {
		String sql = " SELECT purchase.* "
				+ " FROM (SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.PCHDATE) rw FROM PCH_PURCHASE t) purchase "
				+ " WHERE purchase.rw > " + ((page - 1) * pagesize)
				+ " AND purchase.rw <= " + (page * pagesize);

		System.out.println("SQL get next    page : " + sql);
		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Purchase>(Purchase.class));
	}

	// ---------------------------------------------------------------
	// Get next page of view purchase list
	// ---------------------------------------------------------------
	public List<ViewPurchase> findViewNextPage(int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {

		String sql = " SELECT purchase.* "
				+ " FROM (SELECT t.*, ROW_NUMBER() OVER " + " (ORDER BY t."
				+ orderby + " " + order + ") rw "
				+ " FROM PCH_PURCHASE_VIEW t " + builder.getWhereClause()
				+ " )" + " purchase " + " WHERE purchase.rw > "
				+ ((page - 1) * pagesize) + " AND purchase.rw <= "
				+ (page * pagesize);

		System.out.println("SQL get next    page : " + sql);

		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<ViewPurchase>(ViewPurchase.class));
	}

	// ---------------------------------------------------------------
	// Get count items in view purchase list
	// ---------------------------------------------------------------
	public int getViewPageCount(SQLBuilder builder) {
		String sql = "SELECT count(*) FROM PCH_PURCHASE_VIEW "
				+ builder.getWhereClause();

		return this.template.getJdbcOperations().queryForInt(sql);
	}

	// ---------------------------------------------------------------
	// Get purchase by id
	// ---------------------------------------------------------------
	public Purchase findPurchaseByID(long id) {
		Purchase item = null;

		try {
			String sql = "select * from PCH_PURCHASE WHERE id = ?";
			item = template.getJdbcOperations().queryForObject(sql,
					new Object[] { id },
					new BeanPropertyRowMapper<Purchase>(Purchase.class));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	// ---------------------------------------------------------------
	// Get view purchase by id
	// ---------------------------------------------------------------
	public ViewPurchase findPurchaseViewByID(long id) {
		ViewPurchase item = null;

		try {
			String sql = "select * from PCH_PURCHASE_VIEW WHERE id = ?";
			item = template.getJdbcOperations()
					.queryForObject(
							sql,
							new Object[] { id },
							new BeanPropertyRowMapper<ViewPurchase>(
									ViewPurchase.class));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	// ---------------------------------------------------------------
	// Add new purchase in database
	// ---------------------------------------------------------------
	public void savePurchase(Purchase purchase) {
		String sql = "insert into pch_purchase (id,id_product,id_otd,id_company,price,volume,unit, pchdate, productproperty) "
				+ "values (id_purchase_seq.nextval, "
				+ ":id_product, :id_otd, :id_company, :price, :volume, "
				+ ":unit, :pchdate, :productproperty)";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				purchase);

		try {
			template.update(sql, parameters);
		} catch (Exception ex) {
			System.out.println("Error - save purchase: " + ex.toString());
		}
	}
	
	// ---------------------------------------------------------------
	// Update purchase in database
	// ---------------------------------------------------------------
	public void updateClient(Purchase purchase) {
		String sql = "update pch_purchase set "
				+ " id_product = :id_product, id_otd = :id_otd, id_company = :id_company, price = :price, "
				+ " volume = :volume, unit=:unit, pchdate =:pchdate, productproperty:=productproperty " 
				+ " where id = :id";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				purchase);

		try {
			template.update(sql, parameters);
		} catch (Exception ex) {
			System.out.println("Error - save purchase: " + ex.toString());
		}
	}

	// ---------------------------------------------------------------
	// Get list of all filtered purchases 
	// ---------------------------------------------------------------
	public List<ViewPurchase> readViewPurchases(String orderby, String order,
			SQLBuilder builder) {
		
		String sql = " SELECT * FROM PCH_PURCHASE_VIEW " 
				+ builder.getWhereClause() + " ORDER BY " +  orderby + " " + order;

		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<ViewPurchase>(ViewPurchase.class));
	}

	
	
	
	@Override
	public void updatePurchase(Purchase purchase) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveProduct(Product product) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveCompany(Company company) {
		// TODO Auto-generated method stub
	}


}
