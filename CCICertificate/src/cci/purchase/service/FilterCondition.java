package cci.purchase.service;

import cci.cert.service.FieldType;

public class FilterCondition {
	private String field;
	private String operator;
	private String value;
	private String dbfield;
	private FieldType type;
	private Boolean onfilter;

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

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

	public void setDbfield(String dbfield) {
		this.dbfield = dbfield;
	}

	public String getDbfield() {
		return dbfield;
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
			if (type == FieldType.DATE) {
				wherefilter = " " + dbfield + " "
						+ operator
						+ " TO_DATE('" + value + "', 'DD.MM.YY')";
			} else {
				wherefilter = " UPPER("
						+ dbfield
						+ ") "
						+ operator
						+ " "
						+ (operator.equals("like") ? "'%" + value.toUpperCase()
								+ "%'" : "'" + value.toUpperCase() + "' ");
			}
		}
		return wherefilter;
	}

	@Override
	public String toString() {
		return "FilterCondition [field=" + field + ", operator=" + operator
				+ ", value=" + value + ", onfilter=" + onfilter + "]";
	}
}
