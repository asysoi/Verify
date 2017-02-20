package cci.web.controller.fscert;


public class ViewFSCertificateFilter {
	private ViewFSCertificateCondition condition = new ViewFSCertificateCondition();
   	private ViewFSCertificate viewcertificate = new ViewFSCertificate();
   
	public ViewFSCertificateCondition getCondition() {
		return condition;
	}
	public void setCondition(ViewFSCertificateCondition condition) {
		this.condition = condition;
	}

	public ViewFSCertificate getViewcertificate() {
		return viewcertificate;
	}
	public void setViewcertificate(ViewFSCertificate viewcertificate) {
		this.viewcertificate = viewcertificate;
	}
		
	public ViewFSCertificateFilter() {
		super();		
	}

	public ViewFSCertificateFilter(ViewFSCertificate viewcertificate, ViewFSCertificateCondition condition) {
		super();
		this.condition = condition;
		this.viewcertificate = viewcertificate;
	}
	

}
