package cci.cert.repository;

import cci.purchase.service.FilterCondition;

public class SQLBuilderCertificate extends SQLBuilder {
	private String[] activefields = new String[] { "KONTRP", "KONTRS",
			"ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT", "NOMERCERT",
			"EXPERT", "NBLANKA", "RUKOVOD", "TRANSPORT", "MARSHRUT", "OTMETKA",
			"STRANAV", "STRANAPR", "OTD_NAME", "NAME" };

	public String getWhereClause() {
		String where = "";

		if (getFilter() != null) {
            
			for (String key : getFilter().getConditions().keySet()) {
				FilterCondition condition =getFilter().getConditions().get(key);
				
				if (condition.getValue() != null && condition.getOperator() != null && !condition.getValue().trim().isEmpty() && !condition.getOperator().trim().isEmpty()) {
					
					if (key.equals("TOVAR")) {
					     where += (where.trim().isEmpty() ? "" : " AND ") + 
									"cert_id in ( SELECT cert_id FROM C_Product where " +
									condition.getWhereClause() + 
									") ";
					} else {
				       where += (where.trim().isEmpty() ? "" : " AND ")
					  	         +  condition.getWhereClause();
					}
			    }
			}
		    where = (where.trim().isEmpty() ? "" : " where " + where);
			
		}
		return where;
	}

}
