package cci.web.controller.cert;

public class Filter {

	public final String DATE_FORMAT = "DD.MM.YY";

	private String number = null;
	private String blanknumber = null;
	private String from = null;
	private String to = null;
	private String otd_id = null;

	public Filter(String number, String blanknumber, String from, String to) {
		super();
		this.number = number;
		this.blanknumber = blanknumber;
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBlanknumber() {
		return blanknumber;
	}

	public void setBlanknumber(String blanknumber) {
		this.blanknumber = blanknumber;
	}
	
	public String getOtd_id() {
		return otd_id;
	}

	public void setOtd_id(String otd_id) {
		this.otd_id = otd_id;
	}

	public String getWhereLikeClause() {
		
		String sqlwhere= "";
		
		if (number != null && !number.isEmpty()) {
			sqlwhere += " WHERE ";
			sqlwhere += "nomercert LIKE '%" + number + "%'";   
		}
						
		if (blanknumber != null && !blanknumber.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "nblanka LIKE '%" + blanknumber + "%'";   
		}
		
		if (from != null && !from.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "issuedate >= TO_DATE('" + from + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (to != null && !to.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "issuedate <= TO_DATE('" + to + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (otd_id != null && !otd_id.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "otd_id = " + otd_id;   
		}
		
		
		return sqlwhere;
	}
	
    public String getWhereEqualClause() {
		
		String sqlwhere= "";
		
		if (number != null && !number.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "nomercert = '" + number + "'";   
		}
		
		if (blanknumber != null && !blanknumber.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "nblanka = '" + blanknumber + "'";   
		}
		
		if (from != null && !from.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "issudate >= TO_DATE('" + from + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (to != null && !to.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "issuedate <= TO_DATE('" + to + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (otd_id != null && !otd_id.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "otd_id = " + otd_id;   
		}
		
		return sqlwhere;
	}

	@Override
	public String toString() {
		return "[number=" + nullypempty(number) + ", blanknumber=" + nullypempty(blanknumber)
				+ ", from=" + nullypempty(from) + ", to=" + nullypempty(to) + "]";
	}

	private String nullypempty(String var) {
		return (var == null) ? "" : var ;
	}
	
	
}
