package cci.web.controller;

public abstract class Filter {

	public static final String DATE_FORMAT = "DD.MM.YY";

	private String number = null;
	private String from = null;
	private String to = null;
	private String otd_id = null;

	public abstract String getWhereLikeClause();
    public abstract String getWhereEqualClause();

	
	public Filter(String number, String from, String to) {
		super();
		this.number = number;
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
	
	public String getOtd_id() {
		return otd_id;
	}

	public void setOtd_id(String otd_id) {
		this.otd_id = otd_id;
	}


	@Override
	public String toString() {
		return "[number=" + nullypempty(number) + 
			   ", from=" + nullypempty(from) + 
			   ", to=" + nullypempty(to) + "]";
	}

	public String nullypempty(String var) {
		return (var == null) ? "" : var ;
	}
	
}
