package cci.service.owncert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cci.model.cert.Certificate;
import cci.model.owncert.OwnCertificate;
import cci.model.owncert.OwnCertificateExport;
import cci.model.owncert.OwnCertificateHeaders;
import cci.model.owncert.OwnCertificates;
import cci.repository.SQLBuilder;
import cci.repository.owncert.JDBCOwnCertificateDAO;
import cci.repository.owncert.OwnCertificateDAO;
import cci.web.controller.cert.CertificateController;
import cci.web.controller.owncert.OwnFilter;


@Service
public class OwnCertificateService {
	private static final Logger LOG=Logger.getLogger(OwnCertificateService.class);
	
	@Autowired
	private OwnCertificateDAO owncertificateDAO;

	// ------------------------------------------------------------------------------
	//  This method returns count of pages in certificate list 
	// ------------------------------------------------------------------------------
	public int getViewPageCount(SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));
		
		int counter = 0;
		try {
			counter = owncertificateDAO.getViewPageCount(builder);
		} catch (Exception ex) {
			LOG.info(ex.getMessage());
		}
		return counter;
	}

	// ------------------------------------------------------------------------------
	//  This method returns a current page of the certificate's list
	// ------------------------------------------------------------------------------
	public List<OwnCertificate> readCertificatesPage(String[] fields, int page, int pagesize, int pagecount,
			String orderby, String order, SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<OwnCertificate> certs = null;

		try {
			certs = owncertificateDAO.findViewNextPage(fields, page, pagesize, pagecount, orderby,
					order, builder);
		} catch (Exception ex) {
			LOG.info(ex.getMessage());
		}
		return certs;
	}
	
	// ------------------------------------------------------------------------------
	//  Method for Own Certificate Export
	// ------------------------------------------------------------------------------
	public List<OwnCertificateExport> readCertificates(String[] fields, String orderby, String order, SQLBuilder builder) {
		List<OwnCertificateExport> certs = null;

		try {
			certs = owncertificateDAO.getCertificates(fields, orderby, order, builder);
		} catch (Exception ex) {
			LOG.info(ex.getMessage());
		}

		return certs;
	}
	
	// ------------------------------------------------------------------------------------
	//       RESTFUL methods  
	// ------------------------------------------------------------------------------------
	public OwnCertificates getOwnCertificates(OwnFilter filter)  {
	   return owncertificateDAO.getOwnCertificates(filter, true);
	}
	
	public OwnCertificateHeaders getOwnCertificateHeaders(OwnFilter filter) {
		   return owncertificateDAO.getOwnCertificateHeaders(filter, true);
	}
	
	public OwnCertificate getOwnCertificateById(int id) throws Exception {
		return owncertificateDAO.findOwnCertificateByID(id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addOwnSertificate(OwnCertificate certificate) throws Exception {
		owncertificateDAO.saveOwnCertificate(certificate);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OwnCertificate updateOwnCertificate(OwnCertificate certificate) throws Exception {
		return owncertificateDAO.updateOwnCertificate(certificate);
	} 

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Object deleteOwnCertificate(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public OwnCertificate getOwnCertificateByNumber(String number) throws Exception {
		return owncertificateDAO.findOwnCertificateByNumber(number);
	}

}
