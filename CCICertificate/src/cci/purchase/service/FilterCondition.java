package cci.purchase.service;

public class FilterCondition {
	private String field;
	private String operator;
	private String value;
	private Boolean onfilter;

	public Boolean getOnfilter() {
		return onfilter;
	}

	public void setOnfilter(Boolean onfilter) {
		this.onfilter = onfilter;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String makeWhereFilter() {
		String wherefilter = "";

		if (onfilter != null && onfilter && field != null && operator != null
				&& value != null) {
			wherefilter = " where UPPER("
					+ field
					+ ") "
					+ operator
					+ " "
					+ (operator.equals("like") ? "'%" + value.toUpperCase()
							+ "%'" : "'" + value.toUpperCase() + "' ");
		}
		return wherefilter;
	}

	public String getWhereClause() {
		String wherefilter = "";

		if (field != null && operator != null && value != null) {
			wherefilter = " UPPER("
					+ field
					+ ") "
					+ operator
					+ " "
					+ (operator.equals("like") ? "'%" + value.toUpperCase()
							+ "%'" : "'" + value.toUpperCase() + "' ");
		}
		return wherefilter;
	}

	@Override
	public String toString() {
		return "FilterCondition [field=" + field + ", operator=" + operator
				+ ", value=" + value + ", onfilter=" + onfilter + "]";
	}

}
