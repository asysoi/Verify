package cci.model.owncert;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OwnCertificates {
	
	private List<OwnCertificate> owncertificates;
	
    @XmlElement(name="owncertificate")  
	public List<OwnCertificate> getOwncertificates() {
		return owncertificates;
	}
    
	public void setOwncertificates(List<OwnCertificate> owncertificates) {
		this.owncertificates = owncertificates;
	}

}
