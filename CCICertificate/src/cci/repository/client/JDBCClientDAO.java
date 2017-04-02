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
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cci.model.Client;
import cci.model.ClientLocale;
import cci.model.cert.Country;
import cci.model.fscert.FSProduct;
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
			
			if  (item != null) {
				sql = "select * from CCI_CLIENT_LOCALE WHERE IDCLIENT = ? ORDER BY LOCALE";
				List<ClientLocale> locales = template.getJdbcOperations().query(sql, new Object[] { item.getId() },
						new BeanPropertyRowMapper<ClientLocale>(ClientLocale.class));
				if (locales != null && locales.size() > 0) {
				   int lid = 0;	
				   for (ClientLocale locale : locales) {
					   locale.setId(lid++);
				   }
				   item.setLocales(locales);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	// ---------------------------------------------------------------
	// Save/Add Client
	// ---------------------------------------------------------------
	public void saveClient(Client client) {
		long id = 0;
		
		String sql = "insert into cci_client "
				+ "(id, "
				+ "name, city, street, cindex, office, building, phone, cell, fax, "
				+ "unp, okpo, bname, bcity, bstreet, bindex, boffice, bbuilding, account,"
				+ "bunp, email, bemail,codecountry,bcodecountry, version) "
				+ "values (id_client_seq.nextval, "
				+ ":name,:city,:street,:cindex,:office,:building,:phone,:cell, :fax,"
				+ ":unp, :okpo, :bname,:bcity,:bstreet,:bindex,:boffice,:bbuilding,:account,"
				+ ":bunp,:email,:bemail,:codecountry,:bcodecountry,:version) ";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(client);

		try {

			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			int row = template.update(sql, parameters, keyHolder,
					new String[] { "id" });
			id = keyHolder.getKey().intValue();

			if (row > 0) {
				sql = "insert into CCI_CLIENT_LOCALE(idclient, locale, name, street, city) values ("
						+ id + ",:locale, :name, :street, :city)";
				
				if (client.getLocales() != null && client.getLocales().size() > 0) {
					SqlParameterSource[] batch = SqlParameterSourceUtils
							.createBatch(client.getLocales().toArray());
					int[] updateCounts = template.batchUpdate(sql, batch);
				}
				
				client.setId(id);
			}
			client.setVersion(client.getVersion() + 1);
			
		} catch (Exception ex) {
			LOG.info("Error - employee save: " + ex.toString());
		}
	}

	// ---------------------------------------------------------------
	// Update Client  
	// ---------------------------------------------------------------
	public void updateClient(Client client) {
		String sql = "update cci_client set "
				+ "name = :name, city =:city, street =:street, cindex=:cindex, office=:office,"
				+ "building=:building, phone=:phone, cell=:cell,"
				+ "unp=:unp, okpo=:okpo, bname=:bname, bcity=:bcity, bstreet=:bstreet,"
				+ "bindex=:bindex, boffice=:boffice, bbuilding=:bbuilding, account=:account,"
				+ "bunp=:bunp, email=:email, bemail=:bemail, codecountry=:codecountry,"
				+ "bcodecountry=:bcodecountry, version = :version + 1 "
				+ "WHERE id = :id and version=:version";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(client);
		

		try {
			int row = template.update(sql, parameters);
			
			if (row > 0) {
				
				template.getJdbcOperations().update(
						"delete from CCI_CLIENT_LOCALE where idclient = ?",
						client.getId());
				
				sql = "insert into CCI_CLIENT_LOCALE(idclient, locale, name, street, city) values ("
						+ client.getId() + ", :locale, :name, :street, :city)";
				
				LOG.info("Client locales: " + client.getLocales());
				
				if (client.getLocales() != null && client.getLocales().size() > 0) {
					SqlParameterSource[] batch = SqlParameterSourceUtils
							.createBatch(client.getLocales().toArray());
					int[] updateCounts = template.batchUpdate(sql, batch);
				}
				
				client.setVersion(client.getVersion() + 1);
				
			} else {
				throw new RuntimeException("Не удалось изменить контрагента " + client.getName() + " по неизвестной причине.");
			}
			
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
