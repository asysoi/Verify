package cci.web.controller.purchase;

public class ViewPurchaseCondition {
	private String pricefrom = ">";
	private String priceto = "<";
	private String volumefrom = ">" ;
	private String volumeto = "<" ;
	private String unit = "like";
	private String pchdatefrom = ">=";
	private String pchdateto = "<=";
	private String productproperty = "like";
	private String product = "like";
	private String department = "like";	
	private String company = "like";
	private String id_product = "=";
	private String id_otd= "=";	
	private String id_company = "=";
	
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public String getProductproperty() {
		return productproperty;
	}
	public void setProductproperty(String productproperty) {
		this.productproperty = productproperty;
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
