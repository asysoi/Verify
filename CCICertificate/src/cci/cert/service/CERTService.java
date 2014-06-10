package cci.cert.service;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import cci.cert.config.Config;
import cci.cert.model.Certificate;
import cci.cert.repositiry.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class CERTService  {

	@Autowired
	private CertificateDAO  certificateDAO;
	
	@Autowired
	XMLService xmlService;
	
	public List<Certificate> readCertificatesPage(int page, int pagesize) {
		//Locale.setDefault(new Locale("ru", "ru"));

		List<Certificate> certs = null;
		
		try {
			certs = certificateDAO.findNextPage(page, pagesize);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return certs;
	}
	

	public void readAllCertificates() {


		for (Certificate cert : certificateDAO.findAll()) {
		   System.out.println("FindAll: " + cert.getCert_id());
		}
	}

	public Certificate readCertificate(long cert_id) {
		
		Certificate cert = null;
		try {
		   cert= certificateDAO.findByID(cert_id);
		   System.out.println("FindByID: " + cert.getCert_id() + " Продуктов: "+
		   cert.getProducts().size());
		} catch (Exception ex) {
		   ex.printStackTrace();
		}
		
		return cert;
	}
	
	public void uploadCertificate() {

		try {
			Certificate cert;
			Long start = System.currentTimeMillis();
			int i = 0;

			for (String filename : new File(Config.XMLPATH).list()) {
				try {
					i++;
					cert = xmlService
							.loadCertificate(Config.XMLPATH + filename);
					printCertificate(cert);
					certificateDAO.save(cert);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("Duration: "
					+ (System.currentTimeMillis() - start));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void printCertificate(Certificate cert) {
		System.out.println(cert.getCert_id() + ": " + cert.getDatacert() + " | " + cert.getNomercert() + "  |  " + cert.getNblanka() );
	}


	public Certificate checkCertificate(String certnum, String certblank,
			String certdate) {
		
        Certificate cert = null;
		try {
			   cert= certificateDAO.check(certnum, certblank, certdate);
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		return cert;
	}
	
}
