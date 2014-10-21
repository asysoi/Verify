package cci.cert.pdfbuilder;

import cci.cert.model.Certificate;

public class CT1PDFBuilder extends PDFBuilder {

	public String getCertificateTextByMap(Certificate certificate, String map) {

		String str = "";

		if ("exporter".equals(map)) {
			str = 
					(certificate.getExpp() != null && certificate.getExpp().trim() != "" ? certificate.getExpp()  + " " : "")   + 
					(certificate.getExpadress() != null && certificate.getExpadress().trim() != "" ? certificate.getExpadress() + " " : "") +
					((certificate.getExpp() != null && certificate.getExpp().trim() != "" ) || 
					(certificate.getExpadress() != null && certificate.getExpadress().trim() != "") ? "  по поручению  " : "") + 
					certificate.getKontrp() + " " + 
					certificate.getAdress();

		} else if ("importer".equals(map)) {
			str = 
					(certificate.getImporter() != null && certificate.getImporter().trim() != "" ? certificate.getImporter()  + " " : "")   + 
					(certificate.getAdressimp() != null && certificate.getAdressimp().trim() != "" ? certificate.getAdressimp() + " " : "") +
					((certificate.getImporter() != null && certificate.getImporter().trim() != "" ) || 
					(certificate.getAdressimp() != null && certificate.getAdressimp().trim() != "") ? "  по поручению  " : "") + 
					certificate.getPoluchat() + " " + 
					certificate.getAdresspol();
		} else if ("drive".equals(map)) {
			str = certificate.getMarshrut();
		} else if ("certnumber".equals(map)) {
			str = certificate.getNomercert();
		} else if ("blanknumber".equals(map)) {
			str = certificate.getNblanka();
		} else if ("country".equals(map)) {
			str = certificate.getStranapr();
		} else if ("note".equals(map)) {
			str = certificate.getOtmetka();
		} else if ("department".equals(map)) {
			str = "”нитарное предпри€тие по оказанию услуг "
					+ certificate.getOtd_name() + " "
					+ certificate.getOtd_address_index() + ", "
					+ certificate.getOtd_address_city() + ", "
					+ certificate.getOtd_address_line() + ", "
					+ certificate.getOtd_address_home() + ", ";
		} else if ("expert".equals(map)) {
			str = certificate.getDatacert() + " " + certificate.getExpert();
		} else if ("customer".equals(map)) {
			str = certificate.getDatacert() + " " + certificate.getRukovod();
			;
		}

		return str;
	}
}
