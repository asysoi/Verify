package cci.service.fscert;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cci.model.Employee;
import cci.model.fscert.Branch;
import cci.model.fscert.Expert;
import cci.model.fscert.FSCertificate;
import cci.model.owncert.OwnCertificate;
import cci.repository.SQLBuilder;
import cci.repository.fscert.FSCertificateDAO;
import cci.repository.fscert.FSTranslate;
import cci.service.owncert.OwnCertificateService;
import cci.web.controller.fscert.ViewFSCertificate;

@Service
public class FSCertificateService {

    private static final Logger LOG=Logger.getLogger(FSCertificateService.class);
    
	@Autowired
	private FSCertificateDAO fscertificateDAO;
	private FSTranslate templates;

	// ------------------------------------------------------------------------------
	//  This method returns count of pages in certificate list 
	// ------------------------------------------------------------------------------
	public int getViewPageCount(SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));
		
		int counter = 0;
		try {
			counter = fscertificateDAO.getViewPageCount(builder);
		} catch (Exception ex) {
			LOG.info(ex.getMessage());
		}
		return counter;
	}

	// ------------------------------------------------------------------------------
	//  This method returns a current page of the certificate's list
	// ------------------------------------------------------------------------------
	public List<ViewFSCertificate> readCertificatesPage(String[] fields, int page, int pagesize, int pagecount,
			String orderby, String order, SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<ViewFSCertificate> certs = null;

		try {
			certs = fscertificateDAO.findViewNextPage(fields, page, pagesize, pagecount, orderby,
					order, builder);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.info(ex.getMessage());
		}
		return certs;
	}
	
	// ------------------------------------------------------------------------------
	//  Method for FS Certificate Export
	// ------------------------------------------------------------------------------
	public List<ViewFSCertificate> readCertificates(String[] fields, String orderby, String order, SQLBuilder builder) {
		List<ViewFSCertificate> certs = null;

		try {
			certs = fscertificateDAO.getCertificates(fields, orderby, order, builder);
		} catch (Exception ex) {
			LOG.info(ex.getMessage());
		}

		return certs;
	}
	
	// ------------------------------------------------------------------------------
	//  Method for FS Certificate Export
	// ------------------------------------------------------------------------------
	public FSCertificate getFSCertificateById(int id) throws Exception {
		return fscertificateDAO.findFSCertificateByID(id);
	}

	// ------------------------------------------------------------------------------
	//  Add or update FS certificate
	// ------------------------------------------------------------------------------
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void save(FSCertificate fscert)  throws Exception {
        if (fscert.getId() == 0) {
        	fscertificateDAO.saveFSCertificate(fscert);
        } else {
        	fscertificateDAO.updateFSCertificate(fscert);
        }
		
	}

	// ------------------------------------------------------------------------------
	//  Return string template for various needs 
	// ------------------------------------------------------------------------------
	public String getTemplate(String key, String lang) {
		String template = null;
		
        if (templates == null) {
        	templates = fscertificateDAO.loadTemplates();
        }
        
        template = (String)((Map) templates.get(key)).get(lang) ;
        
		return template;
	}

	// ------------------------------------------------------------------------------
	//  Get Expert object by UserName 
	// ------------------------------------------------------------------------------
	public Employee getEmployeeByUserName(String username) {
		return fscertificateDAO.getEmployeeByUserName(username);
	}


	// ------------------------------------------------------------------------------
	//  Get Branch object by Branch code 
	// ------------------------------------------------------------------------------
	public Branch getBranchByCode(String code_otd) {
		return fscertificateDAO.getBranchByCode(code_otd);
	}

	// ------------------------------------------------------------------------------
	//  Method for FS Certificate Export
	// ------------------------------------------------------------------------------	
	public FSCertificate getFSCertificateByNumber(String parentnumber) throws Exception {
		return fscertificateDAO.getFSCertificateByNumber(parentnumber);
	}

	
	// ------------------------------------------------------------------------------
	//  GetList cert numbers for autocomplete
	// ------------------------------------------------------------------------------	
	public String getListCertNumber(String term) {
		// TODO Auto-generated method stub
		return fscertificateDAO.getListCertNumber(term);
	}

}
