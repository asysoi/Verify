package cci.repository.fscert;

import org.apache.log4j.Logger;

import cci.repository.SQLBuilder;
import cci.service.FilterCondition;
import cci.service.SQLQueryUnit;

public class SQLBuilderFSCertificate extends SQLBuilder {
private static final Logger LOG = Logger.getLogger(SQLBuilderFSCertificate.class);
	
	public SQLQueryUnit getSQLUnitWhereClause() {
		SQLQueryUnit qunit = new SQLQueryUnit();
		String where = "";

		if (getFilter() != null) {
            
			for (String key : getFilter().getConditions().keySet()) {
				FilterCondition condition = getFilter().getConditions().get(key);
				
				if (condition.getValue() != null && condition.getOperator() != null 
						&& !condition.getValue().trim().isEmpty() && !condition.getOperator().trim().isEmpty()) {
					
					SQLQueryUnit unit = condition.getWhereClause(); 
					if (key.equals("PRODUCTNAME") ) {
					     where += (where.trim().isEmpty() ? "" : " AND ") + 
									"id in ( SELECT id_fscert FROM fs_product where " +
									unit.getClause() + 
									") ";
					} else if(key.equals("BLANKNUMBER")) {
					     where += (where.trim().isEmpty() ? "" : " AND ") + 
									"id in ( SELECT id_fscert FROM fs_blank where " +
									unit.getClause() + 
									") ";
					} else if(key.equals("EXPORTERNAME")) {
						     where += (where.trim().isEmpty() ? "" : " AND ") + 
										"id_exporter in ( SELECT id FROM cci_client where " +
										unit.getClause() + 
										") ";
					} else if(key.equals("PRODUCERNAME")) {
					     where += (where.trim().isEmpty() ? "" : " AND ") + 
									"id_producer in ( SELECT id FROM cci_client where " +
									unit.getClause() + 
									") ";
					} else if(key.equals("EXPERTNAME")) {
					     where += (where.trim().isEmpty() ? "" : " AND ") + 
									"id_expert in ( SELECT id FROM cci_employee where " +
									unit.getClause() + 
									") ";
					} else if(key.equals("SIGNERNAME")) {
					     where += (where.trim().isEmpty() ? "" : " AND ") + 
									"id_signer in ( SELECT id FROM cci_employee where " +
									unit.getClause() + 
									") ";
					} else {
				       where += (where.trim().isEmpty() ? "" : " AND ")
					  	         +  unit.getClause();
					}
					qunit.getParams().putAll(unit.getParams());
			    }
			}
		    where = (where.trim().isEmpty() ? "" : " where " + where);
			
		}
		LOG.info("Where: " + where);
		LOG.info("Params: " + qunit.getParams());
		qunit.setClause(where);
		return qunit;
	}
}

