package cci.repository.client;

import org.apache.log4j.Logger;

import cci.repository.SQLBuilder;
import cci.repository.fscert.SQLBuilderFSCertificate;
import cci.service.FilterCondition;
import cci.service.SQLQueryUnit;

public class SQLBuilderClient extends SQLBuilder {

	private static final Logger LOG = Logger.getLogger(SQLBuilderClient.class);
	
	public SQLQueryUnit getSQLUnitWhereClause() {
		SQLQueryUnit qunit = new SQLQueryUnit();
		String where = "";

		if (getFilter() != null) {
            
			for (String key : getFilter().getConditions().keySet()) {
				FilterCondition condition = getFilter().getConditions().get(key);
				
				if (condition.getValue() != null && condition.getOperator() != null 
						&& !condition.getValue().trim().isEmpty() && !condition.getOperator().trim().isEmpty()) {
					
					SQLQueryUnit unit = condition.getWhereClause(); 
 			        where += (where.trim().isEmpty() ? "" : " AND ")
					  	         +  unit.getClause();
					qunit.getParams().putAll(unit.getParams());
			    }
			}
		    where = (where.trim().isEmpty() ? "" : " where " + where);
			
		}
		LOG.info("Clients Where: " + where);
		LOG.info("Clients Params: " + qunit.getParams());
		qunit.setClause(where);
		return qunit;
	}
}
