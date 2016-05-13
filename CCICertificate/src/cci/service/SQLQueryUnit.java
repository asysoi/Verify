package cci.service;

import java.util.HashMap;
import java.util.Map;

public class SQLQueryUnit {
    private String clause;
    private Map<String, Object> params;
     
	public String getClause() {
		return clause;
	}
	
	public void setClause(String clause) {
		this.clause = clause;
	}
	
	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		return params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void addParam(String dbfield, Object value) {
           getParams().put(dbfield, value); 		
	}
}
