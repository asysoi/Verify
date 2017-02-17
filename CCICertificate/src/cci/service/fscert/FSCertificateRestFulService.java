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

	public String getFSCertificates(FSFilter filter) {
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public FSCertificate addCertificate(FSCertificate certificate) throws Exception {
		return certificateDAO.saveFSCertificate(certificate);
	}

	public FSCertificate getFSCertificateByNumber(String number) {
		return certificateDAO.getFSCertificateByNumber(number);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public FSCertificate updateFSCertificate(FSCertificate certificate, String branch_id) {
		return certificateDAO.updateFSCertificate(certificate, branch_id);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteFSCertificate(String number, String otd_id) {
		
	}
	
}
