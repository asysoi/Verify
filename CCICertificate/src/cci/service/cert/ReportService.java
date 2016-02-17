package cci.service.cert;

import java.util.List;
import java.util.Locale;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.model.cert.Certificate;
import cci.repository.cert.CertificateDAO;

@Component
public class ReportService {
	private static final Logger LOG = Logger.getLogger(ReportService.class);
	
	@Autowired
	private CertificateDAO certificateDAO;
	
	// --------------------------
	// Get list of Certificates 
	// --------------------------
	public List<Certificate> readCertificatesPage(int page, int pagesize,
			String orderby, String order, String datefrom, String dateto, String otd_name) {
		Locale.setDefault(new Locale("en", "en"));

		List<Certificate> certs = null;
		LOG.info("List");
		try {
			certs = certificateDAO.findViewNextReportPage(page, pagesize, orderby,
					order, datefrom, dateto, otd_name);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return certs;
	}

	// --------------------------
	//  Get count of Certificates
	// --------------------------
	public int getViewPageCount(String datefrom, String dateto, String otd_name) {
		Locale.setDefault(new Locale("en", "en"));
		int counter = 0;
		try {
			LOG.info("Counter");
			counter = certificateDAO.getViewPageReportCount(datefrom, dateto, otd_name);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return counter;
	}
}
