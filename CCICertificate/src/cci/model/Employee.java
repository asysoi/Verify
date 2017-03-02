package cci.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

//------------------------------------------
// Base class for branch employee         
//------------------------------------------
@XmlRootElement(name = "employee")
@Component
@XmlType(propOrder = {"name", "job", "runame", "rujob", "enname","enjob","lastname",
		"firstname", "middlename", "email", "phone", "bday", "department"})
public class Employee {
	private long id;
	private String name;
	private String job;
	private Department department;
	private String runame;
	private String rujob;
	private String enname;
	private String enjob;
	private String firstname;
	private String lastname;
	private String middlename;
	private String email;
	private String phone;
	private String bday;
	private char active;
	
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getRuname() {
		return runame;
	}
	public void setRuname(String runame) {
		this.runame = runame;
	}
	public String getRujob() {
		return rujob;
	}
	public void setRujob(String rujob) {
		this.rujob = rujob;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public String getEnjob() {
		return enjob;
	}
	public void setEnjob(String enjob) {
		this.enjob = enjob;
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
	public String getBday() {
		return bday;
	}
	public void setBday(String bday) {
		this.bday = bday;
	}
	public char getActive() {
		return active;
	}
	public void setActive(char active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", job=" + job + ", department=" + department + ", runame="
				+ runame + ", rujob=" + rujob + ", enname=" + enname + ", enjob=" + enjob + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", middlename=" + middlename + ", email=" + email + ", phone=" + phone
				+ ", bday=" + bday + "]";
	}
	
	
	
}
