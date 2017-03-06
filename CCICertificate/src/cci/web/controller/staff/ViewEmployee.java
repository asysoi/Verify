package cci.web.controller.staff;

import cci.model.Employee;

public class ViewEmployee extends Employee {
	private String departmentname;
	private String id_department;
	private String otd_name;
	private String bdayfrom;
	private String bdayto;
	private String id_otd;

	public String getOtd_name() {
		return otd_name;
	}

	public void setOtd_name(String otd_name) {
		this.otd_name = otd_name;
	}

	public String getId_otd() {
		return id_otd;
	}

	public void setId_otd(String id_otd) {
		this.id_otd = id_otd;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
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

}
