package cci.web.controller.purchase;

import java.util.Date;

public class ViewPurchaseCondition {
	private String pricefrom = ">";
	private String pricefto = "<";
	private String volumefrom = ">" ;
	private String volumeto = "<" ;
	private String unit = "like";
	private String pchDatefrom = ">";
	private String pchDateto = "<";
	private String productProperty = "like";
	private String product = "like";
	private String department = "like";	
	private String company = "like";
	public String getPricefrom() {
		return pricefrom;
	}
	public void setPricefrom(String pricefrom) {
		this.pricefrom = pricefrom;
	}
	public String getPricefto() {
		return pricefto;
	}
	public void setPricefto(String pricefto) {
		this.pricefto = pricefto;
	}
	public String getVolumefrom() {
		return volumefrom;
	}
	public void setVolumefrom(String volumefrom) {
		this.volumefrom = volumefrom;
	}
	public String getVolumeto() {
		return volumeto;
	}
	public void setVolumeto(String volumeto) {
		this.volumeto = volumeto;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPchDatefrom() {
		return pchDatefrom;
	}
	public void setPchDatefrom(String pchDatefrom) {
		this.pchDatefrom = pchDatefrom;
	}
	public String getPchDateto() {
		return pchDateto;
	}
	public void setPchDateto(String pchDateto) {
		this.pchDateto = pchDateto;
	}
	public String getProductProperty() {
		return productProperty;
	}
	public void setProductProperty(String productProperty) {
		this.productProperty = productProperty;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}	

}
