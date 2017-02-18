package cci.repository.owncert;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cci.model.owncert.OwnCertificate;
import cci.model.owncert.OwnCertificateHeader;
import cci.model.owncert.OwnCertificateHeaders;
import cci.model.owncert.OwnCertificates;
import cci.model.owncert.Product;
import cci.model.owncert.Company;
import cci.model.owncert.Products;
import cci.web.controller.owncert.OwnFilter;
import cci.web.controller.cert.exception.NotFoundCertificateException;
import cci.web.controller.cert.exception.CertificateDeleteException;
import cci.web.controller.cert.exception.CertificateUpdateException;

@Repository
public class JDBCOwnCertificateDAO implements OwnCertificateDAO {

	private static final Logger LOG = Logger
			.getLogger(JDBCOwnCertificateDAO.class);
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	// ---------------------------------------------------------------
	// Получить список сертификатов
	// ---------------------------------------------------------------
	public OwnCertificates getOwnCertificates(OwnFilter filter, boolean isLike) {

		String sql = "select * from certview "
				+ (isLike ? filter.getWhereLikeClause() : filter
						.getWhereEqualClause()) + " ORDER BY id";
         OwnCertificates certs = new  OwnCertificates();
		        
		 certs.setOwncertificates(this.template.getJdbcOperations()
				.query(sql, new OwnCertificateMapper()));
						
//						new BeanPropertyRowMapper<OwnCertificate>(
								//OwnCertificate.class)));
		 
		 return certs; 
	}

	// ---------------------------------------------------------------
	// Получить список заголовков сертификатов
	// ---------------------------------------------------------------
	public OwnCertificateHeaders getOwnCertificateHeaders(OwnFilter filter, boolean isLike) {

		String sql = "select number, blanknumber from certview "
				+ (isLike ? filter.getWhereLikeClause() : filter
						.getWhereEqualClause()) + " ORDER BY id";
         OwnCertificateHeaders certs = new  OwnCertificateHeaders();
		        
		 certs.setOwncertificateheaders(this.template.getJdbcOperations()
				.query(sql,	new BeanPropertyRowMapper<OwnCertificateHeader>(OwnCertificateHeader.class)));
		 
		 return certs; 
	}
	
	
	
	// ---------------------------------------------------------------
	// поиск единственного сертификата по id -> PS
	// ---------------------------------------------------------------
	public OwnCertificate findOwnCertificateByID(int id) throws Exception {
		OwnCertificate cert = null;

		String sql = "select * from certview WHERE id = ?";
		cert = template.getJdbcOperations()
				.queryForObject(
						sql,
						new Object[] { id },
						new OwnCertificateMapper());
		
//						new BeanPropertyRowMapper<OwnCertificate>(
//								OwnCertificate.class));

//		sql = "select * from beltpp WHERE id = ? ";
//		cert.setBeltpp(template.getJdbcOperations().queryForObject(sql,
//				new Object[] { cert.getId_beltpp() },
//				new BeanPropertyRowMapper<Company>(Company.class)));

		sql = "select * from ownproduct WHERE id_certificate = ? ORDER BY id";
		
		cert.setProducts(template.getJdbcOperations().query(sql,
				new Object[] { cert.getId() },
				new BeanPropertyRowMapper<Product>(Product.class)));
		return cert;
	}

	// ---------------------------------------------------------------
	// Сохранение сертификата в базе дданных
	// ---------------------------------------------------------------
	public  OwnCertificate saveOwnCertificate(OwnCertificate cert)
			throws Exception {

		cert.setId_beltpp(getBeltppID(cert));

		String sql_cert = "insert into owncertificate(id_beltpp, number, blanknumber, type, customername, customeraddress, "
				+ " customerunp, factoryaddress, branches, datecert, dateexpire, expert, signer, signerjob, datestart, additionallists) "
				+ " values ("
				+ " :id_beltpp, :number, :blanknumber, :type, :customername, :customeraddress, :customerunp, :factoryaddress, :branches,"
				+ " STR_TO_DATE(:datecert,'%d.%m.%Y'), "
				+ " STR_TO_DATE(:dateexpire,'%d.%m.%Y'), "
				+ " :expert, :signer, :signerjob, "
				+ " STR_TO_DATE(:datestart,'%d.%m.%Y')" + ", :additionallists)";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		int id = 0;

		int row = template.update(sql_cert, parameters, keyHolder,
				new String[] { "id" });
		id = keyHolder.getKey().intValue();

		if (row > 0) {
			String sql_product = "insert into ownproduct(id_certificate, number, name, code) values ("
					+ id + ", :number, :name, :code)";

			if (cert.getProducts() != null && cert.getProducts().size() > 0) {
				SqlParameterSource[] batch = SqlParameterSourceUtils
						.createBatch(cert.getProducts().toArray());
				int[] updateCounts = template.batchUpdate(sql_product, batch);
			}

			cert.setId(id);
		}

		return cert;
	}

	// -------------------------------------------
	// Get id of beltpp branch
	// -------------------------------------------
	private int getBeltppID(OwnCertificate cert) {
		String sql = "SELECT id FROM beltpp WHERE name = '"
				+ cert.getBeltpp().getName() + "' Limit 1";
		int id = 0;
		try {
			id = this.template.getJdbcOperations().queryForObject(sql,
					Integer.class);

		} catch (Exception ex) {
			System.out
					.println(ex.getClass().getName() + ": " + ex.getMessage());
		}
		
		if (id == 0) {
			sql = "insert into beltpp(name, address, unp) values(:name, :address, :unp)";
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(
					cert.getBeltpp());
			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			id = 0;

			int row = template.update(sql, parameters, keyHolder,
					new String[] { "id" });
			id = keyHolder.getKey().intValue();
		}

		return id;
	}

	// ---------------------------------------------------------------
	// update certificate
	// ---------------------------------------------------------------
	public OwnCertificate updateOwnCertificate(OwnCertificate cert) {

		OwnFilter filter = new OwnFilter(cert.getNumber(), cert.getBlanknumber(),
				null, null);
		List<OwnCertificate> bufcerts = null;

		try {
			bufcerts = getOwnCertificates(filter, false).getOwncertificates();
		} catch (Exception ex) {
			LOG.info("Ошибка поиска обновляемого сертификата: "
					+ ex.getMessage());
			throw (new NotFoundCertificateException(
					"Ошибка процедуры поиска сертификата в базе данных: "
							+ ex.getMessage()));

		}

		if (bufcerts != null && bufcerts.size() == 1) {

			if (cert.equals(bufcerts.get(0))) {
				throw new CertificateDeleteException("Обновляемый сертификат не изменился. Обновление в базе данных не выполнялось.");
				
			} else {

				cert.setId(bufcerts.get(0).getId());
				cert.setId_beltpp(getBeltppID(cert));

				String sql_cert = "update owncertificate SET "
						+ " id_beltpp=:id_beltpp, type=:type, customername=:customername, customeraddress=:customeraddress, customerunp=:customerunp,"
						+ " factoryaddress=:factoryaddress, branches=:branches, datecert=STR_TO_DATE(:datecert,'%d.%m.%Y'), "
						+ " dateexpire = STR_TO_DATE(:dateexpire,'%d.%m.%Y'), "
						+ " expert = :expert, signer = :signer, signerjob = :signerjob, additionallists=:additionallists, "
						+ " datestart = STR_TO_DATE(:datestart,'%d.%m.%Y') "
						+ " WHERE id = :id ";

				SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);

				try {

					int row = template.update(sql_cert, parameters);
					System.out.println("Row updated = " + row);

					if (row > 0) {

						template.getJdbcOperations()
								.update("delete from ownproduct where id_certificate = ?",
										cert.getId());

						String sql_product = "insert into ownproduct(id_certificate, number, name, code) values ("
								+ cert.getId() + ", :number, :name, :code)";
						System.out.println(sql_product);

						if (cert.getProducts() != null
								&& cert.getProducts().size() > 0) {
							SqlParameterSource[] batch = SqlParameterSourceUtils
									.createBatch(cert.getProducts().toArray());
							int[] updateCounts = template.batchUpdate(
									sql_product, batch);
							System.out.println("Rows updated = "
									+ updateCounts.length);
						}
					}
				} catch (Exception ex) {
					LOG.info(ex.getMessage());
					throw (new cci.web.controller.cert.exception.NotFoundCertificateException(
							"Ошибка обновления найденного cертификата в базе данных: "
									+ ex.getMessage()));
				}
			}
		} else {
			throw (new cci.web.controller.cert.exception.NotFoundCertificateException(
					"Сертификат для обновления в базе данных не найден."));
		}

		return cert;
	}
}
