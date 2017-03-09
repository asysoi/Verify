package cci.repository.client;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import cci.model.Client;
import cci.model.cert.Country;
import cci.repository.SQLBuilder;
import cci.service.SQLQueryUnit;
import cci.web.controller.client.ViewClient;
import cci.web.controller.fscert.ViewFSCertificate;

@Repository
public class JDBCClientDAO implements ClientDAO {

	public static Logger LOG=Logger.getLogger(JDBCClientDAO.class);
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

		SQLQueryUnit filter = builder.getSQLUnitWhereClause();
	    Map<String, Object> params = filter.getParams();
	    LOG.info("Client SQLQueryUnit : " + filter);
	    
		String sql = " SELECT client.* "
				+ " FROM (SELECT t.*, ROW_NUMBER() OVER " + " (ORDER BY t."
				+ orderby + " " + order + ", t.ID " + order + ") rw "
				+ " FROM CLIENT_VIEW t " + filter.getClause() + " )"
				+ " client " + " WHERE client.rw > " + ((page - 1) * pagesize)
				+ " AND client.rw <= " + (page * pagesize);

		LOG.info("Next clients page : " + sql);

	    return this.template.query(sql,	params, 
	    		new BeanPropertyRowMapper<ViewClient>(ViewClient.class));
		
	}

	// ---------------------------------------------------------------
	// Get count clients in the list 
	// ---------------------------------------------------------------
	public int getViewPageCount(SQLBuilder builder) {
		long start = System.currentTimeMillis();
		
        SQLQueryUnit qunit = builder.getSQLUnitWhereClause();
		String sql = "SELECT count(*) FROM CLIENT_VIEW "
				+ qunit.getClause();

		Integer count = this.template.queryForObject(sql, qunit.getParams(), Integer.class);
		
		LOG.info(sql);
		return count.intValue();
	}

	// ---------------------------------------------------------------
	// Get list of countries
	// ---------------------------------------------------------------
	public Map<String, Map<String,String>> getCountriesList() {
		String sql = "SELECT * from C_COUNTRY ORDER BY NAME";

		Map<String, Map<String,String>> countries = new HashMap<String, Map<String,String>>();
		Map<String, String> countriesru = new LinkedHashMap<String, String>();
		Map<String, String> countriesen = new LinkedHashMap<String, String>();
		
		List<Country> list = template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Country>(Country.class));
		
		for (Country cntry:list) {
			countriesru.put(cntry.getCode(), cntry.getName());
			countriesen.put(cntry.getCode(), cntry.getEnname());
		}
		countries.put("RU",countriesru);
		countries.put("EN",countriesen);
		
		LOG.debug("Got country list");		
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
				+ "bunp, email, bemail,codecountry,bcodecountry, enname, encity, enline, version) "
				+ "values (id_client_seq.nextval, "
				+ ":name,:city,:line,:cindex,:office,:building,:work_phone,:cell_phone,"
				+ ":unp, :okpo, :bname,:bcity,:bline,:bindex,:boffice,:bbuilding,:account,"
				+ ":bunp,:email,:bemail,:codecountry,:bcodecountry, :enname, :encity, :enline, :version) ";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(client);

		try {
			template.update(sql, parameters);
			client.setVersion(client.getVersion() + 1);
		} catch (Exception ex) {
			LOG.info("Error - client save: " + ex.toString());
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
				+ "bcodecountry=:bcodecountry, enname=:enname, encity=:encity, enline=:enline, version = :version + 1 "
				+ "WHERE id = :id and version=:version";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(client);

		try {
			template.update(sql, parameters);
			client.setVersion(client.getVersion() + 1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// Get filtered Clients list  
	// ---------------------------------------------------------------
	public List<ViewClient> getClients(String orderby, String order,
			SQLBuilder builder) {
		
		SQLQueryUnit filter = builder.getSQLUnitWhereClause();
	    Map<String, Object> params = filter.getParams();

		String sql = " SELECT * FROM CLIENT_VIEW " 
				+ filter.getClause() + " ORDER BY " +  orderby + " " + order;

	    return this.template.query(sql,	params, 
	    		new BeanPropertyRowMapper<ViewClient>(ViewClient.class));
	}
}
