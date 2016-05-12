package cci.model.cert;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="certifications")
public class CertificateList {
	 
	List<Certificate> list;

	public List<Certificate> getList() {
		return list;
	}
    
	@XmlElement
	public void setList(List<Certificate> list) {
		this.list = list;
	}
	
	
}
