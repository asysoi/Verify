package cci.service.owncert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cci.model.owncert.OwnCertificate;
import cci.model.owncert.OwnCertificateHeaders;
import cci.model.owncert.OwnCertificates;
import cci.repository.owncert.JDBCOwnCertificateDAO;
import cci.repository.owncert.OwnCertificateDAO;
import cci.web.controller.owncert.Filter;


@Service
public class OwnCertificateService {

	@Autowired
	private OwnCertificateDAO owncertificateDAO;

	public OwnCertificates getOwnCertificates(Filter filter) {
	   return owncertificateDAO.getOwnCertificates(filter, true);
	}
	
	public OwnCertificateHeaders getOwnCertificateHeaders(Filter filter) {
		   return owncertificateDAO.getOwnCertificateHeaders(filter, true);
	}
	
	public OwnCertificate getOwnCertificateById(int id) throws Exception {
		return owncertificateDAO.findOwnCertificateByID(id);
	}
	
	public void addOwnSertificate(OwnCertificate certificate) throws Exception {
		owncertificateDAO.saveOwnCertificate(certificate);
	}

	public OwnCertificate updateOwnCertificate(OwnCertificate certificate) {
		return owncertificateDAO.updateOwnCertificate(certificate);
	} 

	public Object deleteOwnCertificate(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
