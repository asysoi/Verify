package cci.cert.certificate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cci.cert.form.CERTApplicationAWT;
import cci.cert.repository.SequenceGenerator;

public class CCICertLoader {

	static final Logger LOG = Logger.getLogger(CCICertLoader.class);

	public static void main(String[] args) {
		new CCICertLoader().start();
	}

	public void start() {

		LOG.info("Certificate Loader started");

		ApplicationContext context = new FileSystemXmlApplicationContext(
				"conf/jdbcconfig.xml");

		CERTApplicationAWT appl = context.getBean("CERTApplicationAWT",
				CERTApplicationAWT.class);
		
		appl.start();

	}

}
