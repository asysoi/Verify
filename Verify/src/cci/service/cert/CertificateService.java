package cci.service.cert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cci.model.cert.Certificate;
import cci.model.owncert.OwnCertificate;
import cci.repository.cert.CertificateDAO;
import cci.repository.owncert.OwnCertificateDAO;


@Service
public class CertificateService {
	private static final Logger LOG = Logger.getLogger(CertificateService.class);

	@Autowired
	private CertificateDAO certificateDAO;
	
	@Autowired
	private OwnCertificateDAO ownCertificateDAO;
	
	public Certificate getCertificate(String number, String blanknumber, String date) throws Exception {
		return certificateDAO.getCertificate(number, blanknumber, date);
	}
	
	// ------------------------------------------------------------------------------
	// Check and return certificate of origin
	// ------------------------------------------------------------------------------
	public Certificate checkCertificate(Certificate cert) {
		Locale.setDefault(new Locale("en", "en"));

		Certificate rcert = null;
		try {
			rcert = certificateDAO.checkCT(cert);
		} catch (Exception ex) {
			LOG.info("Certificate load error: " + ex.getMessage());
		}
		return rcert;
	}

	// ------------------------------------------------------------------------------
	// Check certificate of own production
	// ------------------------------------------------------------------------------
	public OwnCertificate checkOwnCertificate(Certificate cert) {
		OwnCertificate rcert = null;
		try {
			rcert = ownCertificateDAO.checkOwnExist(cert);
		} catch (Exception ex) {
			LOG.info("Certificate check error: " + ex.getMessage());
		}
		return rcert;
	}

	// ------------------------------------------------------------------------------
	// Get  own production
	// ------------------------------------------------------------------------------
	public OwnCertificate getOwnCertificate(Certificate cert) {
		OwnCertificate rcert = null;
		try {
			rcert = ownCertificateDAO.getOwnCertificate(cert);
		} catch (Exception ex) {
			LOG.info("Certificate load error: " + ex.getMessage());
		}
		return rcert;
	}
	
	/* ----------------------------------------------- 
	 * Convert certificate's
	 * numbers splited by delimeter into List 
	 * to write into certificate blanks
	 * ----------------------------------------------
	 */
	public List<String> splitOwnCertNumbers(String number, String addblanks) {
		List<String> ret = new ArrayList<String>();
		ret.add(number);
		if (addblanks != null && !addblanks.trim().isEmpty()) {

			addblanks = addblanks.trim().replaceAll("\\s*-\\s*", "-");
			addblanks = addblanks.replaceAll(",", ";");
			addblanks = addblanks.replaceAll("\\s+", ";");
			addblanks = addblanks.replaceAll(";+", ";");
			addblanks = addblanks.replaceAll(";\\D+;", ";");

			String[] lst = addblanks.split(";");

			for (String str : lst) {
				ret.addAll(getSequenceNumbers(str));
			}
		}
		return ret;
	}

	
	/* ----------------------------------------------- 
	 * Convert certificate's
	 * numbers range into List of separated numbers to write into certificate
	 * blanks 
	 * ---------------------------------------------- */
	private Collection<String> getSequenceNumbers(String addblanks) {
		List<String> numbers = new ArrayList<String>();
		int pos = addblanks.indexOf("-");
		
		if (pos > 0) {
			int firstnumber = Integer.parseInt((addblanks.substring(0, pos)));
			int lastnumber = Integer.parseInt(addblanks.substring(pos + 1));
			
			for (int i = firstnumber; i <= lastnumber; i++) {
				numbers.add(addnull(i + ""));
			}
		} else if (!addblanks.trim().isEmpty())
			numbers.add(addblanks);
		return numbers;
	}
	
	private String addnull(String number) {
		if (number.length() < 7) {
			number = addnull("0"+number);
		}
		return number;
	}

}
