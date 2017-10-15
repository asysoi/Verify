package cci.repository.cert;

import java.util.List;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import cci.model.cert.Certificate;
import cci.model.cert.Product;
import cci.web.controller.cert.exception.CertificateGetException;
import cci.web.controller.cert.exception.NotFoundCertificateException;

@Repository
public class JDBCCertificateDAO implements CertificateDAO {
	
	private static final Logger LOG = Logger.getLogger(JDBCCertificateDAO.class);
	
	private NamedParameterJdbcTemplate template;
	private DataSource ds; 

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
		ds = dataSource;
	}
	
	// ---------------------------------------------------------------
	// поиск сертификата по number
	// ---------------------------------------------------------------
	public Certificate check(Certificate cert) {
		Certificate rcert = null;
		long start = System.currentTimeMillis();
		
		try {
			String sql = "select * from CERT_VIEW WHERE NOMERCERT = ? AND NBLANKA = ? AND (DATACERT=? OR ISSUEDATE=TO_DATE(?,'DD.MM.YY'))";
			rcert = template.getJdbcOperations().queryForObject(
					sql,
					new Object[] { cert.getNomercert(), cert.getNblanka(),
							cert.getDatacert(), cert.getDatacert() },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));
			
			LOG.info("Certificate check: " + (System.currentTimeMillis() - start));
			if (rcert != null) {
				sql = "select * from C_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				rcert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { rcert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (Exception ex) {
			LOG.info("Certificate isn't found: " + ex.getMessage());
		}
		
		LOG.info("Certificate check load: " + (System.currentTimeMillis() - start));
		return rcert;
	}

	// ---------------------------------------------------------------
	// поиск сертификата по номеру бланка
	// возможно несколько сертификатов -> PS
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
	// поиск сертификата по номеру сертификата -> PS
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
	// find certificate by query template -> PS
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

		
	
	// -----------------------------------------------------------------------------------------------------------------------------
	//                     Certificate REST FULL Methods for certificate of origin
	// -----------------------------------------------------------------------------------------------------------------------------
	
	
	//--------------------------------------------------------------------
	// Get Certificate by certificate number / FOR REST SERVICE
	//--------------------------------------------------------------------
	public Certificate getCertificateByNumber(String number, String blanknumber) throws Exception {
		
		Certificate rcert = null;
		long start = System.currentTimeMillis();
		
		try {
			String sql = "select * from CERT_VIEW WHERE NOMERCERT = ? AND NBLANKA = ?";
			rcert = template.getJdbcOperations().queryForObject(
					sql,
					new Object[] { number, blanknumber},
					new BeanPropertyRowMapper<Certificate>(Certificate.class));
		
			if (rcert != null) {
				sql = "select * from C_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				rcert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { rcert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch(EmptyResultDataAccessException ex) {
			LOG.info("Certificate find error: " + ex.getMessage());
			throw (new NotFoundCertificateException("Cертификат с номером " + number + " на бланке с номером  " + blanknumber  
 					                                + " не найден в базе."));
		} catch (Exception ex) {
			LOG.info("Certificate loading error: " + ex.getMessage());
			throw (new CertificateGetException(ex.getMessage()));
		}
		
		LOG.info("Certificate loading: " + (System.currentTimeMillis() - start));
		return rcert;
	}

	
}
