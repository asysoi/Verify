package cci.cert.form;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import cci.cert.certificate.CCIProperty;
import cci.cert.certificate.Config;
import cci.cert.certificate.ConfigReader;
import cci.cert.form.listener.ApplicationListener;
import cci.cert.form.listener.ExitMenuListener;
import cci.cert.form.listener.PropertyMenuListener;
import cci.cert.form.listener.StartMenuListener;
import cci.cert.repositiry.FormRepository;
import cci.cert.util.DesEncrypter;

@Component
public class CERTApplicationAWT {
	
	public static Logger LOG=Logger.getLogger(CERTApplicationAWT.class);
	private MenuItem startItem;
	private MenuItem propertyItem;
	private MenuItem exitItem;

	public void start() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LOG.info("Application started" );
				new CERTApplicationAWT().createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {
		if (!SystemTray.isSupported()) {
			LOG.error("SystemTray не поддерживается в сиcтеме.");
			return;
		}

		loadImages();
		LOG.info(System.getProperty("user.dir"));
				
		final TrayIcon trayIcon = new TrayIcon(FormRepository.getInstance()
				.getLogoffImage(), "OTRS icon");
		

		final PopupMenu topMenu = new PopupMenu();
		startItem = new MenuItem(Config.M_START); 
		propertyItem = new MenuItem(Config.M_PROPERTY);
		exitItem = new MenuItem(Config.M_EXIT);

		// Add components to popup menu
		topMenu.add(startItem);
		topMenu.addSeparator();
		topMenu.add(propertyItem);
		topMenu.addSeparator();
		topMenu.add(exitItem);

		trayIcon.setPopupMenu(topMenu);
		// trayIcon.setToolTip(OTRS.APP_TIPS);
		trayIcon.setImageAutoSize(true);

		final SystemTray tray = SystemTray.getSystemTray();
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			LOG.error("Application can not placed in the system tray.");
			return;
		}
		FormRepository.getInstance().setTray(tray);
		FormRepository.getInstance().setTrayIcon(trayIcon);

		trayIcon.addActionListener(new ApplicationListener());
		startItem.addActionListener(new StartMenuListener());
		propertyItem.addActionListener(new PropertyMenuListener());
		exitItem.addActionListener(new ExitMenuListener());
		

		FormRepository.getInstance().setTopMenu(topMenu);

		try {
			LOG.info("Read application config file");
			CCIProperty props = CCIProperty.getInstance();
			props.setProperties(ConfigReader.getInstance().readConfig());
			DesEncrypter encrypter = new DesEncrypter();
			props.setProperty(Config.PSW,
					encrypter.decrypt(props.getProperty(Config.PSW)));
		} catch (FileNotFoundException ex) {
			LOG.info("Default properties loading ...");
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}

	private  Image createImage(String path, String description) {
		URL imageURL = CERTApplicationAWT.class.getResource(path);

		if (imageURL == null) {
			LOG.info("Ресурс не найден: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}

	private void loadImages() {
		FormRepository.getInstance().setLogonImage(
				createImage("images/l_g.gif", ""));
		FormRepository.getInstance().setLogoffImage(
				createImage("images/l_r.gif", ""));
		FormRepository.getInstance().setReminderImage(
				createImage("images/l_a.gif", ""));
	}


	private void setMenuEnabled(boolean enabled) {
		PopupMenu menu = FormRepository.getInstance().getTopMenu();

		for (int i = 0; i < menu.getItemCount(); i++) {
			if (menu.getItem(i).getLabel().equals("rrr")
					|| menu.getItem(i).getLabel().equals("ff")) {
				menu.getItem(i).setEnabled(enabled);
			}
		}
	}
}