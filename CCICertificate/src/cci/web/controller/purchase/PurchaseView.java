package cci.web.controller.purchase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseView {
	
	private Long id;
	private String id_product;
	private String id_otd;	
	private String id_company;	
	private long price;	
	private long volume;	
	private String unit;
	private Date pchDate;	
	private String productProperty;
	
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
	public Date getPchDate() {
		return pchDate;
	}
	
	public String getPchDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(pchDate);
	}

	public void setPchDate(Date pchDate) {
		this.pchDate = pchDate;
	}
	public String getProductProperty() {
		return productProperty;
	}
	public void setProductProperty(String productProperty) {
		this.productProperty = productProperty;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getId_product() {
		return id_product;
	}
	public void setId_product(String id_product) {
		this.id_product = id_product;
	}
	public String getId_otd() {
		return id_otd;
	}
	public void setId_otd(String id_otd) {
		this.id_otd = id_otd;
	}
	public String getId_company() {
		return id_company;
	}
	public void setId_company(String id_company) {
		this.id_company = id_company;
	}
}

