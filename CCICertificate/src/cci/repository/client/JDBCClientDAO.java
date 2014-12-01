package cci.repository.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import cci.model.Client;
import cci.model.cert.Certificate;
import cci.model.cert.Company;
import cci.model.cert.Country;
import cci.repository.SQLBuilder;
import cci.service.client.ClientService;
import cci.web.controller.client.ViewClient;
import cci.web.controller.purchase.PurchaseView;

@Repository
public class JDBCClientDAO implements ClientDAO {
	public static Logger LOG=LogManager.getLogger(JDBCClientDAO.class);
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	// ---------------------------------------------------------------
	// Get the page of clients
	// ---------------------------------------------------------------
	public List<ViewClient> findViewNextPage(int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {

		String sql = " SELECT client.* "
				+ " FROM (SELECT t.*, ROW_NUMBER() OVER " + " (ORDER BY t."
				+ orderby + " " + order + ", t.ID " + order + ") rw "
				+ " FROM CLIENT_VIEW t " + builder.getWhereClause() + " )"
				+ " client " + " WHERE client.rw > " + ((page - 1) * pagesize)
				+ " AND client.rw <= " + (page * pagesize);

		System.out.println("Next page : " + sql);

		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<ViewClient>(ViewClient.class));
	}

	// ---------------------------------------------------------------
	// Get count clients in the list 
	// ---------------------------------------------------------------
	public int getViewPageCount(SQLBuilder builder) {
		long start = System.currentTimeMillis();

		String sql = "SELECT count(*) FROM CLIENT_VIEW "
				+ builder.getWhereClause();

		int count = this.template.getJdbcOperations().queryForInt(sql);

		System.out.println(sql);
		System.out.println("Query time: "
				+ (System.currentTimeMillis() - start));

		return count;
	}

	// ---------------------------------------------------------------
	// Get list of countries
	// ---------------------------------------------------------------
	public Map<String, String> getCountriesList() {
		String sql = "SELECT * from C_COUNTRY ORDER BY NAME";

		Map<String, String> countries = new LinkedHashMap<String, String>();

		List<Country> list = template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Country>(Country.class));

		for (Country cntry : list) {
			countries.put(cntry.getCode(), cntry.getName());
		}
		System.out.println("Got country list");
		return countries;
	}

	// ---------------------------------------------------------------
	// Get View Client
	// ---------------------------------------------------------------
	public ViewClient findClientViewByID(Long id) {

		ViewClient item = null;
		try {
			String sql = "select * from CLIENT_VIEW WHERE id = ?";
			item = template.getJdbcOperations().queryForObject(sql,
					new Object[] { id },
					new BeanPropertyRowMapper<ViewClient>(ViewClient.class));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}
	
	// ---------------------------------------------------------------
	// Get Client
	// ---------------------------------------------------------------
	public Client findClientByID(Long id) {

		Client item = null;
		try {
			String sql = "select * from CCI_CLIENT WHERE id = ?";
			item = template.getJdbcOperations().queryForObject(sql,
					new Object[] { id },
					new BeanPropertyRowMapper<Client>(Client.class));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	// ---------------------------------------------------------------
	// Save/Add Client
	// ---------------------------------------------------------------
	public void saveClient(Client client) {
		String sql = "insert into cci_client "
				+ "(id, "
				+ "name, city, line,cindex,office,building,work_phone,cell_phone,"
				+ "unp, okpo, bname,bcity,bline,bindex,boffice,bbuilding,account,"
				+ "bunp, email, bemail,codecountry,bcodecountry) "
				+ "values (id_client_seq.nextval, "
				+ ":name,:city,:line,:cindex,:office,:building,:work_phone,:cell_phone,"
				+ ":unp, :okpo, :bname,:bcity,:bline,:bindex,:boffice,:bbuilding,:account,"
				+ ":bunp,:email,:bemail,:codecountry,:bcodecountry) ";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(client);

		try {
			template.update(sql, parameters);
			
		} catch (Exception ex) {
			System.out.println("Error - client save: " + ex.toString());
		}
	}

	// ---------------------------------------------------------------
	// Update Client  
	// ---------------------------------------------------------------
	public void updateClient(Client client) {
		String sql = "update cci_client set "
				+ "name = :name, city =:city, line =:line, cindex=:cindex, office=:office,"
				+ "building=:building, work_phone=:work_phone, cell_phone=:cell_phone,"
				+ "unp=:unp, okpo=:okpo, bname=:bname, bcity=:bcity, bline=:bline,"
				+ "bindex=:bindex, boffice=:boffice, bbuilding=:bbuilding, account=:account,"
				+ "bunp=:bunp, email=:email, bemail=:bemail, codecountry=:codecountry,"
				+ "bcodecountry=:bcodecountry  WHERE id = :id";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(client);

		try {
			template.update(sql, parameters);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// Get filtered Clients list  
	// ---------------------------------------------------------------
	public List<ViewClient> getClients(String orderby, String order,
			SQLBuilder builder) {
		String sql = " SELECT * FROM CLIENT_VIEW " 
				+ builder.getWhereClause() + " ORDER BY " +  orderby + " " + order;

		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<ViewClient>(ViewClient.class));
	}
}
