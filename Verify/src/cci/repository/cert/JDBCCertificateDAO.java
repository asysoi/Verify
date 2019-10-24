package cci.repository.cert;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cci.model.cert.Certificate;
import cci.model.cert.Product;
import cci.web.controller.cert.exception.CertificateGetException;
import cci.web.controller.cert.exception.NotFoundCertificateException;

//@Repository
public class JDBCCertificateDAO implements CertificateDAO {
	
	private static final Logger LOG = Logger.getLogger(JDBCCertificateDAO.class);
	
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
	
	// ---------------------------------------------------------------
	// поиск сертификата 
	// ---------------------------------------------------------------
	public Certificate checkCT(Certificate cert) {
		Certificate rcert = null;
		long start = System.currentTimeMillis();
		
		try {
			String sql = "select * from CERT_VIEW WHERE NOMERCERT = ? AND NBLANKA like ? AND (DATACERT=? OR ISSUEDATE=TO_DATE(?,'DD.MM.YY'))";
			rcert = template.getJdbcOperations().queryForObject(
					sql,
					new Object[] { cert.getNomercert(), cert.getNblanka()+"%",
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
	
	// --------------------------------------------------------------------
	// Get Certificate by certificate number / FOR REST SERVICE
	// --------------------------------------------------------------------
	public Certificate getCertificate(String number, String blanknumber, String date) {

		Certificate rcert = null;

		try {
			String sql = "select * from CERT_VIEW WHERE NOMERCERT = ? AND NBLANKA like ? AND "
					+ " (DATACERT=? OR ISSUEDATE=TO_DATE(?,'DD.MM.YY'))";
			rcert = template.getJdbcOperations().queryForObject(sql, new Object[] { number, blanknumber+"%", date, date },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			if (rcert != null) {
				sql = "select * from C_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				rcert.setProducts(template.getJdbcOperations().query(sql, new Object[] { rcert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (EmptyResultDataAccessException ex) {
			LOG.info("Certificate find error: " + ex.getMessage());
			throw (new NotFoundCertificateException(
					"Cертификат с номером " + number + " на бланке с номером  " + blanknumber + " не найден в базе."));
		} catch (Exception ex) {
			LOG.info("Certificate loading error: " + ex.getMessage());
			throw (new CertificateGetException(ex.getMessage()));
		}

		LOG.info("Certificate loaded");
		return rcert;
	}
	
}
