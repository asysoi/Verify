package cci.cert.service;

import java.util.HashMap;
import java.util.Map;

import cci.purchase.service.FilterCondition;

public abstract class Filter {
	private Map<String, FilterCondition> conditions;
	private String fullsearchvalue;
	private Boolean onfilter;


	public Map<String, FilterCondition> getConditions() {
		return conditions;
	}

	public void setFilters(Map<String, FilterCondition> conditions) {
		this.conditions = conditions;
	}

	public String getFullsearchvalue() {
		return fullsearchvalue;
	}

	public void setFullsearchvalue(String fullsearchvalue) {
		this.fullsearchvalue = fullsearchvalue;
	}

	public Boolean getOnfilter() {
		return onfilter;
	}

	public void setOnfilter(Boolean onfilter) {
		this.onfilter = onfilter;
	}

	public void init(String[] fields, String[] dbfields, FieldType[] types) {
		
        for(int i = 0; i < fields.length; i++) {
      	  this.setConditionValue(fields[i], dbfields[i], "", "", types[i]);
        }
	}

	public void setConditionValue(String field, String dbfield, String operator, String value, FieldType type) {
		if (conditions == null) {
			conditions = new HashMap<String, FilterCondition>();
		}
        String upkey = field.toUpperCase();
        
		if (conditions.containsKey(upkey)) {
			conditions.get(upkey).setOperator(operator);
			conditions.get(upkey).setValue(value);
		} else {
			FilterCondition filter = new FilterCondition();
			filter.setField(field);
			filter.setOperator(operator);
			filter.setValue(value);
			filter.setDbfield(dbfield);
			filter.setType(type);
			conditions.put(upkey, filter);
		}
	}

	@Override
	public String toString() {
		String ret = "";
		
		for (String field : getConditions().keySet()) {
            ret += "\n";  
            ret += getConditions().get(field).toString();	  
        }
		return ret;
	}

	
	
	
}
