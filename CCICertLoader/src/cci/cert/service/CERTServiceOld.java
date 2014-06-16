package cci.cert.service;

import java.io.File;
import java.util.List;
import java.util.Locale;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repositiry.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class CERTServiceOld  {

	public void readCertificatePage(ApplicationContext context, int page, int pagesize) {
		CertificateDAO dao = context.getBean("certificateDAO",
				CertificateDAO.class);
		
		Locale.setDefault(new Locale("en", "us"));

		try {
			
		   List<Certificate> certs = dao.findNextPage(1, 50);
		   System.out.println(certs.size());
		   
		   for (Certificate cert : certs) {
		     System.out.println("FindNextPage: " + cert.getCert_id());
		   }
		} catch (Exception ex) {
		 ex.printStackTrace();
		}
		
	}

	public void readAllCertificate(ApplicationContext context) {
		CertificateDAO dao = context.getBean("certificateDAO",
				CertificateDAO.class);
		
		Locale.setDefault(new Locale("en", "us"));

		for (Certificate cert : dao.findAll()) {
		   System.out.println("FindAll: " + cert.getCert_id());
		}
	}

	public void readCertificate(ApplicationContext context, long cert_id) {
		CertificateDAO dao = context.getBean("certificateDAO",
				CertificateDAO.class);
		
		Locale.setDefault(new Locale("en", "us"));

		try {
		   Certificate cert = dao.findByID(cert_id);
		   System.out.println("FindByID: " + cert.getCert_id() + " Продуктов: "+
		   cert.getProducts().size());
		} catch (Exception ex) {
		   ex.printStackTrace();
		}

		for (Certificate cert : dao.findAll()) {
		   System.out.println("FindAll: " + cert.getCert_id());
		}
	}
	
	public void uploadCertificate(ApplicationContext context) {
		CertificateDAO dao = context.getBean("certificateDAO",
				CertificateDAO.class);
		Locale.setDefault(new Locale("en", "us"));

		XMLService xmlservice = context.getBean("XMLService", XMLService.class);

		try {
			Certificate cert;
			Long start = System.currentTimeMillis();
			int i = 0;

			for (String filename : new File(Config.XMLPATH).list()) {
				try {
					i++;
					cert = xmlservice
							.loadCertificate(Config.XMLPATH + filename);
					printCertificate(cert);
					dao.save(cert);
				} catch (Exception ex) {
					System.out
							.println("===============================================================");
					System.out.println(Config.XMLPATH + filename);
					ex.printStackTrace();
				}
			}
			System.out.println("Added: " + i + " certificates");
			System.out.println("Duration: "
					+ (System.currentTimeMillis() - start));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void printCertificate(Certificate cert) {
		System.out.println(cert.getCert_id() + ": " + cert.getDatacert() + " | " + cert.getNomercert() + "  |  " + cert.getNblanka() );
	}
	
	
}
