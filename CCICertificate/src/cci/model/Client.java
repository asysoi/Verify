﻿package cci.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

@XmlRootElement(name = "client")
@Component
@XmlType(propOrder = {"name", "codecountry" , "cindex", "city", "line", "office", "building", 
		"work_phone", "cell_phone", "email", "unp", "okpo", "account", "bname", "bindex", "bcodecountry"
		,"bcity", "bline", "boffice", "bbuilding", "bwork_phone", "bcell_phone", "bemail", "bunp"})

public class Client {
	private long id;		
	private String name;
	private String codecountry;
	private String cindex;
	private String city;		
	private String line;		
	private String office;
	private String building;
	private String work_phone;	
	private String cell_phone;
	private String email;
	private String unp;
	private String okpo;
	private String account;
	private String bname;
	private String bindex;
	private String bcodecountry;
	private String bcity;		
	private String bline;		
	private String boffice;
	private String bbuilding;
	private String bwork_phone;	
	private String bcell_phone;
	private String bemail;
	private String bunp;
	private String address;
	
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
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
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
	public String getWork_phone() {
		return work_phone;
	}
	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}
	public String getCell_phone() {
		return cell_phone;
	}
	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
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
	public String getBline() {
		return bline;
	}
	public void setBline(String bline) {
		this.bline = bline;
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
	public String getBwork_phone() {
		return bwork_phone;
	}
	public void setBwork_phone(String bwork_phone) {
		this.bwork_phone = bwork_phone;
	}
	public String getBcell_phone() {
		return bcell_phone;
	}
	public void setBcell_phone(String bcell_phone) {
		this.bcell_phone = bcell_phone;
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
	
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", codecountry=" + codecountry + ", cindex=" + cindex + ", city="
				+ city + ", line=" + line + ", office=" + office + ", building=" + building + ", work_phone="
				+ work_phone + ", cell_phone=" + cell_phone + ", email=" + email + ", unp=" + unp + ", okpo=" + okpo
				+ ", account=" + account + ", bname=" + bname + ", bindex=" + bindex + ", bcodecountry=" + bcodecountry
				+ ", bcity=" + bcity + ", bline=" + bline + ", boffice=" + boffice + ", bbuilding=" + bbuilding
				+ ", bwork_phone=" + bwork_phone + ", bcell_phone=" + bcell_phone + ", bemail=" + bemail + ", bunp="
				+ bunp + "]";
	}
	
	
}
