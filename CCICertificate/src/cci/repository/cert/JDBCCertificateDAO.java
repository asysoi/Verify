package cci.repository.cert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cci.model.cert.Certificate;
import cci.model.cert.Country;
import cci.model.cert.Product;
import cci.model.cert.Report;
import cci.repository.SQLBuilder;
import cci.service.FilterCondition;
import cci.web.controller.client.ClientController;
import cci.web.controller.purchase.ViewPurchase;

@Repository
public class JDBCCertificateDAO implements CertificateDAO {
	
	private static final Logger LOG = Logger.getLogger(JDBCCertificateDAO.class);
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	// ---------------------------------------------------------------
	// количкество сертификатов в списке
	// ---------------------------------------------------------------
	public int getViewPageCount(SQLBuilder builder) {
		long start = System.currentTimeMillis();

		String sql = "SELECT count(*) FROM CERT_VIEW "
				+ builder.getWhereClause();

		int count = this.template.getJdbcOperations().queryForInt(sql);

		LOG.info(sql);
		LOG.info("Query time: "
				+ (System.currentTimeMillis() - start));

		return count;
	}

	// ---------------------------------------------------------------
	// поиск сертификата по id
	// ---------------------------------------------------------------
	public Certificate findByID(Long id) {
		Certificate cert = null;

		try {
			String sql = "select * from CERT_VIEW WHERE cert_id = ?";
			cert = template.getJdbcOperations().queryForObject(sql,
					new Object[] { id },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			sql = "select * from C_PRODUCT WHERE cert_id = ? ORDER BY product_id";
			cert.setProducts(template.getJdbcOperations().query(sql,
					new Object[] { cert.getCert_id() },
					new BeanPropertyRowMapper<Product>(Product.class)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return cert;
	}

	// ---------------------------------------------------------------
	// поиск сертификата по id
	// ---------------------------------------------------------------
	public Certificate check(Certificate cert) {
		Certificate rcert = null;

		try {
			String sql = "select * from CERT_VIEW WHERE NOMERCERT = ? AND NBLANKA = ? AND (DATACERT=? OR ISSUEDATE=TO_DATE(?,'DD.MM.YY'))";
			rcert = template.getJdbcOperations().queryForObject(
					sql,
					new Object[] { cert.getNomercert(), cert.getNblanka(),
							cert.getDatacert(), cert.getDatacert() },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));
			if (rcert != null) {
				sql = "select * from C_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				rcert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { rcert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rcert;
	}

	// ---------------------------------------------------------------
	// поиск скртификата по номеру бланка
	// возможно несколько сертификатов
	// ---------------------------------------------------------------
	public List<Certificate> findByNBlanka(String number) {
		List<Certificate> certs = null;

		try {
			String sql = "select * from CERT_VIEW WHERE nblanka = ?";
			certs = template.getJdbcOperations().query(sql,
					new Object[] { number },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			for (Certificate cert : certs) {
				sql = "select * from C_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return certs;
	}

	// ---------------------------------------------------------------
	// поиск сертификата по номеру сертификата
	// ---------------------------------------------------------------
	public List<Certificate> findByNumberCert(String number) {
		List<Certificate> certs = null;

		try {
			String sql = "select * from CERT_VIEW WHERE nomercert = ?";
			certs = template.getJdbcOperations().query(sql,
					new Object[] { number },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			for (Certificate cert : certs) {
				sql = "select * from C_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return certs;
	}

	// ---------------------------------------------------------------
	// вернуть очередную страницу списка сертификатов
	// ---------------------------------------------------------------
	public List<Certificate> findViewNextPage(String[] dbfields, int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {
		long start = System.currentTimeMillis();

		/* 
		String flist = "cert.cert_id, cert.datacert";
		
		for (String field : dbfields) {
		    flist += ", cert." + field;  	
		}
		
		String sql = " SELECT " + flist
				+ " FROM (SELECT t.*, ROW_NUMBER() OVER " + " (ORDER BY t."
				+ orderby + " " + order + ", t.CERT_ID " + order + ") rw "
				+ " FROM CERT_VIEW t " + builder.getWhereClause() + " )"
				+ " cert " + " WHERE cert.rw > " + ((page - 1) * pagesize)
				+ " AND cert.rw <= " + (page * pagesize);
		*/

		String flist = "cert_id, datacert, otd_name";
		
		for (String field : dbfields) {
		    flist += ", " + field;  	
		}

		String sql = "select " + flist 
				+ " from cert_view where cert_id in "
				+ " (select  a.cert_id "
				+ " from (SELECT cert_id FROM (select cert_id from cert_view "
				+  builder.getWhereClause()
				+ " ORDER by " +  orderby + " " + order + ", cert_id " + order  
				+ ") where rownum <= ? "    
				+ ") a left join (SELECT cert_id FROM (select cert_id from cert_view "
				+  builder.getWhereClause()
				+ " ORDER by " +  orderby + " " + order + ", cert_id " + order
				+ ") where rownum <= ? "   
				+ ") b on a.cert_id = b.cert_id where b.cert_id is null)" 
				+ " ORDER by " +  orderby + " " + order + ", cert_id " + order;
		
		
		LOG.info("Next page : " + sql);
		return this.template.getJdbcOperations().query(sql,
				new Object[] {page * pagesize, (page - 1) * pagesize}, 
				new BeanPropertyRowMapper<Certificate>(Certificate.class));
	}

	// ---------------------------------------------------------------
	// вернуть все сертификаты
	// ---------------------------------------------------------------
	public List<Certificate> findAll() {
		String sql = "select * from CERT_VIEW ORDER BY cert_id";

		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Certificate>(Certificate.class));
	}

	// ---------------------------------------------------------------
	// save certificate
	// ---------------------------------------------------------------
	public int save(Certificate cert) {
		String sql_cert = "insert into c_cert values "
				+ "(beltpp.cert_id_seq.nextval, "
				+ ":forms, :unn, :kontrp, :kontrs, :adress, :poluchat, :adresspol, :datacert,"
				+ ":nomercert, :expert, :nblanka, :rukovod, :transport, :marshrut, :otmetka,"
				+ ":stranav, :stranapr, :status, :koldoplist, :flexp, :unnexp, :expp, "
				+ ":exps, :expadress, :flimp, :importer, :adressimp, :flsez, :sez,"
				+ ":flsezrez, :stranap, :otd_id, :parentnumber, :parentstatus, TO_DATE(:datacert,'DD.MM.YY'))";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		int cert_id = 0;

		try {
			int row = template.update(sql_cert, parameters, keyHolder,
					new String[] { "CERT_ID" });
			cert_id = keyHolder.getKey().intValue();

			String sql_product = "insert into C_PRODUCT values ("
					+ " beltpp.product_id_seq.nextval, " + cert_id + ", "
					+ " :numerator, :tovar, :vidup, :kriter, :ves, :schet)";

			if (cert.getProducts() != null && cert.getProducts().size() > 0) {
				SqlParameterSource[] batch = SqlParameterSourceUtils
						.createBatch(cert.getProducts().toArray());
				int[] updateCounts = template.batchUpdate(sql_product, batch);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return cert_id;
	}

	// ---------------------------------------------------------------
	// update certificate
	// ---------------------------------------------------------------
	public void update(Certificate cert) {

		String sql_cert = "update c_cert SET "
				+ "forms = :forms, unn = :unn, kontrp = :kontrp, kontrs = :kontrs, adress = :adress, poluchat = :poluchat, adresspol = :adresspol, datacert = :datacert,"
				+ "nomercert = :nomercert, expert = :expert, nblanka = :nblanka, rukovod = :rukovod, transport = :transport, marshrut = :marshrut, otmetka = :otmetka,"
				+ "stranav = :stranav, stranapr = :stranapr, status = :status, koldoplist = :koldoplist, flexp = :flexp, unnexp = :unnexp, expp = :expp, "
				+ "exps = :exps, expadress = :expadress, flimp = :flimp, importer = :importer, adressimp = :adressimp, flsez = :flsez, sez = :sez,"
				+ "flsezrez = :flsezrez, stranap = :stranap, parentnumber = :parentnumber, parentstatus = : parentstatus, issuedate=TO_DATE(:datacert,'DD.MM.YY')"
				+ "WHERE cert_id = :cert_id";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);

		try {

			int row = template.update(sql_cert, parameters);

			template.getJdbcOperations().update(
					"delete from C_PRODUCT where cert_id = ?",
					Long.valueOf(cert.getCert_id()));

			String sql_product = "insert into C_PRODUCT values ("
					+ " beltpp.product_id_seq.nextval, " + cert.getCert_id()
					+ ", "
					+ " :numerator, :tovar, :vidup, :kriter, :ves, :schet)";

			if (cert.getProducts() != null && cert.getProducts().size() > 0) {
				SqlParameterSource[] batch = SqlParameterSourceUtils
						.createBatch(cert.getProducts().toArray());
				int[] updateCounts = template.batchUpdate(sql_product, batch);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// ---------------------------------------------------------------
	// find certificate by query template
	// ---------------------------------------------------------------
	public List<Certificate> findByCertificate(Certificate qcert) {
		List<Certificate> certs = null;

		String sql_cert = "SELECT * from cert_view WHERE "
				+ "datacert = :datacert AND nomercert = :nomercert AND nblanka = :nblanka";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				qcert);

		try {

			certs = template.query(sql_cert, parameters,
					new BeanPropertyRowMapper<Certificate>());

			for (Certificate cert : certs) {
				String sql = "select * from C_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>()));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return certs;
	}

	// ---------------------------------------------------------------
	// Get list of departments
	// ---------------------------------------------------------------
	public List<String> getDepartmentsList() {
		String sql = "SELECT otd_name from C_OTD";

		LOG.debug("Got department list");
		return (List<String>) template.getJdbcOperations().queryForList(sql,
				String.class);
	}

	// ---------------------------------------------------------------
	// Get MAP of branch's by roles
	// ---------------------------------------------------------------
	public Map<String,String> getACL() {
		String sql = "SELECT acl_role, id from C_OTD";

		LOG.debug("Got ACL map");
		
		return template.query(sql, new ResultSetExtractor<Map<String, String>>(){
			
		    public Map<String, String> extractData(ResultSet rs) throws SQLException,DataAccessException {
		        HashMap<String,String> mapRet= new HashMap<String,String>();
		        while(rs.next()){
		            mapRet.put(rs.getString("acl_role"), Integer.toString(rs.getInt("id")));
		        }
		        return mapRet;
		    }
		});
	}
	
	// ---------------------------------------------------------------
	// Get list of countries
	// ---------------------------------------------------------------
	public Map<String, String> getCountriesList() {
		String sql = "SELECT * from C_COUNTRY ORDER BY NAME";

		Map<String, String> countries = new LinkedHashMap<String, String>();
		
		List<Country> list = template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Country>(Country.class));
		
		for (Country cntry:list) {
			countries.put(cntry.getCode(), cntry.getName());
		}
		LOG.debug("Got country list");		
		return countries;
	}

	// ---------------------------------------------------------------
	// Get list of forms
	// ---------------------------------------------------------------
	public List<String> getFormsList() {
		String sql = "SELECT forms from c_cert group by forms having forms is not null ORDER BY forms ";

		LOG.debug("Got forms list");
		return (List<String>) template.getJdbcOperations().queryForList(sql,
				String.class);
	}

	
	// ---------------------------------------------------------------
	// Get full list of certificates by filter with tovar 
	// ---------------------------------------------------------------
	public List<Certificate> getCertificates(String[] dbfields, String orderby, String order,
			SQLBuilder builder) {
		
		String flist = "cert_id, otd_name";
		
		for (String field : dbfields) {
		    flist += ", " + field;  	
		}
		
		String sql = " SELECT " + flist + " FROM CERT_VIEW_TOFILE " 
				+ builder.getWhereClause() + " ORDER BY " +  orderby + " " + order;

		LOG.info("Get certificates: " + sql);
		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Certificate>(Certificate.class));
	}

	// ---------------------------------------------------------------
	// Get Analitic Report grouped by Fields 
	// ---------------------------------------------------------------
	public List<Report> getReport(String[] fields, SQLBuilder builder, Boolean onfilter) {
		String field = fields[0];   // берем только одно поле для группировки
		
		String sql = "SELECT " + field + " as field, COUNT(*) as value FROM (SELECT * FROM CERT_VIEW " +  
					 (onfilter ? builder.getWhereClause() : "") + ") group by " + field + " ORDER BY value DESC" ;		

		LOG.debug("Make report: " + sql);
		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Report>(Report.class));
	}

	// ---------------------------------------------------------------
    // Get Pagination of Certificates rEPORT
	// ---------------------------------------------------------------
	public List<Certificate> findViewNextReportPage(String[] dbfields, int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {
		
		/*
		String flist = "cert.cert_id, cert.datacert";

		for (String field : dbfields) {
		    flist += ", cert." + field;  	
		}
		
		String sql = " SELECT " + flist
				+ " FROM (SELECT t.*, ROW_NUMBER() OVER " + " (ORDER BY t."
				+ orderby + " " + order + ", t.FILE_IN_ID " + order + ") rw "
				+ " FROM CERT_REPORT t " +   builder.getWhereClause() + " )"
				+ " cert " + " WHERE cert.rw > " + ((page - 1) * pagesize)
				+ " AND cert.rw <= " + (page * pagesize);
		*/		

		String flist = "file_in_id, cert_id, datacert";
		
		for (String field : dbfields) {
		    flist += ", " + field;  	
		}

		String sql = "select " + flist 
				+ " from cert_report where file_in_id in "
				+ " (select  /*+ materialize  no_merge */ a.file_in_id "
				+ " from (SELECT file_in_id FROM (select file_in_id from cert_report "
				+  builder.getWhereClause()
				+ " ORDER by " +  orderby + " " + order + ", file_in_id " + order  
				+ ") where rownum <= ? "  
				+ ") a left join (SELECT file_in_id FROM (select file_in_id from cert_report "
				+  builder.getWhereClause()
				+ " ORDER by " +  orderby + " " + order + ", file_in_id " + order
				+ ") where rownum <= ? "  
				+ ") b on a.file_in_id = b.file_in_id where b.file_in_id is null)" 
				+ " ORDER by " +  orderby + " " + order + ", file_in_id " + order;

		LOG.info("Next page : " + sql);
		return this.template.getJdbcOperations().query(sql,
				new Object[] {page * pagesize, (page - 1) * pagesize},
				new BeanPropertyRowMapper<Certificate>(Certificate.class));

	}

	// ---------------------------------------------------------------
    // Get Count Certificates  
	// ---------------------------------------------------------------
	public int getViewPageReportCount(SQLBuilder builder) {
		long start = System.currentTimeMillis();

		String sql = "select count(*) from CERT_REPORT " + builder.getWhereClause();
		
		LOG.info(sql);
		int count = this.template.getJdbcOperations().queryForInt(sql);

		return count;
	}

	// ---------------------------------------------------------------
	// Get full list of certificates by filter with tovar for Load Report 
	// ---------------------------------------------------------------
	public List<Certificate> getReportCertificates(String[] dbfields, String orderby,
			String order, SQLBuilder builder) {
		String flist = "cert_id";
		
		for (String field : dbfields) {
		    flist += ", " + field;  	
		}
		
		String sql = " SELECT " + flist + " FROM CERT_VIEW_LOAD_REPORT " 
				+ builder.getWhereClause() + " ORDER BY " +  orderby + " " + order;

		LOG.info("Get certificates for Report: " + sql);
		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Certificate>(Certificate.class));
	}

}
