package cci.web.controller.client;

import cci.model.Client;

public class ViewClient extends Client {
	private String country;
	private String bcountry;
		
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
	@Override
	public String toString() {
		return "ViewClient [" + super.toString() + "country=" + country + ", bcountry=" + bcountry + "]";
	}
	
	
}
