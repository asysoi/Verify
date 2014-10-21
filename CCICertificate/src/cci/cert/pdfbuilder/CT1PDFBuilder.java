package cci.cert.pdfbuilder;

import cci.cert.model.Certificate;
import cci.cert.service.CountryConverter;

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
			str = certificate.getMarshrut() == null ? "" : certificate.getMarshrut();
		} else if ("certnumber".equals(map)) {
			str = certificate.getNomercert();
		} else if ("blanknumber".equals(map)) {
			str = certificate.getNblanka();
		} else if ("country".equals(map)) {
			str = CountryConverter.getCountryNameByCode(certificate.getStranapr());
		} else if ("note".equals(map)) {
			str = certificate.getOtmetka() == null ? "" : certificate.getOtmetka();
		} else if ("department".equals(map)) {
			str = "Унитарное предприятие по оказанию услуг "
					+ '"' + certificate.getOtd_name() + '"' + " "
					+ certificate.getOtd_address_index() + ", "
					+ certificate.getOtd_address_city() + ", "
					+ certificate.getOtd_address_line() + ", "
					+ certificate.getOtd_address_home() + "";
		} else if ("expert".equals(map)) {
			str = certificate.getExpert() == null ? "" : certificate.getExpert() ;
		} else if ("customer".equals(map)) {
			str = certificate.getRukovod() == null ? "" : certificate.getRukovod();
		} else if ("dateexpert".equals(map)) {
			str = certificate.getDatacert();
		} else if ("datecustomer".equals(map)) {
			str = certificate.getDatacert();
		} else if ("issuecountry".equals(map)) {
			str = (certificate.getStranap() == null ? "Республике Беларусь" : CountryConverter.getCountryNameByCode(certificate.getStranap()));
		} 


		return str;
	}
}
