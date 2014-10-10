   package cci.cert.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import cci.cert.model.Certificate;

public class ViewFilter {
   	private ViewCondition condition = new ViewCondition();
   	private ViewCertificate viewcertificate = new ViewCertificate();;
   
	public ViewCondition getCondition() {
		return condition;
	}
	public void setCondition(ViewCondition condition) {
		this.condition = condition;
	}

	public ViewCertificate getViewcertificate() {
		return viewcertificate;
	}
	public void setViewcertificate(ViewCertificate viewcertificate) {
		this.viewcertificate = viewcertificate;
	}
		
	public ViewFilter() {
		super();		
	}

	public ViewFilter(ViewCertificate viewcertificate, ViewCondition condition) {
		super();
		this.condition = condition;
		this.viewcertificate = viewcertificate;
	}
	
	@Override
	public String toString() {
		return "ViewFilter [viewcertificate=" + viewcertificate + "] \n [condition="
				+ condition + "]";
	}
	
	
}
