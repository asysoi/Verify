package cci.cert.service;

import java.io.*;

public class ConvertUT8BOMtoUTF8 {

	// FEFF because this is the Unicode char represented by the UTF-8 byte order
	// mark (EF BB BF).
	public static final String UTF8_BOM = "\uFEFF";

	public static void convert(String args[]) {
		try {
			if (args.length != 2) {
				System.out
						.println("Usage : java UTF8ToAnsiUtils utf8file ansifile");
				System.exit(1);
			}

			boolean firstLine = true;
			FileInputStream fis = new FileInputStream(args[0]);
			BufferedReader r = new BufferedReader(new InputStreamReader(fis,
					"UTF8"));
			FileOutputStream fos = new FileOutputStream(args[1]);
			Writer w = new BufferedWriter(new OutputStreamWriter(fos, "Cp1252"));
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
			System.exit(0);
		}

		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
			
		    if (!(s.indexOf("<?") != -1 &&  s.indexOf("xml") != -1 && s.indexOf("encoding")!=-1)) {
		    	s += ("<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + System.getProperty("line.separator"));  
		    }
	 	}
		return s;
	}
}
