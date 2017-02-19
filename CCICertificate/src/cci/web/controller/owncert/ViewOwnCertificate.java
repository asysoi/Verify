package cci.web.controller.owncert;

import cci.model.owncert.OwnCertificate;

public class ViewOwnCertificate extends OwnCertificate {
	private String productname;
	private String productcode;
	private String datecertfrom;
	private String datecertto;
	private String additionallistsfrom;
	private String additionalliststo;
	private String otd_id;
	
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getDatecertfrom() {
		return datecertfrom;
	}
	public void setDatecertfrom(String datecertfrom) {
		this.datecertfrom = datecertfrom;
	}
	public String getDatecertto() {
		return datecertto;
	}
	public void setDatecertto(String datecertto) {
		this.datecertto = datecertto;
	}
	public String getAdditionallistsfrom() {
		return additionallistsfrom;
	}
	public void setAdditionallistsfrom(String additionallistsfrom) {
		this.additionallistsfrom = additionallistsfrom;
	}
	public String getAdditionalliststo() {
		return additionalliststo;
	}
	public void setAdditionalliststo(String additionalliststo) {
		this.additionalliststo = additionalliststo;
	}
	public String getOtd_id() {
		return otd_id;
	}
	public void setOtd_id(String otd_id) {
		this.otd_id = otd_id;
	}
	@Override
	public String toString() {
		return "ViewOwnCertificate [" + super.toString() + " productname=" + productname + ", productcode=" + productcode + ", datecertfrom="
				+ datecertfrom + ", datecertto=" + datecertto + ", additionallistsfrom=" + additionallistsfrom
				+ ", additionalliststo=" + additionalliststo + ", otd_id=" + otd_id + "]";
	}
	
    

}
