package cci.service.cert;

import java.util.List;
import java.util.Locale;





import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.model.cert.Certificate;
import cci.repository.SQLBuilder;
import cci.repository.cert.CertificateDAO;

@Component
public class ReportService {
	private static final Logger LOG = Logger.getLogger(ReportService.class);
	
	@Autowired
	private CertificateDAO certificateDAO;
	
	// --------------------------
	// Get list of Certificates 
	// --------------------------
	public List<Certificate> readCertificatesPage(String[] fields, int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<Certificate> certs = null;
		LOG.debug("List");
		try {
			certs = certificateDAO.findViewNextReportPage(fields, page, pagesize, orderby,
					order, builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return certs;
	}

	// --------------------------
	//  Get count of Certificates
	// --------------------------
	public int getViewPageCount(SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));
		int counter = 0;
		try {
			LOG.debug("Counter");
			counter = certificateDAO.getViewPageReportCount(builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return counter;
	}

	// --------------------------
	//  Get Certificates for download
	// --------------------------
	public List readCertificates(String[] dbfields, String orderby, String order,
			SQLBuilder builder) {
		
		Locale.setDefault(new Locale("en", "en"));

		List<Certificate> certs = null;

		try {
			certs = certificateDAO.getReportCertificates(dbfields, orderby, order, builder);
		} catch (Exception ex) {
			LOG.info("Ошибка загрузки списка сертификатов для выгрузки: " + ex.toString());
		}

		return certs;
	}
}
