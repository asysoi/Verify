package cci.web.controller.client;

import cci.model.Client;

public class ViewClient extends Client {
	private String country;
	private String bcountry;
	private String address;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBcountry() {
		return bcountry;
	}
	public void setBcountry(String bcountry) {
		this.bcountry = bcountry;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
