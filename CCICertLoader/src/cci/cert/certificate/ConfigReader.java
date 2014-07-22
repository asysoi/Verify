package cci.cert.certificate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class ConfigReader {
	private final String sFileName = "config.properties";
	private final String xFileName = "config.xml";
	private String sDirSeparator = System.getProperty("file.separator");
	private static ConfigReader configurator;

	private ConfigReader() {
	}

	public static ConfigReader getInstance() {
		if (configurator == null) {
			configurator = new ConfigReader();
		}
		return configurator;
	}

	public Properties readConfig() throws FileNotFoundException, IOException {
		File currentDir = new File(".");
		Properties props = new Properties();

		String sFilePath = currentDir.getCanonicalPath() + sDirSeparator
				+ sFileName;
		System.out.println(sFilePath);
		FileInputStream ins = new FileInputStream(sFilePath);
		props.load(ins);
		ins.close();

		return props;
	}

	public Properties readConfigXML() throws FileNotFoundException, IOException {
		File currentDir = new File(".");
		Properties props = new Properties();

		String xFilePath = currentDir.getCanonicalPath() + sDirSeparator
				+ xFileName;
		FileInputStream ins = new FileInputStream(xFilePath);
		props.loadFromXML(ins);
		ins.close();

		return props;
	}

	public void storeConfig(Properties props) throws FileNotFoundException,
			IOException {
		File currentDir = new File(".");

		String sFilePath = currentDir.getCanonicalPath() + sDirSeparator
				+ sFileName;
		System.out.println(sFilePath);

		FileOutputStream out = new FileOutputStream(sFilePath);
		props.store(out, "");
		out.close();

	}

	public void storeConfigXML(Properties props) throws FileNotFoundException,
			IOException {
		File currentDir = new File(".");

		String xFilePath = currentDir.getCanonicalPath() + sDirSeparator
				+ xFileName;

		FileOutputStream out = new FileOutputStream(xFilePath);
		props.storeToXML(out, "");
		out.close();

	}

}
