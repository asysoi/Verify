package cci.model.fscert;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

import cci.model.Department;
import cci.model.cert.Product;

//------------------------------------
//Free Certificate Model
//------------------------------------

@XmlRootElement(name = "fscertificate")
@Component
@XmlType(propOrder = {"certnumber","parentnumber", "dateissue", "dateexpiry", "confirmation", "declaration", "codecountrytarget", "datecert", "listscount", 
		"language", "branch", "exporter", "producer", "expert", "signer", "products", "blanks"})

public class FSCertificate {
	private long id;
	private String certnumber;
	private String parentnumber;
	private String dateissue;
	private String dateexpiry;
	private String confirmation;
	private String declaration;
	private String codecountrytarget;
	private Integer listscount;
	private String datecert;
	private Branch branch;
	private Exporter exporter;
	private Producer producer;
	private Expert expert;
	private Signer signer;
	private List<FSProduct> products;
	private List<FSBlank> blanks;
	private long otd_id;
	private String language="RU";
	private Department department;
	
	
	public void init() {
		branch = new Branch();
		exporter = new Exporter();
		producer = new Producer();
		expert = new Expert();
		signer = new Signer();
	}

    
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@XmlTransient
	public String getSotd_id() {
		return ""+otd_id;
	}
	public void setSotd_id(String sotd_id) {
		this.otd_id = Integer.valueOf(sotd_id);
	}

	
	@XmlTransient
	public long getOtd_id() {
		return otd_id;
	}
	public void setOtd_id(long otd_id) {
		this.otd_id = otd_id;
	}

	@XmlTransient
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCertnumber() {
		return certnumber;
	}

	public void setCertnumber(String number) {
		this.certnumber = number;
	}

	public String getParentnumber() {
		return parentnumber;
	}

	public void setParentnumber(String parentnumber) {
		this.parentnumber = parentnumber;
	}

	public String getDateissue() {
		return dateissue;
	}

	public void setDateissue(String dateissue) {
		this.dateissue = dateissue.replace("00:00:00.0", "");
	}

	public String getDateexpiry() {
		return dateexpiry;
	}

	public void setDateexpiry(String dateexpiry) {
		this.dateexpiry = dateexpiry.replace("00:00:00.0", "");
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public String getCodecountrytarget() {
		return codecountrytarget;
	}

	public void setCodecountrytarget(String codecountrytarget) {
		this.codecountrytarget = codecountrytarget;
	}

	public Integer getListscount() {
		return listscount;
	}

	public void setListscount(Integer listscount) {
		this.listscount = listscount;
	}

	public String getDatecert() {
		return datecert;
	}

	public void setDatecert(String datecert) {
		this.datecert = datecert.replace("00:00:00.0", "");
	}
	
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Exporter getExporter() {
		return exporter;
	}

	public void setExporter(Exporter exporter) {
		this.exporter = exporter;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public Signer getSigner() {
		return signer;
	}

	public void setSigner(Signer signer) {
		this.signer = signer;
	}

	@XmlElementWrapper(name = "products")
	@XmlElement(name = "row")
	public List<FSProduct> getProducts() {
		if (products == null) {
			products = new ArrayList<FSProduct>();
		}
		return products;
	}

	public void setProducts(List<FSProduct> products) {
		this.products = products;
	}

	@XmlElementWrapper(name = "blanks")
	@XmlElement(name = "blank")
	public List<FSBlank> getBlanks() {
		if (blanks == null) {
			blanks = new ArrayList<FSBlank>();
		}
		return blanks;
	}

	public void setBlanks(List<FSBlank> blanks) {
		this.blanks = blanks;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "FSCertificate [id=" + id + ", certnumber=" + certnumber + ", parentnumber=" + parentnumber
				+ ", dateissue=" + dateissue + ", dateexpiry=" + dateexpiry + ", confirmation=" + confirmation
				+ ", declaration=" + declaration + ", codecountrytarget=" + codecountrytarget + ", listscount="
				+ listscount + ", datecert=" + datecert + ", branch=" + branch + ", exporter=" + exporter
				+ ", producer=" + producer + ", expert=" + expert + ", signer=" + signer + ", products=" + products
				+ ", blanks=" + blanks + ", otd_id=" + otd_id + ", language=" + language + "]";
	}

}
