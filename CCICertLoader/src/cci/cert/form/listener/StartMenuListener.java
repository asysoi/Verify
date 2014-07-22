package cci.cert.form.listener;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class StartMenuListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		MenuItem item = (MenuItem) e.getSource();
        try {
			// ProcessBuilder process = new ProcessBuilder(OTRSProperty.getInstance().getProperty(OTRS.PR_PATH), 
			//		      OTRSProperty.getInstance().getProperty(OTRS.PR_OTRS_URL) + "/otrs/index.pl?Action=Login&Lang=ru&User=" +
			//		      OTRSProperty.getInstance().getProperty(OTRS.PR_LOGIN) +  
			//		      "&Password=" +
			//		      OTRSProperty.getInstance().getProperty(OTRS.PR_PSW));
			// process.start();
		} catch (Exception ex) {
			// LOG.error("Browser launch error. " + ex.getMessage());
		}
	}
};
