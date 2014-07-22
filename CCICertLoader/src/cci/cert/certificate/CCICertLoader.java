package cci.cert.certificate;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cci.cert.form.OTRSApplicationAWT;
import cci.cert.service.CERTService;

public class CCICertLoader {

	static final Logger LOG = Logger.getLogger(CCICertLoader.class);
	
	public static void main(String[] args) {
		new CCICertLoader().start();
	}

	public void start() {  

		LOG.info("Certificate Loader started");  
		
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"conf/jdbcconfig.xml");
		
		CERTService service = context.getBean("CERTService",
			CERTService.class);
		
		new OTRSApplicationAWT().start();
		
		
		//service.uploadCertificateFromFTP();
	}
	
}
