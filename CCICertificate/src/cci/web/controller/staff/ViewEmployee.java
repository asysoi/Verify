package cci.web.controller.staff;

import cci.model.Employee;

public class ViewEmployee extends Employee {
	private String departmentname;
	private String otd_name;
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

}
