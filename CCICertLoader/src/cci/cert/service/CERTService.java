package cci.cert.service;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repositiry.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class CERTService extends Thread {

	@Autowired
	private CertificateDAO  certificateDAO;
	
	@Autowired
	private XMLService xmlService;
	
	@Autowired
	private FTPReader ftpReader;
	
	public void run() {
		uploadCertificateFromFTP();
	}
	
	public void setFinished(boolean exitflag) {
		ftpReader.finished(exitflag);
	}
	
	public List<Certificate> readCertificatesPage(int page, int pagesize) {
		Locale.setDefault(new Locale("en", "us"));

		List<Certificate> certs = null;
		
		try {
			certs = certificateDAO.findNextPage(page, pagesize);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return certs;
	}
	

	public void readAllCertificates() {
		Locale.setDefault(new Locale("en", "us"));   
        List<Certificate> certs = certificateDAO.findAll();
        		
		for (Certificate cert : certs) {
		   System.out.println("FindAll: " + cert.getCert_id());
		}
		System.out.println("Количество сертификатов: " + certs.size());
	}

	public Certificate readCertificate(long cert_id) {
		Locale.setDefault(new Locale("en", "us"));
		
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
			
		
	public void printCertificate(Certificate cert) {
		System.out.println(cert.getCert_id() + ": " + cert.getDatacert() + " | " + cert.getNomercert() + "  |  " + cert.getNblanka() );
	}


	public Certificate checkCertificate(String certnum, String certblank,
			String certdate) {
		Locale.setDefault(new Locale("en", "us"));
		
        Certificate cert = null;
		try {
			   cert= certificateDAO.check(cert);
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		return cert;
	}


	public Certificate checkCertificate(Certificate cert) {
		Locale.setDefault(new Locale("en", "us"));
		
        Certificate rcert = null;
		try {
			   rcert= certificateDAO.check(cert);
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		return rcert;
	}
	
	
	public void uploadCertificate() {
		Locale.setDefault(new Locale("en", "us"));
		
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

	
	
	public void uploadCertificateFromFTP() {
		Locale.setDefault(new Locale("en", "us"));
		
		try {
			Long start = System.currentTimeMillis();
			ftpReader.load(certificateDAO);
			System.out.println("Duration: "
					+ (System.currentTimeMillis() - start));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
}
