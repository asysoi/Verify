package cci.cert.service;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cci.cert.config.Config;
import cci.cert.model.Certificate;
import cci.cert.repository.CertificateDAO;
import cci.cert.repository.SQLBuilder;
import cci.cert.util.XMLService;
import cci.purchase.service.FilterCondition;

@Component
public class CERTService {

	@Autowired
	private CertificateDAO certificateDAO;

	@Autowired
	XMLService xmlService;

	@Autowired
	FTPReader ftpReader;
	private List<String> departments = null;
	private List<String> forms = null;
	private Map<String, String> countries = null;
	

	public List<Certificate> readCertificatesPage(int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<Certificate> certs = null;

		try {
			certs = certificateDAO.findViewNextPage(page, pagesize, orderby,
					order, builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return certs;
	}

	public void readAllCertificates() {
		Locale.setDefault(new Locale("en", "en"));

		for (Certificate cert : certificateDAO.findAll()) {
			System.out.println("FindAll: " + cert.getCert_id());
		}
	}

	public Certificate readCertificate(long cert_id) {
		Locale.setDefault(new Locale("en", "en"));

		Certificate cert = null;
		try {
			cert = certificateDAO.findByID(cert_id);
			System.out.println("FindByID: " + cert.getCert_id()
					+ " Продуктов: " + cert.getProducts().size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return cert;
	}

	public void uploadCertificate() {
		Locale.setDefault(new Locale("en", "en"));

		try {
			Certificate cert;
			Long start = System.currentTimeMillis();
			int i = 0;

			for (String filename : new File(Config.XMLPATH).list()) {
				try {
					i++;
					cert = xmlService
							.loadCertificate(Config.XMLPATH + filename);
					printCertificate(cert);
					certificateDAO.save(cert);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("Duration: "
					+ (System.currentTimeMillis() - start));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void uploadCertificateFromFTP() {
		Locale.setDefault(new Locale("en", "en"));

		try {
			Long start = System.currentTimeMillis();
			ftpReader.load(certificateDAO);
			System.out.println("Duration: "
					+ (System.currentTimeMillis() - start));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void printCertificate(Certificate cert) {
		System.out.println(cert.getCert_id() + ": " + cert.getDatacert()
				+ " | " + cert.getNomercert() + "  |  " + cert.getNblanka());
	}

	public Certificate checkCertificate(String certnum, String certblank,
			String certdate) {
		Locale.setDefault(new Locale("en", "en"));

		Certificate cert = null;
		try {
			cert = certificateDAO.check(cert);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return cert;
	}

	public Certificate checkCertificate(Certificate cert) {
		Locale.setDefault(new Locale("en", "en"));

		Certificate rcert = null;
		try {
			rcert = certificateDAO.check(cert);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rcert;
	}

	public int getViewPageCount(SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));
		int counter = 0;
		try {
			counter = certificateDAO.getViewPageCount(builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return counter;
	}

	public List<String> getDepartmentsList() {
		if (departments == null) {
			Locale.setDefault(new Locale("en", "en"));
			try {
				departments = certificateDAO.getDepartmentsList();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return departments;
	}

	public Map<String, String> getCountriesList() {
		if (countries == null) {
			Locale.setDefault(new Locale("en", "en"));
			try {
				countries = certificateDAO.getCountriesList();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return countries;
	}

	public List<String> getFormsList() {
		if (forms == null) {
			Locale.setDefault(new Locale("en", "en"));
			try {
				forms = certificateDAO.getFormsList();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return forms;
	}

	public List<Certificate> readCertificates(String orderby, String order,
			SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<Certificate> certs = null;

		try {
			certs = certificateDAO.getCertificates(orderby,
					order, builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return certs;
	}
}
