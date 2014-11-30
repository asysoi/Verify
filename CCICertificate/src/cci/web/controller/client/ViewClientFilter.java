package cci.web.controller.client;

public class ViewClientFilter {
 	private ViewClientCondition condition = new ViewClientCondition();
   	private ViewClient viewclient = new ViewClient();
   
	public ViewClientCondition getCondition() {
		return condition;
	}
	public void setCondition(ViewClientCondition condition) {
		this.condition = condition;
	}

	public ViewClient getViewclient() {
		return viewclient;
	}
	public void setViewclient(ViewClient viewclient) {
		this.viewclient = viewclient;
	}
		
	public ViewClientFilter() {
		super();		
	}

	public ViewClientFilter(ViewClient viewclient, ViewClientCondition condition) {
		super();
		this.condition = condition;
		this.viewclient = viewclient;
	}
	
	@Override
	public String toString() {
		return "ViewFilter [viewcertificate=" + viewclient + "] \n [condition="
				+ condition + "]";
	}

}
