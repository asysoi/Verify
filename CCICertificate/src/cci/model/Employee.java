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
@XmlType(propOrder = {"name", "job"})

public class Employee {
	private long id;
	private String name;
	private String job;
	
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
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", job=" + job + "]";
	}
	
}
