package cci.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cci.model.fscert.FSProduct;
import cci.web.controller.client.ClientController;

@XmlRootElement(name = "client")
@Component
@XmlType(propOrder = {"name", "codecountry" , "cindex", "city", "street", "office", "building", 
		"phone", "cell", "fax", "email", "unp", "okpo", "account", "bname", "bindex", "bcodecountry"
		,"bcity", "bstreet", "boffice", "bbuilding", "bphone", "bcell", "bemail", "bunp"})

public class Client {
	private static final Logger LOG = Logger.getLogger(Client.class);
	
	private long id;		
	private String name;
	private String codecountry;
	private String cindex;
	private String city;		
	private String street;		
	private String office;
	private String building;
	private String phone;	
	private String cell;
	private String fax;
	private String email;
	private String unp;
	private String okpo;
	private String account;
	private String bname;
	private String bindex;
	private String bcodecountry;
	private String bcity;		
	private String bstreet;		
	private String boffice;
	private String bbuilding;
	private String bphone;	
	private String bcell;
	private String bemail;
	private String bunp;
	private String address;
	private List<ClientLocale> locales;
    private long version;
	private int locked;
	
	public void init(Client client) {
		this.id = client.getId();
		this.name = client.getName();
		this.codecountry = client.getCodecountry();
		this.cindex = client.getCindex();
		this.city = client.getCity();
		this.street = client.getStreet();
		this.office = client.getOffice();
		this.building = client.getBuilding();
		this.phone = client.getPhone();
		this.cell = client.getCell();
		this.fax = client.getFax();
		this.email = client.getEmail();
		this.unp = client.getUnp();
		this.okpo = client.getOkpo();
		this.account = client.getAccount();
		this.bname = client.getBname();
		this.bindex = client.getBindex();
		this.bcodecountry = client.getBcodecountry();
		this.bcity = client.getBcity();
		this.bstreet = client.getBstreet();
		this.boffice = client.getBoffice();
		this.bbuilding = client.getBbuilding();
		this.bphone = client.getBphone();
		this.bcell = client.getBcell();
		this.bemail = client.getBemail();
		this.bunp = client.getBunp();
		this.address = client.getAddress();
		this.version = client.getVersion();
		this.locked = client.getLocked();
		this.locales = cloneLocales(client.getLocales()); 
	}
	
	private List<ClientLocale> cloneLocales(List<ClientLocale> locales) {
		List<ClientLocale> clocales = null;
		if (locales != null) {
			clocales = new ArrayList<ClientLocale>();
			
			for (ClientLocale element : locales) {
				clocales.add(element.clone());
			}
		}	
		return clocales;
	}

	@XmlTransient	
	public String getEnname() {
	   ClientLocale locale = getLocale("EN");
	   return locale != null ? locale.getName() : "";	
	}
	
    public void setEnname(String enname) {
    	ClientLocale locale = getLocale("EN");
    	LOG.info("locale = " + locale);
    	
    	if (locale == null) {
    		locale = new ClientLocale();
    		locale.setLocale("EN");
    		locales.add(locale);
    	}
    	
    	locale.setName(enname);
	}
    
	@XmlTransient	
	public String getEnstreet() {
	   ClientLocale locale = getLocale("EN");
	   return locale != null ? locale.getStreet() : "";	
	}
	
    public void setEnstreet(String enstreet) {
    	ClientLocale locale = getLocale("EN");
    	
    	if (locale == null) {
    		locale = new ClientLocale();
    		locale.setLocale("EN");
    		locales.add(locale);
    	}
    	
    	locale.setStreet(enstreet);
	}

	@XmlTransient	
	public String getEncity() {
	  ClientLocale locale = getLocale("EN");
	  return locale != null ? locale.getCity() : "";	
	}
	
    public void setEncity(String encity) {
    	ClientLocale locale = getLocale("EN");
    	
    	if (locale == null) {
    		locale = new ClientLocale();
    		locale.setLocale("EN");
    		locales.add(locale);
    	}
    	
    	locale.setCity(encity);
	}
	
	@XmlTransient	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@XmlTransient
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getCindex() {
		return cindex;
	}
	public void setCindex(String cindex) {
		this.cindex = cindex;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUnp() {
		return unp;
	}
	public void setUnp(String unp) {
		this.unp = unp;
	}
	public String getOkpo() {
		return okpo;
	}
	public void setOkpo(String okpo) {
		this.okpo = okpo;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getBcity() {
		return bcity;
	}
	public void setBcity(String bcity) {
		this.bcity = bcity;
	}
	public String getBstreet() {
		return bstreet;
	}
	public void setBstreet(String bstreet) {
		this.bstreet = bstreet;
	}
	public String getBindex() {
		return bindex;
	}
	public void setBindex(String bindex) {
		this.bindex = bindex;
	}
	public String getBoffice() {
		return boffice;
	}
	public void setBoffice(String boffice) {
		this.boffice = boffice;
	}
	public String getBbuilding() {
		return bbuilding;
	}
	public void setBbuilding(String bbuilding) {
		this.bbuilding = bbuilding;
	}
	public String getBphone() {
		return bphone;
	}
	public void setBphone(String bphone) {
		this.bphone = bphone;
	}
	public String getBcell() {
		return bcell;
	}
	public void setBcell_phone(String bcell) {
		this.bcell = bcell;
	}
	public String getBemail() {
		return bemail;
	}
	public void setBemail(String bemail) {
		this.bemail = bemail;
	}
	public String getBunp() {
		return bunp;
	}
	public void setBunp(String bunp) {
		this.bunp = bunp;
	}
	public String getCodecountry() {
		return codecountry;
	}
	public void setCodecountry(String codecountry) {
		this.codecountry = codecountry;
	}
	public String getBcodecountry() {
		return bcodecountry;
	}
	public void setBcodecountry(String bcodecountry) {
		this.bcodecountry = bcodecountry;
	}
	
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@XmlTransient
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<ClientLocale> getLocales() {
		if (locales == null) {
			locales = new ArrayList<ClientLocale>();
		}
		return locales;
	}

	public void setLocales(List<ClientLocale> locales) {
		this.locales = locales;
	}
	
	@XmlTransient
	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", codecountry=" + codecountry + ", cindex=" + cindex + ", city="
				+ city + ", street=" + street + ", office=" + office + ", building=" + building + ", phone=" + phone
				+ ", cell=" + cell + ", fax=" + fax + ", email=" + email + ", unp=" + unp + ", okpo=" + okpo
				+ ", account=" + account + ", bname=" + bname + ", bindex=" + bindex + ", bcodecountry=" + bcodecountry
				+ ", bcity=" + bcity + ", bstreet=" + bstreet + ", boffice=" + boffice + ", bbuilding=" + bbuilding
				+ ", bwork_phone=" + bphone + ", bcell_phone=" + bcell + ", bemail=" + bemail + ", bunp="
				+ bunp + ", address=" + address + ", locales=" + locales + ", version=" + version + ", locked=" + locked
				+ "]";
	}

	public ClientLocale getLocale(String locale) {
		ClientLocale rlocale = null;
		
		if (getLocales() != null) {
			for (ClientLocale item : locales) {
				if (locale.equals(item.getLocale())) {
					rlocale = item;
					break;
				}
			}
		} 
		return rlocale;
	}
}
