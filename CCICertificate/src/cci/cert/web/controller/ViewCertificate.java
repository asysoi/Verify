package cci.cert.web.controller;

import cci.cert.model.Certificate;

public class ViewCertificate extends Certificate {

	private String tovar;
	private String kriter;
	private String schet;
	private String datefrom;
	private String dateto;

	public String getTovar() {
		return tovar;
	}

	public void setTovar(String tovar) {
		this.tovar = tovar;
	}

	public String getKriter() {
		return kriter;
	}

	public void setKriter(String kriter) {
		this.kriter = kriter;
	}

	public String getSchet() {
		return schet;
	}

	public void setSchet(String schet) {
		this.schet = schet;
	}

	public String getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return dateto;
	}

	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

	@Override
	public String toString() {
		return "ViewCertificate [tovar=" + tovar + ", kriter=" + kriter
				+ ", schet=" + schet + ", datefrom=" + datefrom + ", dateto="
				+ dateto + ", toString()=" + super.toString() + "]";
	}

	
	
}
