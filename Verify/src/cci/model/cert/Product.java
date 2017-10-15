package cci.model.cert;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

@XmlRootElement(name="row")
@XmlType(propOrder = {"numerator", "vidup", "tovar", "kriter", "ves", "schet", "fobvalue"})
@Component
public class Product {
	private Long product_id;
	private String numerator;
	private String tovar;
	private String vidup;
	private String kriter;
	private String ves;
	private String schet;
	private String fobvalue;
	
	@XmlTransient
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
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
	public String getVidup() {
		return vidup;
	}
	public void setVidup(String vidup) {
		this.vidup = vidup;
	}
	public String getKriter() {
		return kriter;
	}
	public void setKriter(String kriter) {
		this.kriter = kriter;
	}
	public String getVes() {
		return ves;
	}
	public void setVes(String ves) {
		this.ves = ves;
	}
	public String getSchet() {
		return schet;
	}
	public void setSchet(String schet) {
		this.schet = schet;
	}
	public String getFobvalue() {
		return fobvalue;
	}
	public void setFobvalue(String fobvalue) {
		this.fobvalue = fobvalue;
	}
}
