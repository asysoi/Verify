package cci.web.controller.fscert;

import cci.web.controller.Filter;

public class FSFilter extends Filter {

	public FSFilter(String number, String from, String to) {
		super(number, from, to);
	}

	@Override
	public String getWhereLikeClause() {
		String sqlwhere= "";
		
		if (getNumber() != null && !getNumber().isEmpty()) {
			sqlwhere += " WHERE ";
			sqlwhere += "number LIKE '%" + getNumber() + "%'";   
		}
	
		if (getFrom() != null && !getFrom().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "dateissue >= TO_DATE('" + getFrom() + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (getTo() != null && !getTo().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "dateissue <= TO_DATE('" + getTo() + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (getOtd_id() != null && !getOtd_id().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "otd_id = " + getOtd_id();   
		}
		
		
		return sqlwhere;
	}

	@Override
	public String getWhereEqualClause() {
		String sqlwhere= "";
		
		if (getNumber() != null && !getNumber().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "nomercert = '" + getNumber() + "'";   
		}
		
		if (getFrom() != null && !getFrom().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "dateissue >= TO_DATE('" + getFrom() + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (getTo() != null && !getTo().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "dateissue <= TO_DATE('" + getTo() + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (getOtd_id() != null && !getOtd_id().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "otd_id = " + getOtd_id();   
		}
		return sqlwhere;
	}

}
