package cci.model.cert.fscert;

import java.util.List;
import javax.xml.bind.annotation.XmlTransient;
import cci.model.cert.Product;

// ------------------------------------
//       Free Certificate Model
// ------------------------------------
public class FSCertificate {
	private long id;
	private String number;
	private String parentnumber;
	private String dateissue;
	private String dateexpiry;
	private String confirmation;
	private String declaration;
	private Branch branch;
	private Exporter exporter;
	private Producer producer;
	private Expert expert;
	private Signer signer;
	private List<Product> products;
	private List<Blank> blanks;

	@XmlTransient
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
		this.dateissue = dateissue;
	}

	public String getDateexpiry() {
		return dateexpiry;
	}

	public void setDateexpiry(String dateexpiry) {
		this.dateexpiry = dateexpiry;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Blank> getBlanks() {
		return blanks;
	}

	public void setBlanks(List<Blank> blanks) {
		this.blanks = blanks;
	}

	
	
	
}
