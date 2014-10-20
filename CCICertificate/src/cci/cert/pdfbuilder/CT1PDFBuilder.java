package cci.cert.pdfbuilder;

import cci.cert.model.Certificate;

public class CT1PDFBuilder extends PDFBuilder {
	

	public String getCertificateTextByMap(Certificate certificate, String map) {

		String str = "";

		if ("exporter".equals(map)) {
			if (certificate.getKontrp() != null) {
			   str = certificate.getKontrp()  
				+ certificate.getAdress();
			}
		} else if ("importer".equals(map)) {
			if (certificate.getImporter() != null) {
			  str = certificate.getImporter();
			}
		} else if ("use".equals(map)) {
			str = certificate.getAdress();
		} else if ("number".equals(map)) {
			str = certificate.getAdressimp();
		} else if ("listammount".equals(map)) {
			str = "";
		}

		return str;
	}
}
