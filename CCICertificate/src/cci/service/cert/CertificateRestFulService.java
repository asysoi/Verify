package cci.service.cert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





import cci.model.cert.Certificate;
import cci.model.cert.CertificateList;
import cci.repository.cert.CertificateDAO;
import cci.web.controller.cert.Filter;

@Service
public class CertificateRestFulService {

	@Autowired
	private CertificateDAO certificateDAO;

	public String getCertificates(Filter filter) throws Exception {
	   return certificateDAO.getCertificates(filter, true);
	}

	public void addSertificate(Certificate certificate) {
		certificateDAO.save(certificate);
	}

	public Certificate getCertificateByNumber(String number, String blanknumber) {
		return certificateDAO.getCertificateByNumber(number, blanknumber);
	}

	public Certificate updateCertificate(Certificate certificate) throws Exception {
		return certificateDAO.update(certificate);
	}

	public void deleteCertificate(String number, String blanknumber) {
	  certificateDAO.deleteCertificate(number, blanknumber);
	}
}
