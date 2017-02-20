package cci.service.fscert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cci.model.fscert.FSCertificate;
import cci.repository.cert.CertificateDAO;
import cci.repository.fscert.FSCertificateDAO;
import cci.web.controller.fscert.FSFilter;

@Service
public class FSCertificateRestFulService {
	
	@Autowired
	private FSCertificateDAO fscertificateDAO;

	public String getFSCertificates(FSFilter filter) throws Exception {
		return fscertificateDAO.getFSCertificates(filter);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public FSCertificate addCertificate(FSCertificate certificate) throws Exception {
		return fscertificateDAO.saveFSCertificate(certificate);
	}

	public FSCertificate getFSCertificateByNumber(String number) throws Exception {
		return fscertificateDAO.getFSCertificateByNumber(number);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public FSCertificate updateFSCertificate(FSCertificate certificate, String branch_id) throws Exception {
		return fscertificateDAO.updateFSCertificate(certificate, branch_id);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public String deleteFSCertificate(String certnumber, String branch_id) throws Exception {
		return fscertificateDAO.deleteFSCertificate(certnumber, branch_id);
	}
	
}
