package cci.service;

import org.apache.log4j.Logger;

public class FilterCondition {
	private String dateconvertfunction = "TO_DATE";
	private String dateconvertformat= "'DD.MM.YY'";
	private String field;
	private String operator;
	private String value;
	private String dbfield;
	private FieldType type;
	private Boolean onfilter;
	private boolean isPREPARED = false;
	
	private static final Logger LOG = Logger.getLogger(FilterCondition.class);

	public String getDateconvertfunction() {
		return dateconvertfunction;
	}

	public void setDateconvertfunction(String dateconvertfunction) {
		this.dateconvertfunction = dateconvertfunction;
	}

	public String getDateconvertformat() {
		return dateconvertformat;
	}

	public void setDateconvertformat(String dateconvertformat) {
		this.dateconvertformat = dateconvertformat;
	}
	
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

			if (isPREPARED) {
				if (type == FieldType.DATE) {
					wherefilter = " " + dbfield + " " + operator
							+ dateconvertfunction + "( :" + field + "," +  dateconvertformat + ")";
					sunit.addParam(field, value);

				} else if (type == FieldType.NUMBER) {
					wherefilter = " " + dbfield + " " + operator + " :" + field;
					sunit.addParam(field, Integer.valueOf(value));
				} else if (type == FieldType.TEXT) {
					wherefilter = " contains(" + dbfield + ",  :" + field + ") > 0 ";
					sunit.addParam(field, placeHolderReplace(value, '*', '%', true));
				} else if (type == FieldType.TEXTSTRING) {
					wherefilter = " contains(" + dbfield + ",  :" + field + ") > 0 ";
					sunit.addParam(field, placeHolderReplace(value, '*', '%', false));
				} else if (type == FieldType.STRING){
					wherefilter = " UPPER(" + dbfield + ") " + operator + " :"
							+ field;
					sunit.addParam(field, (operator.equals("like") ? "%" + value.toUpperCase() + "%" : value.toUpperCase()));
					
				} else {
					wherefilter = " UPPER(" + dbfield + ") " + operator + " :"
							+ field;
					sunit.addParam(field, (operator.equals("like") ? placeHolderReplace(value.toUpperCase(), '*', '%', true) : value.toUpperCase()));
				}
				
			} else {

				if (type == FieldType.DATE) {
					wherefilter = " " + dbfield + " " + operator + dateconvertfunction +"('"
							+ value + "'," + dateconvertformat + ")";
				} else if (type == FieldType.NUMBER) {
					wherefilter = " " + dbfield + " " + operator + " " + value
							+ " ";
				} else if (type == FieldType.TEXT) {
					wherefilter = " contains(" + dbfield + ", '" + placeHolderReplace(value, '*', '%', true) + "') > 0 ";
				} else if (type == FieldType.TEXTSTRING) {
					wherefilter = " contains(" + dbfield + ", '" + placeHolderReplace(value, '*', '%', true) + "') > 0 ";
				} else if (type == FieldType.STRING){
					wherefilter = " UPPER("
							+ dbfield
							+ ") "
							+ operator
							+ " "
							+ (operator.equals("like") ? "'%" + value.toUpperCase() + "%' " : 
														 "'" + value.toUpperCase() + "' ");
				} else {
					wherefilter = " UPPER("
							+ dbfield
							+ ") "
							+ operator
							+ " "
							+ (operator.equals("like") ? "'" + placeHolderReplace(value.toUpperCase(), '*', '%', true) + "'" : 
														 "'" + value.toUpperCase() + "' ");
				}

			}
		}
		sunit.setClause(wherefilter);
		return sunit;
	}

	// ----------------------------------------------------------
	// Replaces palceholders into target string  
	//
	// ----------------------------------------------------------
	private Object placeHolderReplace(String value, char oldChar,
			char newChar,  boolean allItems) {
		String ret = null;
		
        if (allItems) {
        	ret = value.replace(oldChar, newChar);
        } else {
        	char[] dst = new char[value.length()];
        	value.getChars(0, value.length(), dst, 0);
        	
        	int i = 0;
        	while (dst[i] == oldChar && i < value.length()) {
        		dst[i++] = newChar;
        	}
        	
        	i = value.length() -1;
        	while (dst[i] == oldChar && i < value.length()) {
        		dst[i--] = newChar;
        	}
        	
        	ret = dst.toString();
        }
        
        LOG.info("Value : " + value + " -> " + ret );
		return ret;
	}

	@Override
	public String toString() {
		return "FilterCondition [field=" + field + ", dbfield = " + dbfield + ", operator=" + operator
				+ ", value=" + value + ", onfilter=" + onfilter + "]";
	}
}
