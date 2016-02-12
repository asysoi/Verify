package cci.service.cert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.model.cert.Certificate;
import cci.model.cert.Report;
import cci.repository.SQLBuilder;
import cci.repository.cert.CertificateDAO;
import cci.util.cert.XMLService;

@Component
public class CertService {

	@Autowired
	private CertificateDAO certificateDAO;

	@Autowired
	XMLService xmlService;

	@Autowired
	FTPReader ftpReader;
	private List<String> departments = null;
	private List<String> forms = null;
	private Map<String, String> countries = null;
	private Map<String, String> acl = null;

	public Map<String, String> getACL() {
		if (acl == null) {
			Locale.setDefault(new Locale("en", "en"));
			try {
				acl = certificateDAO.getACL();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return acl;
	}

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

			for (String filename : new File("[path to file]").list()) {
				try {
					i++;
					cert = xmlService.loadCertificate("[path to file]"
							+ filename);
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
			certs = certificateDAO.getCertificates(orderby, order, builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return certs;
	}

	// ------------------------------------------------------------------------------
	//
	// ------------------------------------------------------------------------------
	public List<Report> makeReports(String[] fields, SQLBuilder builder,
			Boolean onfilter) {

		Locale.setDefault(new Locale("en", "en"));
		List<Report> reports = null;

		try {
			reports = certificateDAO.getReport(fields, builder, onfilter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return reports;
	}
}
