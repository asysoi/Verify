package cci.cert.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@XmlRootElement(name="cert")
@Component
public class Certificate {
	private Long cert_id;
	private String forms;
	private String unn;
	private String kontrp;
	private String kontrs;
	private String adress;
	private String poluchat;
	private String adresspol;
	private String datacert;
	private String nomercert;
	private String expert;
	private String nblanka;
	private String rukovod;
	private String transport;
	private String marshrut;
	private String otmetka;
	private String stranav;
	private String stranapr;
	private String status;
	private String koldoplist;
	private String flexp;
	private String unnexp;
	private String expp;
	private String exps;
	private String expadress;
	private String flimp;
	private String importer;
	private String adressimp;
	private String flsez;
	private String sez;
	private String flsezrez;
	private String stranap;
	private String otd_name;
	private String otd_address_index;
	private String otd_address_city;
	private String otd_address_line;
	private String otd_address_home;
	
	// список продукции
	private List<Product> products;
	//private Party otdelenie;

	
	public Long getCert_id() {
		return cert_id;
	}
	public void setCert_id(Long cert_id) {
		this.cert_id = cert_id;
	}
	
	public String getForms() {
		return forms;
	}
	public void setForms(String forms) {
		this.forms = forms;
	}
	
	public String getUnn() {
		return unn;
	}
	public void setUnn(String unn) {
		this.unn = unn;
	}
	
	public String getKontrp() {
		return kontrp;
	}
	public void setKontrp(String kontrp) {
		this.kontrp = kontrp;
	}
	
	public String getKontrs() {
		return kontrs;
	}
	public void setKontrs(String kontrs) {
		this.kontrs = kontrs;
	}
	
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public String getPoluchat() {
		return poluchat;
	}
	public void setPoluchat(String poluchat) {
		this.poluchat = poluchat;
	}
	
	public String getAdresspol() {
		return adresspol;
	}
	public void setAdresspol(String adresspol) {
		this.adresspol = adresspol;
	}
	
	public String getDatacert() {
		return datacert;
	}
	public void setDatacert(String datacert) {
		this.datacert = datacert;
	}
	
	public String getNomercert() {
		return nomercert;
	}
	public void setNomercert(String nomercert) {
		this.nomercert = nomercert;
	}
	
	public String getExpert() {
		return expert;
	}
	public void setExpert(String expert) {
		this.expert = expert;
	}
	
	public String getNblanka() {
		return nblanka;
	}
	public void setNblanka(String nblanka) {
		this.nblanka = nblanka;
	}
	
	public String getRukovod() {
		return rukovod;
	}
	public void setRukovod(String rukovod) {
		this.rukovod = rukovod;
	}
	
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	
	public String getMarshrut() {
		return marshrut;
	}
	public void setMarshrut(String marshrut) {
		this.marshrut = marshrut;
	}
	
	public String getOtmetka() {
		return otmetka;
	}
	public void setOtmetka(String otmetka) {
		this.otmetka = otmetka;
	}
	
	public String getStranav() {
		return stranav;
	}
	public void setStranav(String stranav) {
		this.stranav = stranav;
	}
	
	public String getStranapr() {
		return stranapr;
	}
	public void setStranapr(String stranapr) {
		this.stranapr = stranapr;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getKoldoplist() {
		return koldoplist;
	}
	public void setKoldoplist(String koldoplist) {
		this.koldoplist = koldoplist;
	}
	
	public String getFlexp() {
		return flexp;
	}
	public void setFlexp(String flexp) {
		this.flexp = flexp;
	}
	
	public String getUnnexp() {
		return unnexp;
	}
	public void setUnnexp(String unnexp) {
		this.unnexp = unnexp;
	}
	
	public String getExpp() {
		return expp;
	}
	public void setExpp(String expp) {
		this.expp = expp;
	}
	
	public String getExps() {
		return exps;
	}
	public void setExps(String exps) {
		this.exps = exps;
	}
	
	public String getExpadress() {
		return expadress;
	}
	public void setExpadress(String expadress) {
		this.expadress = expadress;
	}
	
	public String getFlimp() {
		return flimp;
	}
	public void setFlimp(String flimp) {
		this.flimp = flimp;
	}
	
	public String getImporter() {
		return importer;
	}
	public void setImporter(String importer) {
		this.importer = importer;
	}
	
	public String getAdressimp() {
		return adressimp;
	}
	public void setAdressimp(String adressimp) {
		this.adressimp = adressimp;
	}
	
	public String getFlsez() {
		return flsez;
	}
	public void setFlsez(String flsez) {
		this.flsez = flsez;
	}
	
	public String getSez() {
		return sez;
	}
	public void setSez(String sez) {
		this.sez = sez;
	}
	
	public String getFlsezrez() {
		return flsezrez;
	}
	public void setFlsezrez(String flsezrez) {
		this.flsezrez = flsezrez;
	}
	
	public String getStranap() {
		return stranap;
	}
	public void setStranap(String stranap) {
		this.stranap = stranap;
	}
	
	@XmlElementWrapper(name = "products")
	@XmlElement(name = "row")
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public String getOtd_address_index() {
		return otd_address_index;
	}
	public void setOtd_address_index(String otd_address_index) {
		this.otd_address_index = otd_address_index;
	}
	public String getOtd_address_city() {
		return otd_address_city;
	}
	public void setOtd_address_city(String otd_address_city) {
		this.otd_address_city = otd_address_city;
	}
	public String getOtd_address_line() {
		return otd_address_line;
	}
	public void setOtd_address_line(String otd_address_line) {
		this.otd_address_line = otd_address_line;
	}
	public String getOtd_address_home() {
		return otd_address_home;
	}
	public void setOtd_address_home(String otd_address_home) {
		this.otd_address_home = otd_address_home;
	}
	public String getOtd_name() {
		return otd_name;
	}
	public void setOtd_name(String otd_name) {
		this.otd_name = otd_name;
	}
	
	public String getShort_kontrp() {
		
		return kontrp.length() > 100 ?  kontrp.substring(1, 100) + " ..." :  kontrp;
	}
	
	public String toString() {
		return "Сертификат :" + nomercert + " Бланк: " + nblanka + " Дата: " + datacert;
	}
}
