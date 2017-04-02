package cci.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

@XmlRootElement(name = "employeelocale")
@Component
@XmlType(propOrder = { "locale", "name", "job"})
public class EmployeeLocale {
	private long id;
	private String locale;
	private String name;
	private String job;
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	public EmployeeLocale clone() {
		EmployeeLocale clocale = new EmployeeLocale();
		clocale.setId(id);
		clocale.setLocale(locale);
		clocale.setName(name);
		clocale.setJob(job);
		return clocale;
	}
	
	@Override
	public String toString() {
		return "EmployeeLocale [id=" + id + ", locale=" + locale + ", name=" + name + ", job=" + job + "]";
	}
	
	
	
}
