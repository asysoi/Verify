package cci.service.cert;

import java.util.Locale;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cci.model.cert.Certificate;
import cci.repository.cert.CertificateDAO;
import cci.service.utils.XMLService;

@Component
public class CertService {
	private static final Logger LOG = Logger.getLogger(CertService.class);

	@Autowired
	private CertificateDAO certificateDAO;

	@Autowired
	XMLService xmlService;
	
	

	// ------------------------------------------------------------------------------
	//
	// ------------------------------------------------------------------------------
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

	// ------------------------------------------------------------------------------
	//
	// ------------------------------------------------------------------------------
	public Certificate checkCertificate(Certificate cert) {
		Locale.setDefault(new Locale("en", "en"));

		Certificate rcert = null;
		try {
			rcert = certificateDAO.check(cert);
		} catch (Exception ex) {
			LOG.info("Certificate load error: " + ex.getMessage());
		}
		return rcert;
	}

	
}
