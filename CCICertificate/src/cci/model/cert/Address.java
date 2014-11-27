package cci.model.cert;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@XmlRootElement(name = "address")
@Component
public class Address {
	private String country;
	private String city;
	private String index;
	private String line;
	private String office;
	private String house;
	
    public String getCountry() {
		return country;
	}
    @XmlElement
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	@XmlElement
	public void setCity(String city) {
		this.city = city;
	}
	public String getIndex() {
		return index;
	}
	@XmlElement
	public void setIndex(String index) {
		this.index = index;
	}
	public String getLine() {
		return line;
	}
	@XmlElement
	public void setLine(String line) {
		this.line = line;
	}
	public String getOffice() {
		return office;
	}
	@XmlElement
	public void setOffice(String office) {
		this.office = office;
	}
	public String getHouse() {
		return house;
	}
	@XmlElement
	public void setHouse(String house) {
		this.house = house;
	}
	
}
