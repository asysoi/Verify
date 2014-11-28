package cci.web.controller.client;

public class ViewClientFilter {
 	private ViewClientCondition condition = new ViewClientCondition();
   	private ViewCompany viewcompany = new ViewCompany();
   
	public ViewClientCondition getCondition() {
		return condition;
	}
	public void setCondition(ViewClientCondition condition) {
		this.condition = condition;
	}

	public ViewCompany getViewcompany() {
		return viewcompany;
	}
	public void setViewcompany(ViewCompany viewcompany) {
		this.viewcompany = viewcompany;
	}
		
	public ViewClientFilter() {
		super();		
	}

	public ViewClientFilter(ViewCompany viewcompany, ViewClientCondition condition) {
		super();
		this.condition = condition;
		this.viewcompany = viewcompany;
	}
	
	@Override
	public String toString() {
		return "ViewFilter [viewcertificate=" + viewcompany + "] \n [condition="
				+ condition + "]";
	}

}
