package cci.service.owncert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cci.controller.Filter;
import cci.model.OwnCertificate;
import cci.model.OwnCertificateHeaders;
import cci.model.OwnCertificates;
import cci.repository.cert.JDBCOwnCertificateDAO;

@Service
public class OwnCertificateService {

	@Autowired
	private JDBCOwnCertificateDAO certificateDAO;

	public OwnCertificates getOwnCertificates(Filter filter) {
	   return certificateDAO.getOwnCertificates(filter, true);
	}
	
	public OwnCertificateHeaders getOwnCertificateHeaders(Filter filter) {
		   return certificateDAO.getOwnCertificateHeaders(filter, true);
	}
	
	public OwnCertificate getOwnCertificateById(int id) throws Exception {
		return certificateDAO.findOwnCertificateByID(id);
	}
	
	public void addOwnSertificate(OwnCertificate certificate) throws Exception {
		certificateDAO.saveOwnCertificate(certificate);
	}

	public OwnCertificate updateOwnCertificate(OwnCertificate certificate) {
		return certificateDAO.updateOwnCertificate(certificate);
	} 

	public Object deleteOwnCertificate(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
