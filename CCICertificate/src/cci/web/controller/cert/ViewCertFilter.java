   package cci.web.controller.cert;

import java.util.LinkedHashMap;
import java.util.Map;

import cci.model.cert.Certificate;

public class ViewCertFilter {
   	private ViewCertCondition condition = new ViewCertCondition();
   	private ViewCertificate viewcertificate = new ViewCertificate();;
   
	public ViewCertCondition getCondition() {
		return condition;
	}
	public void setCondition(ViewCertCondition condition) {
		this.condition = condition;
	}

	public ViewCertificate getViewcertificate() {
		return viewcertificate;
	}
	public void setViewcertificate(ViewCertificate viewcertificate) {
		this.viewcertificate = viewcertificate;
	}
		
	public ViewCertFilter() {
		super();		
	}

	public ViewCertFilter(ViewCertificate viewcertificate, ViewCertCondition condition) {
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
