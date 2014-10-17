package cci.cert.form.listener;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cci.cert.certificate.Config;
import cci.cert.repository.FormRepository;
import cci.cert.service.CERTService;

public class StartMenuListener implements ActionListener {
	
	static final Logger LOG = Logger.getLogger(StartMenuListener.class);
	
	public void actionPerformed(ActionEvent e) {
		MenuItem item = (MenuItem) e.getSource();
		
        try {
			// ProcessBuilder process = new ProcessBuilder(OTRSProperty.getInstance().getProperty(OTRS.PR_PATH), 
			//		      OTRSProperty.getInstance().getProperty(OTRS.PR_OTRS_URL) + "/otrs/index.pl?Action=Login&Lang=ru&User=" +
			//		      OTRSProperty.getInstance().getProperty(OTRS.PR_LOGIN) +  
			//		      "&Password=" +
			//		      OTRSProperty.getInstance().getProperty(OTRS.PR_PSW));
			// process.start();
        	
    		if (Config.M_START.equals(item.getLabel())) {
    			ApplicationContext context = new FileSystemXmlApplicationContext(
        				"conf/jdbcconfig.xml");
            	
            	CERTService service = context.getBean("CERTService",
            			CERTService.class);
            	LOG.info("Loading started...");
            	
            	FormRepository.getInstance().setService(service);
            	service.start();
	            item.setLabel(Config.M_STOP);
    		} else {
    			FormRepository.getInstance().getService().setFinished(true);
    			item.setLabel(Config.M_START);
    		}
        	

        	
        	
		} catch (Exception ex) {
			LOG.error("Browser launch error. " + ex.getMessage());
		}
	}
};
