package cci.web.controller.purchase;

import cci.model.purchase.Purchase;

public class ViewPurchase extends Purchase {
	
	private String product;
	private String department;	
	private String company;	

	
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

