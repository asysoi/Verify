package cci.cert.service;

import java.io.*;

import org.apache.log4j.Logger;

public class ConvertUT8BOMtoUTF8 {

	// FEFF because this is the Unicode char represented by the UTF-8 byte order
	// mark (EF BB BF).
	static final Logger LOG = Logger.getLogger(FTPReader.class);
	public static final String UTF8_BOM = "\uFEFF";

	public static void main(String args[]) {
		try {
			String din = "D:\\Certifications\\data\\xml\\repo\\gomel";
			String dout = "D:\\Certifications\\data\\xml\\source\\gomel";

			for (File file : new File(din).listFiles()) {
				if (file.isFile()) {
					boolean firstLine = true;

					FileInputStream fis = new FileInputStream(file);
					BufferedReader r = new BufferedReader(
							new InputStreamReader(fis));
					FileOutputStream fos = new FileOutputStream(dout + File.separator + file.getName());
					
					Writer w = new BufferedWriter(new OutputStreamWriter(fos));
					
					for (String s = ""; (s = r.readLine()) != null;) {
						if (firstLine) {
							s = removeUTF8BOM(s);
							firstLine = false;
						}
						w.write(s + System.getProperty("line.separator"));
						w.flush();
					}

					w.close();
					r.close();
					LOG.info("Process file: " + file.getPath());
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	private static String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		
		if (!(s.indexOf("<?") != -1 && s.indexOf("xml") != -1 && s
				.indexOf("encoding") != -1)) {
			s = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + System
					.getProperty("line.separator") + s ;
		}
		return s;
	}
}
