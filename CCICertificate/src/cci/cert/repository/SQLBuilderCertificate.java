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
				
				if (!condition.getValue().trim().isEmpty() && !condition.getOperator().trim().isEmpty()) {
				    where += (where.isEmpty() ? "" : " AND ")
						+ condition.getWhereClause();
			    }
            
			    if (getFilter().getConditions().containsKey("TOVAR") && !getFilter().getConditions().get("TOVAR").getValue().isEmpty()) {
				     where += (where.isEmpty() ? "" : " AND ") + 
						"cert_id in ( SELECT cert_id FROM C_Product where upper(tovar) like '%" +getFilter().getFullsearchvalue() + "%')";

			    }
			
			    where = (where.isEmpty() ? "" : " where " + where);
			}
			
		}
		return where;
	}

}
