package cci.cert.service;

public class FilterCertificate extends Filter {

	public FilterCertificate() {
		String[] fields = new String[] { "CERT_ID", "FORMS", "UNN", "KONTRP",
				"KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT",
				"ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD",
				"TRANSPORT", "MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR",
				"STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS",
				"EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ",
				"FLSEZREZ", "STRANAP", "OTD_ID", "PARENTNUMBER",
				"PARENTSTATUS", "CHLDNUMBER", "CHILD_ID", "OTD_NAME", "NAME",
				"OTD_ADDRESS_INDEX", "ADDR_CITY", "ADDR_LINE", "ADDR_BUILDING", "TOVAR", "DENORM" };
		
		this.init(fields);
	}
	
	
	public void setFullsearchvalue(String fullsearchvalue) {
		super.setFullsearchvalue(fullsearchvalue);
		
		getConditions().get("DENORM").setOperator("like");
		getConditions().get("DENORM").setValue(fullsearchvalue);
		
	}
}
