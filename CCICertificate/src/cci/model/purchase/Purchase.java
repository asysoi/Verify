package cci.model.purchase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class Purchase {
	
	private Long id;	
	private Long id_product;
	private Long id_otd;	
	private Long id_company;	
	private Long price;	
	private Long volume;	
	private String unit;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date pchdate;
	private String productproperty;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_product() {
		return id_product;
	}

	public void setId_product(Long id_product) {
		this.id_product = id_product;
	}

	public Long getId_otd() {
		return id_otd;
	}

	public void setId_otd(Long id_otd) {
		this.id_otd = id_otd;
	}

	public Long getId_company() {
		return id_company;
	}

	public void setId_company(Long id_company) {
		this.id_company = id_company;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getPchdate() {
		return pchdate;
	}

	public void setPchdate(Date pchdate) {
		this.pchdate = pchdate;
	}

	public String getProductproperty() {
		return productproperty;
	}

	public void setProductproperty(String productproperty) {
		this.productproperty = productproperty;
	}

	public String getPchdatestring() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return pchdate == null ? "" : df.format(pchdate);
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
