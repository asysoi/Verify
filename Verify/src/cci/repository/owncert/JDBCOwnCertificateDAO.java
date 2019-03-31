package cci.repository.owncert;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cci.model.cert.Certificate;
import cci.model.owncert.Branch;
import cci.model.owncert.Factory;
import cci.model.owncert.OwnCertificate;
import cci.model.owncert.Product;

//@Repository
public class JDBCOwnCertificateDAO implements OwnCertificateDAO {

	private static final Logger LOG = Logger
			.getLogger(JDBCOwnCertificateDAO.class);
	
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	/* ---------------------------------------------------------------
	 * Find certificate by unique identification. Updated 07.02.2019
	 *  --------------------------------------------------------------- */
	public OwnCertificate checkOwnExist(Certificate cert) {

		String sql = "select * from owncertificate WHERE number = ? and blanknumber = ? and datecert =  STR_TO_DATE(?,'%d.%m.%Y')";
		LOG.info("checkOwnExist");
		OwnCertificate rcert = template.getJdbcOperations()
				.queryForObject(
						sql,
						new Object[] { cert.getNomercert().trim(), cert.getNblanka().trim(), cert.getDatacert().trim()},
						new OwnCertificateMapper<OwnCertificate>());
	
		return rcert;	
	}
	

	/* ---------------------------------------------------------------
	 * Get full filled certificate by unique identification. Updated 07.02.2019
	 *  --------------------------------------------------------------- */
	public OwnCertificate getOwnCertificate(Certificate cert) {

		String sql = "select * from owncertificate WHERE number = ? and blanknumber = ?"
					 + " and datecert =  STR_TO_DATE(?,'%d.%m.%Y')";
		LOG.info("getOwnCertificate " + cert.getNomercert() + "|" + cert.getNblanka().trim() + "|" + cert.getDatacert().trim());
		
		OwnCertificate rcert = null;
		try {
		   rcert = template.getJdbcOperations()
				.queryForObject(
						sql,
						new Object[] { cert.getNomercert().trim(), cert.getNblanka().trim(), cert.getDatacert().trim()},
						new OwnCertificateMapper<OwnCertificate>());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		loadReletedCertificateEntities(rcert);
		return rcert;	
	}
	
	/* ---------------------------------------------------------------
	 * 
	 * Load all certificate's attributes 
	 * 
	 *  --------------------------------------------------------------- */
	private void loadReletedCertificateEntities(OwnCertificate cert) {
		String sql = "select * from ownproduct WHERE id_certificate = ? ORDER BY id";

		cert.setProducts(template.getJdbcOperations().query(sql, new Object[] { cert.getId() },
				new BeanPropertyRowMapper<Product>(Product.class)));

		sql = "select * from ownbranch WHERE id_certificate = ? ORDER BY id";

		cert.setBranches(template.getJdbcOperations().query(sql, new Object[] { cert.getId() },
				new BeanPropertyRowMapper<Branch>(Branch.class)));

		sql = "select * from ownfactory WHERE id_certificate = ? ORDER BY id";

		cert.setFactories(template.getJdbcOperations().query(sql, new Object[] { cert.getId() },
				new BeanPropertyRowMapper<Factory>(Factory.class)));
	}
}
