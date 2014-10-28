package cci.cert.service;

import java.io.File;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repository.CertificateDAO;

@Component
public class CERTService extends Thread {

	@Autowired
	private CertificateDAO  certificateDAO;
	
	private CERTReader reader;
	
	public void run() {
		loadCertificate();
	}
	
	public void setFinished(boolean exitflag) {
		reader.finished(exitflag);
	}
	
	public void printCertificate(Certificate cert) {
		System.out.println(cert.getCert_id() + ": " + cert.getDatacert() + " | " + cert.getNomercert() + "  |  " + cert.getNblanka() );
	}

	
	public void loadCertificate() {
		Locale.setDefault(new Locale("en", "us"));
		
		try {
			Long start = System.currentTimeMillis();
			reader.load(certificateDAO);
			System.out.println("Duration: "
					+ (System.currentTimeMillis() - start));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public CERTReader getReader() {
		return reader;
	}

	public void setReader(CERTReader reader) {
		this.reader = reader;
	}
}
