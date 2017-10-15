package cci.service.cert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cci.model.cert.Certificate;
import cci.model.cert.CertificateList;
import cci.repository.cert.CertificateDAO;

@Service
public class CertificateRestFulService {

	@Autowired
	private CertificateDAO certificateDAO;

	public Certificate getCertificateByNumber(String number, String blanknumber) throws Exception {
		return certificateDAO.getCertificateByNumber(number, blanknumber);
	}

}
