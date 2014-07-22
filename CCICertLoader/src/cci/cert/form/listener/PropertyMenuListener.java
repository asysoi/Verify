package cci.cert.form.listener;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cci.cert.form.PropertiesForm;
import cci.cert.repositiry.FormRepository;


public class PropertyMenuListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		// MenuItem item = (MenuItem) e.getSource();
		
		PropertiesForm pform = new PropertiesForm(FormRepository.getInstance().getShell(), SWT.ICON_INFORMATION
				| SWT.OK);
		pform.open();
	}
};
