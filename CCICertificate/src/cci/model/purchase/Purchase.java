package cci.model.purchase;

import java.util.Date;


public class Purchase {
	
	private long id;	
	private long id_product;
	private long id_otd;	
	private long id_company;	
	private long price;	
	private long volume;	
	private String unit;
	private Date pchdate;
	private String productproperty;
		
	public long getID() {
		return id;
	}
	public void setID(long id) {
		this.id = id;
	}
	public long getId_product() {
		return id_product;
	}
	public void setId_product(long id_product) {
		this.id_product = id_product;
	}
	public long getId_otd() {
		return id_otd;
	}
	public void setId_otd(long id_otd) {
		this.id_otd = id_otd;
	}
		
	public long getId_company() {
		return id_company;
	}
	public void setId_company(long id_company) {
		this.id_company = id_company;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getProductproperty() {
		return productproperty;
	}
	public void setProductproperty(String productproperty) {
		this.productproperty = productproperty;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getPchdate() {
		return pchdate;
	}
	public void setPchdate(Date pchdate) {
		this.pchdate = pchdate;
	}
	
	@Override
	public String toString() {
		return "Purchase [id=" + id + ", id_product=" + id_product
				+ ", id_otd=" + id_otd + ", id_company=" + id_company
				+ ", price=" + price + ", volume=" + volume + ", unit=" + unit
				+ ", pchDate=" + pchdate + ", productProperty="
				+ productproperty + "]";
	}
	
}
