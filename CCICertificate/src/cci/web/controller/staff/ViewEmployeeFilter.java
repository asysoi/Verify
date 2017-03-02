package cci.web.controller.staff;

import cci.web.controller.staff.ViewEmployee;
import cci.web.controller.staff.ViewEmployeeConfition;;

public class ViewEmployeeFilter {
	private ViewEmployeeConfition condition = new ViewEmployeeConfition();
   	private ViewEmployee viewemployee = new ViewEmployee();
   
	public ViewEmployeeConfition getCondition() {
		return condition;
	}
	public void setCondition(ViewEmployeeConfition condition) {
		this.condition = condition;
	}

	public ViewEmployee getViewemployee() {
		return viewemployee;
	}
	public void setViewemployee(ViewEmployee viewEmployee) {
		this.viewemployee = viewemployee;
	}
		
	public ViewEmployeeFilter() {
		super();		
	}

	public ViewEmployeeFilter(ViewEmployee viewemployee, ViewEmployeeConfition condition) {
		super();
		this.condition = condition;
		this.viewemployee = viewemployee;
	}
	
	@Override
	public String toString() {
		return "ViewFilter [viewcertificate=" + viewemployee + "] \n [condition="
				+ condition + "]";
	}
}
