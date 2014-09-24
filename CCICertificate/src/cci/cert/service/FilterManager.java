package cci.cert.service;

import java.util.HashMap;
import java.util.Map;

public abstract class FilterManager {
	private Map<String, FilterCondition> filters;

	public Map<String, FilterCondition> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, FilterCondition> filters) {
		this.filters = filters;
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

	private String fullsearchvalue;
	private Boolean onfilter;

	public abstract void init();

	public abstract String makeWhereClause();

	public void setConditionValue(String field, String operator, String value) {
		if (filters == null) {
			filters = new HashMap<String, FilterCondition>();
		}

		if (filters.containsKey(field)) {
			filters.get(field).setOperator(operator);
			filters.get(field).setValue(value);
		} else {
			FilterCondition filter = new FilterCondition();
			filter.setField(field);
			filter.setOperator(operator);
			filter.setValue(value);
			filters.put(field, filter);
		}
	}
}
