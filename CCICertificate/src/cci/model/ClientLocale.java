package cci.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

@XmlRootElement(name = "clientlocales")
@Component
@XmlType(propOrder = { "locale", "name", "city", "street" })

public class ClientLocale {
	private long id;
	private String locale;
	private String name;
	private String city;
	private String street;
	private String address;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public ClientLocale clone() {
		ClientLocale clocale = new ClientLocale();
		clocale.setId(id);
		clocale.setLocale(locale);
		clocale.setCity(city);
		clocale.setStreet(street);
		clocale.setName(name);
		clocale.setAddress(address);
		return clocale;
	}

	@Override
	public String toString() {
		return "ClientLocale [locale=" + locale + ", name=" + name + ", city=" + city + ", street=" + street
				+ ", address=" + address + "]";
	}
}
