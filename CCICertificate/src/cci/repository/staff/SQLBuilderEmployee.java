package cci.repository.staff;

import org.apache.log4j.Logger;

import cci.repository.SQLBuilder;
import cci.repository.client.SQLBuilderClient;
import cci.service.FilterCondition;
import cci.service.SQLQueryUnit;

public class SQLBuilderEmployee extends SQLBuilder {
	private static final Logger LOG = Logger.getLogger(SQLBuilderEmployee.class);
	
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
		LOG.info("Employees Where: " + where);
		LOG.info("Employees Params: " + qunit.getParams());
		qunit.setClause(where);
		return qunit;
	}

}
