package cci.web.controller.purchase;

import cci.model.purchase.Purchase;

public class ViewPurchase extends Purchase {
	
	private String product;
	private String department;	
	private String company;	
	private String pricefrom;
	private String priceto;
	private String volumefrom;
	private String volumeto;
	private String pchdatefrom;
	private String pchdateto;

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
	
	public String getPricefrom() {
		return pricefrom;
	}
	public void setPricefrom(String pricefrom) {
		this.pricefrom = pricefrom;
	}
	public String getPriceto() {
		return priceto;
	}
	public void setPriceto(String priceto) {
		this.priceto = priceto;
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
	public String getPchdatefrom() {
		return pchdatefrom;
	}
	public void setPchdatefrom(String pchdatefrom) {
		this.pchdatefrom = pchdatefrom;
	}
	public String getPchdateto() {
		return pchdateto;
	}
	public void setPchdateto(String pchdateto) {
		this.pchdateto = pchdateto;
	}
	@Override
	public String toString() {
		return "ViewPurchase [product=" + product + ", department="
				+ department + ", company=" + company + ", pricefrom="
				+ pricefrom + ", priceto=" + priceto + ", volumefrom="
				+ volumefrom + ", volumeto=" + volumeto + ", pchdatefrom="
				+ pchdatefrom + ", pchdateto=" + pchdateto
				+ ", Id_product=" + getId_product() + ", Id_otd="
				+ getId_otd() + ", Id_company=" + getId_company()
				+ ", Unit=" + getUnit() + ", Productproperty="
				+ getProductproperty() + "]";
	}
	
	

}

