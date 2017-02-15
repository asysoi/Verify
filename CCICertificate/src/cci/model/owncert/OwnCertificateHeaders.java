package cci.model.owncert;

//* ------------------------------------
//*  This class contains   
//*  list of light OwnCertificates   
//* ------------------------------------

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OwnCertificateHeaders {

	private List<OwnCertificateHeader> owncertificateheaders;

	@XmlElement(name = "owncertificate")
	public List<OwnCertificateHeader> getOwncertificateheaders() {
		return owncertificateheaders;
	}

	public void setOwncertificateheaders(
			List<OwnCertificateHeader> owncertificateheaders) {
		this.owncertificateheaders = owncertificateheaders;
	}

}
