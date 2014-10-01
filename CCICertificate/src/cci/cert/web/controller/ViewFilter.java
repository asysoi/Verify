   package cci.cert.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import cci.cert.model.Certificate;

public class ViewFilter {
    private Certificate certificate;
   	private ViewCondition condition;
   
   	
	public Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	public ViewCondition getCondition() {
		return condition;
	}
	public void setCondition(ViewCondition condition) {
		this.condition = condition;
	}
	
	public ViewFilter() {
		super();		
	}
	
	public ViewFilter(Certificate certificate, ViewCondition condition) {
		super();
		this.certificate = certificate;
		this.condition = condition;
	}
	
	@Override
	public String toString() {
		return "ViewFilter [certificate=" + certificate + ", condition="
				+ condition + "]";
	}
	
	
}
