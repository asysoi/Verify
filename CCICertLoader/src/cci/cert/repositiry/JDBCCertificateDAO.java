package cci.cert.repositiry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cci.cert.certificate.CCICertLoader;
import cci.cert.model.Certificate;
import cci.cert.model.Product;

@Repository
public class JDBCCertificateDAO implements CertificateDAO {
	private static final Logger LOG = Logger.getLogger(JDBCCertificateDAO.class);
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	// поиск сертификата по id
	public Certificate findByID(Long id) {
		Certificate cert = null;

		try {
			String sql = "select * from CERT_VIEW WHERE cert_id = ?";
			cert = template.getJdbcOperations().queryForObject(sql,
					new Object[] { id },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			sql = "select * from c_PRODUCT WHERE cert_id = ? ORDER BY product_id";
			cert.setProducts(template.getJdbcOperations().query(sql,
					new Object[] { cert.getCert_id() },
					new BeanPropertyRowMapper<Product>(Product.class)));
		} catch (Exception ex) {
			LOG.error("Ошибка поиска сертификата по ID " + id);
			ex.printStackTrace();
		}
		return cert;
	}

	// поиск сертификата по id
	public Certificate check(Certificate cert) {
		Certificate rcert = null;

		try {
			String sql = "select * from CERT_VIEW WHERE NOMERCERT = ? AND NBLANKA = ? AND DATACERT=? ";
			rcert = template.getJdbcOperations().queryForObject(
					sql,
					new Object[] { cert.getNomercert(), cert.getNblanka(),
							cert.getDatacert() },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));
			if (rcert != null) {
				sql = "select * from c_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				rcert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { rcert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (EmptyResultDataAccessException emt) {
			LOG.error("Сертификат с номером  "
					+ cert.getNomercert() + " не найден. Возращено пустое значение.");
		} catch (Exception ex) {
			LOG.error("Проверка наличия в базе сертификата "
					+ cert.getNomercert() + " Ошибка: " + ex.getMessage());
			ex.printStackTrace();
		}
		return rcert;
	}

	// поиск скртификата по номеру бланка
	// возможно несколько сертификатов
	public List<Certificate> findByNBlanka(String number) {
		List<Certificate> certs = null;

		try {
			String sql = "select * from CERT_VIEW WHERE nblanka = ?";
			certs = template.getJdbcOperations().query(sql,
					new Object[] { number },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			for (Certificate cert : certs) {
				sql = "select * from c_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (Exception ex) {
			LOG.error("Ошибка поиска сертификата по номеру бланка " + number);
			ex.printStackTrace();
		}
		return certs;
	}

	// поиск сертификата по номеру сертификата
	public List<Certificate> findByNumberCert(String number) {
		List<Certificate> certs = null;

		try {
			String sql = "select * from CERT_VIEW WHERE nomercert = ?";
			certs = template.getJdbcOperations().query(sql,
					new Object[] { number },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			for (Certificate cert : certs) {
				sql = "select * from c_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (Exception ex) {
			LOG.error("Ошибка поиска сертификата по номеру сертификата "
					+ number);
			ex.printStackTrace();
		}
		return certs;
	}

	public List<Certificate> findNextPage(int pageindex, int pagesize) {

		String sql = " SELECT cert.* "
				+ " FROM (SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.NOMERCERT) rw FROM c_CERT t) cert "
				+ " WHERE cert.rw > " + ((pageindex - 1) * pagesize)
				+ " AND cert.rw <= " + (pageindex * pagesize);

		LOG.debug("SQL get next page : " + sql);
		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Certificate>(Certificate.class));
	}

	public List<Certificate> findAll() {
		String sql = "select * from c_CERT ORDER BY cert_id";

		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Certificate>(Certificate.class));
	}

	public long save(Certificate cert) {
		String sql_cert = "insert into c_cert values "
				+ "(beltpp_cert_test.cert_id_seq.nextval, "
				+ ":forms, :unn, :kontrp, :kontrs, :adress, :poluchat, :adresspol, :datacert,"
				+ ":nomercert, :expert, :nblanka, :rukovod, :transport, :marshrut, :otmetka,"
				+ ":stranav, :stranapr, :status, :koldoplist, :flexp, :unnexp, :expp, "
				+ ":exps, :expadress, :flimp, :importer, :adressimp, :flsez, :sez,"
				+ ":flsezrez, :stranap, :otd_id )";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		long cert_id = 0;

		try {
			int row = template.update(sql_cert, parameters, keyHolder,
					new String[] { "CERT_ID" });
			cert_id = keyHolder.getKey().longValue();

			String sql_product = "insert into c_PRODUCT values ("
					+ " beltpp_cert_test.product_id_seq.nextval, " + cert_id
					+ ", "
					+ " :numerator, :tovar, :vidup, :kriter, :ves, :schet)";

			if (cert.getProducts() != null && cert.getProducts().size() > 0) {
				SqlParameterSource[] batch = SqlParameterSourceUtils
						.createBatch(cert.getProducts().toArray());
				int[] updateCounts = template.batchUpdate(sql_product, batch);
			}

		} catch (Exception ex) {
			LOG.error("Ошибка добавления сертификата " + cert.getNomercert());
			ex.printStackTrace();
		}
		return cert_id;
	}

	public void update(Certificate cert) {

		String sql_cert = "update c_cert SET "
				+ "forms = :forms, unn = :unn, kontrp = :kontrp, kontrs = :kontrs, adress = :adress, poluchat = :poluchat, adresspol = :adresspol, datacert = :datacert,"
				+ "nomercert = :nomercert, expert = :expert, nblanka = :nblanka, rukovod = :rukovod, transport = :transport, marshrut = :marshrut, otmetka = :otmetka,"
				+ "stranav = :stranav, stranapr = :stranapr, status = :status, koldoplist = :koldoplist, flexp = :flexp, unnexp = :unnexp, expp = :expp, "
				+ "exps = :exps, expadress = :expadress, flimp = :flimp, importer = :importer, adressimp = :adressimp, flsez = :flsez, sez = :sez,"
				+ "flsezrez = :flsezrez, stranap = :stranap, otd_id = :otd_id "
				+ "WHERE cert_id = :cert_id";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);

		try {

			int row = template.update(sql_cert, parameters);

			template.getJdbcOperations().update(
					"delete from c_PRODUCT where cert_id = ?",
					Long.valueOf(cert.getCert_id()));

			String sql_product = "insert into c_PRODUCT values ("
					+ " beltpp_cert_test.product_id_seq.nextval, "
					+ cert.getCert_id() + ", "
					+ " :numerator, :tovar, :vidup, :kriter, :ves, :schet)";

			if (cert.getProducts() != null && cert.getProducts().size() > 0) {
				SqlParameterSource[] batch = SqlParameterSourceUtils
						.createBatch(cert.getProducts().toArray());
				int[] updateCounts = template.batchUpdate(sql_product, batch);
			}

		} catch (Exception ex) {
			LOG.error("Ошибка сохранения сертификата " + cert.getNomercert());
			ex.printStackTrace();
		}

	}

	public List<Certificate> findByCertificate(Certificate qcert) {
		List<Certificate> certs = null;

		String sql_cert = "SELECT * from c_cert WHERE "
				+ "datacert = :datacert AND nomercert = :nomercert AND nblanka = :nblanka";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				qcert);

		try {

			certs = template.query(sql_cert, parameters,
					new BeanPropertyRowMapper<Certificate>());

			for (Certificate cert : certs) {
				String sql = "select * from c_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>()));
			}

		} catch (Exception ex) {
			LOG.error("Ошибка поиска сертификатов по шаблону сертификата: "
					+ qcert);
			ex.printStackTrace();
		}

		return certs;
	}

	public long getOtdIdBySynonimName(String directory) {
		long id = 0;

		try {
			String sql = "select otd_id from C_OTD WHERE OTD_NAME_SYN = ?";
			id = template.getJdbcOperations().queryForObject(sql,
					new Object[] { directory }, Long.class);
		} catch (Exception ex) {
			LOG.error("Ошибка поиска ID отделения по его синониму: "
					+ directory);
			ex.printStackTrace();
		}

		return id;
	}

	public int saveFile(long cert_id, String lfile) {
		String sql = "insert into c_files_in(file_in_id, file_in_name, cert_id, date_load) values "
				+ "(beltpp_cert_test.file_id_seq.nextval, :file_in_name, :cert_id, SYSDATE)";
		int row = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("file_in_name", lfile);
		parameters.put("cert_id", cert_id);
	
		try {
			row = template.update(sql, parameters);
		} catch (Exception ex) {
			LOG.error("Ошибка сохранения записи о файле " +lfile  + " сертификата " + cert_id + " в таблицу file_in");
			ex.printStackTrace();
		}
		return row;
	}
}
