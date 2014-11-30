package cci.repository.client;

import cci.repository.SQLBuilder;
import cci.service.FilterCondition;

public class SQLBuilderClient extends SQLBuilder {
	
	public String getWhereClause() {
		String where = "";

		if (getFilter() != null) {
            
			for (String key : getFilter().getConditions().keySet()) {
				FilterCondition condition =getFilter().getConditions().get(key);
				
				if (condition.getValue() != null && condition.getOperator() != null && !condition.getValue().trim().isEmpty() && !condition.getOperator().trim().isEmpty()) {
					
					where += (where.trim().isEmpty() ? "" : " AND ")
					  	         +  condition.getWhereClause();
				}
			}
		    where = (where.trim().isEmpty() ? "" : " where " + where);
			
		}
		return where;
	}


}
