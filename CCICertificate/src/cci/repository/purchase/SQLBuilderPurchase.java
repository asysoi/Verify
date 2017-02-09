package cci.repository.purchase;

import org.apache.log4j.Logger;

import cci.repository.SQLBuilder;
import cci.repository.cert.SQLBuilderCertificate;
import cci.service.FilterCondition;
import cci.service.SQLQueryUnit;

public class SQLBuilderPurchase extends SQLBuilder {
	private static final Logger LOG = Logger.getLogger(SQLBuilderPurchase.class);
	
	public String getWhereClause() {
		String where = "";

		if (getFilter() != null) {

			for (String key : getFilter().getConditions().keySet()) {
				FilterCondition condition = getFilter().getConditions().get(key);

				if (condition.getValue() != null && condition.getOperator() != null
						&& !condition.getValue().trim().isEmpty() && !condition.getOperator().trim().isEmpty()) {

					where += (where.trim().isEmpty() ? "" : " AND ") + condition.getWhereClause().getClause();
				}
			}
			where = (where.trim().isEmpty() ? "" : " where " + where);

		}
		return where;
	}
	
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
		qunit.setClause(where);
		
		LOG.info("Where: " + where);
		LOG.info("Params: " + qunit.getParams());
		
		return qunit;
	}
	
}
