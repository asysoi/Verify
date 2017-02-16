package cci.service.fscert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cci.model.cert.fscert.FSCertificate;
import cci.repository.cert.CertificateDAO;
import cci.web.controller.fscert.FSFilter;

@Service
public class FSCertificateRestFulService {
	@Autowired
	private CertificateDAO certificateDAO;

	public String getCertificates(FSFilter filter) {
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addCertificate(FSCertificate certificate) throws Exception {
		try {
		   certificateDAO.saveFSCertificate(certificate);
		} catch (Exception up) {
			throw up;
		}
	}

	public FSCertificate getCertificateByNumber(String number) {
		return null;
	}

	public FSCertificate updateCertificate(FSCertificate cert, String otd_id) {
		return null;
	}

	public void deleteCertificate(String number, String blanknumber, String otd_id) {
		
	}
	
}
