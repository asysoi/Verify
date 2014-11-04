package cci.cert.form.listener;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cci.cert.certificate.Config;
import cci.cert.repository.FormRepository;
import cci.cert.service.CERTReader;
import cci.cert.service.CERTService;
import cci.cert.service.FSReader;
import cci.cert.service.FTPReader;

public class StartMenuListener implements ActionListener {
	
	static final Logger LOG = Logger.getLogger(StartMenuListener.class);
	
	public void actionPerformed(ActionEvent e) {
		MenuItem item = (MenuItem) e.getSource();
		
        try {
        	
    		if (Config.M_START.equals(item.getLabel())) {
    			LOG.info("Loading started...");
    			ApplicationContext context = new FileSystemXmlApplicationContext(
        				"conf/jdbcconfig.xml");
            	CERTService service = context.getBean("CERTService",
            			CERTService.class);
            	
            	CERTReader reader = context.getBean("FSReader",
            			FSReader.class);
            	
            	reader = context.getBean("FTPReader",
            			FTPReader.class);

            	service.setReader(reader);
            	FormRepository.getInstance().setService(service);
            	
            	service.start();
	            item.setLabel(Config.M_STOP);
    		} else {
    			LOG.info("Sending a finish signal...");
    			FormRepository.getInstance().getService().setFinished(true);
    			item.setLabel(Config.M_START);
    			LOG.info("Finish signal has been sent...");
    		}
        	
		} catch (Exception ex) {
			LOG.error("Browser launch error. " + ex.getMessage());
		}
	}
};
