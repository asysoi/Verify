package cci.cert.pdfbuilder;

import cci.cert.model.Certificate;
import cci.cert.service.CountryConverter;

public class CT1bPDFBuilder extends PDFBuilder {
	

	public String getCertificateTextByMap(Certificate certificate, String map) {

		String str = "";

		if ("certnumber".equals(map)) {
			str = certificate.getNomercert();
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
		}  else if ("listnumber".equals(map)) {
			str = "" + certificate.getCurrentlist();  
		}

		return str;
	}
}
