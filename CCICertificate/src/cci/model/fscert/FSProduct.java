package cci.model.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

@XmlRootElement(name="row")
@XmlType(propOrder = {"numerator", "tovar"})
@Component
public class FSProduct {
	private Long id;
	private Long id_fscert;
	private String numerator;
	private String tovar;
	
	@XmlTransient
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@XmlTransient	
	public Long getId_fscert() {
		return id_fscert;
	}
	public void setId_fscert(Long id_fscert) {
		this.id_fscert = id_fscert;
	}
	public String getNumerator() {
		return numerator;
	}
	public void setNumerator(String numerator) {
		this.numerator = numerator;
	}
	public String getTovar() {
		return tovar;
	}
	public void setTovar(String tovar) {
		this.tovar = tovar;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", id_fscert=" + id_fscert 
				+ ", numerator=" + numerator + ", tovar=" + tovar + "]";
	}
}
