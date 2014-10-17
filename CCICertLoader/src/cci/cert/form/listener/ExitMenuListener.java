package cci.cert.form.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import cci.cert.certificate.CCICertLoader;
import cci.cert.certificate.Config;
import cci.cert.repository.FormRepository;


public class ExitMenuListener implements ActionListener {
	
	static final Logger LOG = Logger.getLogger(ExitMenuListener.class);
	
	public void actionPerformed(ActionEvent e) {

		FormRepository.getInstance().getTray()
				.remove(FormRepository.getInstance().getTrayIcon());
		if (FormRepository.getInstance().getService() != null) {
		    FormRepository.getInstance().getService().setFinished(true);
		}
		FormRepository.getInstance().getShell().dispose();
		FormRepository.getInstance().getDisplay().dispose();
		LOG.info("Application closed");
		System.exit(0);
	}

}
