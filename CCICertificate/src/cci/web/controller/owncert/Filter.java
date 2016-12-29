package cci.web.controller.owncert;

public class Filter {

	public final String DATE_FORMAT = "%d.%m.%Y";

	private String number = null;
	private String blanknumber = null;
	private String from = null;
	private String to = null;

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

	public String getWhereLikeClause() {
		
		String sqlwhere= "";
		
		if (number != null && !number.isEmpty()) {
			sqlwhere += " WHERE ";
			sqlwhere += "number LIKE '%" + number + "%'";   
		}
		
		if (blanknumber != null && !blanknumber.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "blanknumber LIKE '%" + blanknumber + "%'";   
		}
		
		if (from != null && !from.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "datecert > STR_TO_DATE('" + from + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (to != null && !to.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "datecert < STR_TO_DATE('" + to + "','" +  DATE_FORMAT + "') ";   
		}
		
		return sqlwhere;
	}
	
    public String getWhereEqualClause() {
		
		String sqlwhere= "";
		
		if (number != null && !number.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "number = '" + number + "'";   
		}
		
		if (blanknumber != null && !blanknumber.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "blanknumber = '" + blanknumber + "'";   
		}
		
		if (from != null && !from.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "datecert > STR_TO_DATE('" + from + "','" +  DATE_FORMAT + "') ";   
		}
		
		if (to != null && !to.isEmpty()) {
			if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
			sqlwhere += "datecert < STR_TO_DATE('" + to + "','" +  DATE_FORMAT + "') ";   
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
