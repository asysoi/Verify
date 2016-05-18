package cci.service;


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
	
	
	public SQLQueryUnit getWhereClause() {
		SQLQueryUnit sunit = new SQLQueryUnit();
		String wherefilter = "";

		if (field != null && operator != null && value != null) {
			if (type == FieldType.DATE) {
				wherefilter = " " + dbfield + " "
						+ operator
						+ " TO_DATE( :" + field + ", 'DD.MM.YY')";
				sunit.addParam(field, value);
			} else if (type == FieldType.NUMBER) {
				wherefilter = " " + dbfield + " "
						+ operator
						+ " :" + field;
				sunit.addParam(field, Integer.valueOf(value));
			} else if (type == FieldType.TEXT) {
				wherefilter = " contains(" + dbfield + ",  :" + field + ") > 0 ";
				sunit.addParam(field, value );	
			} else {
				wherefilter = " UPPER("
						+ dbfield
						+ ") "
						+ operator
						+ " :" + field;
				sunit.addParam(field, (operator.equals("like") ? "%" + value.toUpperCase()
								+ "%" : value.toUpperCase()));
			}
		}
        sunit.setClause(wherefilter);		
		return sunit;
	}

	@Override
	public String toString() {
		return "FilterCondition [field=" + field + ", operator=" + operator
				+ ", value=" + value + ", onfilter=" + onfilter + "]";
	}
}
