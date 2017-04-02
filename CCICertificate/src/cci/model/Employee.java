package cci.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

//------------------------------------------
// Base class for branch employee         
//------------------------------------------
@XmlRootElement(name = "employee")
@Component
@XmlType(propOrder = {"name", "job", "runame", "rujob", "lastname",
		"firstname", "middlename", "email", "phone", "bday", "department", "employeelocales"})
public class Employee {
	private long id;
	private String name;
	private String job;
	private Department department;
	private String firstname;
	private String lastname;
	private String middlename;
	private String email;
	private String phone;
	private String bday;
	private char active;
	private List<EmployeeLocale> locales;
    private long version;
	private int locked;

	public void init(Employee emp) {
		id = emp.getId();
		name = emp.getName();
		job = emp.getJob();
		department = emp.getDepartment();
		firstname = emp.getFirstname();
		lastname = emp.getLastname();
		middlename = emp.getMiddlename();
		email = emp.getEmail();
		phone = emp.getPhone();
		bday = emp.getBday();
		active = emp.getActive();	
		this.locales = cloneLocales(emp.getLocales()); 
	}
	
	public EmployeeLocale getLocale(String locale) {
		EmployeeLocale rlocale = null;
		
		if (getLocales() != null) {
			for (EmployeeLocale item : locales) {
				if (locale.equals(item.getLocale())) {
					rlocale = item;
					break;
				}
			}
		} 
		return rlocale;
	}

	@XmlTransient	
	public String getEnname() {
	   EmployeeLocale locale = getLocale("EN");
	   return locale != null ? locale.getName() : "";	
	}
	
    public void setEnname(String enname) {
    	EmployeeLocale locale = getLocale("EN");
    	
    	if (locale == null) {
    		locale = new EmployeeLocale();
    		locale.setLocale("EN");
    		locales.add(locale);
    	}
    	
    	locale.setName(enname);
	}

	@XmlTransient	
	public String getEnjob() {
	   EmployeeLocale locale = getLocale("EN");
	   return locale != null ? locale.getJob() : "";	
	}
	
    public void setEnjob(String enjob) {
    	EmployeeLocale locale = getLocale("EN");
    	
    	if (locale == null) {
    		locale = new EmployeeLocale();
    		locale.setLocale("EN");
    		locales.add(locale);
    	}
    	
    	locale.setJob(enjob);
	}
    
    
    
	private List<EmployeeLocale> cloneLocales(List<EmployeeLocale> locales) {
		List<EmployeeLocale> clocales = null;
		if (locales != null) {
			clocales = new ArrayList<EmployeeLocale>();
			
			for (EmployeeLocale element : locales) {
				clocales.add(element.clone());
			}
		}	
		return clocales;
	}

	@XmlElementWrapper(name = "employeelocales")
	@XmlElement(name = "locale")
	public List<EmployeeLocale> getLocales() {
		if (locales == null) {
			locales = new ArrayList<EmployeeLocale>();
		}
		return locales;
	}

	public void setLocales(List<EmployeeLocale> locales) {
		this.locales = locales;
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	public Department getDepartment() {
		if (department == null) {
			department = new Department();
		}
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
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
		return "Employee [id=" + id + ", name=" + name + ", job=" + job + ", department=" + department + ", firstname="
				+ firstname + ", lastname=" + lastname + ", middlename=" + middlename + ", email=" + email + ", phone="
				+ phone + ", bday=" + bday + ", active=" + active + ", locales=" + locales + "]";
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}


}
