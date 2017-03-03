package cci.web.controller.staff;

import cci.model.Department;

public class ViewEmployeeConfition {
	private String id="=";
	private String name="like";
	private String job="like";
	private String firstname="like";
	private String lastname="like";
	private String middlename="like";
	private String email="like";
	private String phone="like";
	private String active="=";
	private String id_department="=";
	private String bdayfrom="<=";
	private String bdayto=">=";
	private String id_otd="=";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getId_department() {
		return id_department;
	}
	public void setId_department(String id_department) {
		this.id_department = id_department;
	}
	public String getBdayfrom() {
		return bdayfrom;
	}
	public void setBdayfrom(String bdayfrom) {
		this.bdayfrom = bdayfrom;
	}
	public String getBdayto() {
		return bdayto;
	}
	public void setBdayto(String bdayto) {
		this.bdayto = bdayto;
	}
	public String getId_otd() {
		return id_otd;
	}
	public void setId_otd(String id_otd) {
		this.id_otd = id_otd;
	}
	
	
	
}
