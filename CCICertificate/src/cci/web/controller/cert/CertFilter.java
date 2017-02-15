package cci.web.controller.cert;

public class CertFilter extends cci.web.controller.Filter {

	private String blanknumber = null;

	public CertFilter(String number, String blanknumber, String from, String to) {
		super(number, from, to);
		this.blanknumber = blanknumber;
	}

	public String getBlanknumber() {
		return blanknumber;
	}

	public void setBlanknumber(String blanknumber) {
		this.blanknumber = blanknumber;
	}
	

	public String getWhereLikeClause() {
		
		String sqlwhere= "";
		
		if (getNumber() != null && !getNumber().isEmpty()) {
			sqlwhere += " WHERE ";
			sqlwhere += "nomercert LIKE '%" + getNumber() + "%'";   
		}
						
		if (blanknumber != null && !blanknumber.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "nblanka LIKE '%" + blanknumber + "%'";   
		}
		
		if (getFrom() != null && !getFrom().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "issuedate >= TO_DATE('" + getFrom() + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (getTo() != null && !getTo().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "issuedate <= TO_DATE('" + getTo() + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (getOtd_id() != null && !getOtd_id().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "otd_id = " + getOtd_id();   
		}
		
		
		return sqlwhere;
	}
	
    public String getWhereEqualClause() {
		
		String sqlwhere= "";
		
		if (getNumber() != null && !getNumber().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "nomercert = '" + getNumber() + "'";   
		}
		
		if (blanknumber != null && !blanknumber.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "nblanka = '" + blanknumber + "'";   
		}
		
		if (getFrom() != null && !getFrom().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "issudate >= TO_DATE('" + getFrom() + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (getTo() != null && !getTo().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "issuedate <= TO_DATE('" + getTo() + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (getOtd_id() != null && !getOtd_id().isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "otd_id = " + getOtd_id();   
		}
		
		return sqlwhere;
	}

	@Override
	public String toString() {
		return "[number=" + nullypempty(getNumber()) + ", blanknumber=" + nullypempty(blanknumber)
				+ ", from=" + nullypempty(getFrom()) + ", to=" + nullypempty(getTo()) + "]";
	}
	
}
