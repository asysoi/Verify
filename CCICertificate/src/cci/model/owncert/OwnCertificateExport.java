package cci.model.owncert;

public class OwnCertificateExport extends OwnCertificate {
	private String beltppname;
	private String beltppunp;
	private String productname;
	private String beltppaddress;

	public String getBeltppname() {
		return beltppname;
	}

	public void setBeltppname(String beltppname) {
		this.beltppname = beltppname;
	}

	public String getBeltppaddress() {
		return beltppaddress;
	}

	public void setBeltppaddress(String beltppaddress) {
		this.beltppaddress = beltppaddress;
	}

	public String getBeltppunp() {
		return beltppunp;
	}

	public void setBeltppunp(String beltppunp) {
		this.beltppunp = beltppunp;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

}
