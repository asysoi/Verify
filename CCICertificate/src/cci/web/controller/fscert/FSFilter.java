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
			sqlwhere += "certnumber LIKE '%" + getNumber() + "%'";   
		}
	
		if (getFrom() != null && !getFrom().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "dateissue >= TO_DATE(:from,'" +  DATE_FORMAT + "') ";   
		}
		
		if (getTo() != null && !getTo().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "dateissue <= TO_DATE(:to,'" +  DATE_FORMAT + "') ";   
		}
		
		return sqlwhere;
	}

	@Override
	public String getWhereEqualClause() {
		String sqlwhere= "";
		
		if (getNumber() != null && !getNumber().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "certnumber = '" + getNumber() + "'";   
		}
		
		if (getFrom() != null && !getFrom().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "dateissue >= TO_DATE(:from,'" +  DATE_FORMAT + "') ";
		}
		
		if (getTo() != null && !getTo().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "dateissue <= TO_DATE(:to,'" +  DATE_FORMAT + "') ";
		}
		
		return sqlwhere;
	}

}
