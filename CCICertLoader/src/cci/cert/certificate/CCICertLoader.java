package cci.cert.certificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cci.cert.model.Certificate;
import cci.cert.repositiry.CertificateDAO;
import cci.cert.service.CERTService;

public class CCICertLoader {

	
	public static void main(String[] args) {
		new CCICertLoader().start();
	}

	public void start() {
		System.out.println("Certificate Loader started...");

		ApplicationContext context = new FileSystemXmlApplicationContext(
				"conf/jdbcconfig.xml");
		
		CERTService service = context.getBean("CERTService",
			CERTService.class);
		
		service.uploadCertificateFromFTP();
	
	}
	
}
