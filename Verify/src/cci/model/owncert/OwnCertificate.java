package cci.model.owncert;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlType(propOrder = {"type", "number", "blanknumber", "beltpp", "customername", "customeraddress", "customerunp", 
		"additionalblanks", "datestart", "dateexpire", "expert", "signer", "signerjob", "datecert", "dateload",  
		"datestop", "datechange", "numbernext", "numberprev", "status", "productdescription", "filename",
		"factories", "branches", "products" })

public class OwnCertificate {
	private int id;
	@JsonIgnore
	private int id_beltpp;
	private String type;
	private String number;
	private String blanknumber;
	private String customername;
	private String customeraddress;
	private String customerunp;
	private String additionalblanks;
	private Company beltpp;
	private String datestart;
	private String dateexpire;
	private String expert;
	private String signer;
	private String signerjob;
	private String datecert;
	private String dateload;
	private String datestop;
	private String datechange;
	private String numberprev;
	private String numbernext;
	private String status;
	private String filename;
	private String productdescription;
	private List<Factory> factories;
	private List<Branch> branches;
	private List<Product> products;
	private String otd_id;
	private String xml;
	
	
		
	@XmlTransient
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	@XmlTransient
	public String getOtd_id() {
		return otd_id;
	}
	
	public void setOtd_id(String otd_id) {
		this.otd_id = otd_id;
	}

	@XmlTransient
	public String getBeltppname() {
		return beltpp != null ? beltpp.getName() : "";
	}
	
	@XmlTransient
	public String getBeltppaddress() {
		return beltpp != null ? beltpp.getAddress() : "";
	}

	@XmlTransient
	public String getBeltppphone() {
		return beltpp != null ? beltpp.getPhone() : "";
	}

	@XmlTransient
	public String getBeltppunp() {
		return beltpp != null ? beltpp.getUnp() : "";
	}

	@XmlTransient
	public String getFactorylist() {
		String str="";
		if (factories != null) {
		   for (Factory factory: factories) {
			   str += (str.isEmpty() ? "" : "; ") + factory.getAddress();
		   }
		}
		return str;
	}
	
	@XmlTransient
	public String getBranchlist() {
		String str="";
		if (branches != null) {
		   for (Branch branch: branches) {
			   str += (str.isEmpty() ? "" : "; ") + branch.getName() + 
					   (branch.getAddress() != null && ! branch.getAddress().trim().isEmpty() ?  ", " + branch.getAddress() : "");
		   }
		}
		return str;
	}
	
	@XmlTransient
	public String getProductlist() {
		String str="";
		if (products != null) {
		   for (Product product: products) {
			   str += (str.isEmpty() ? "" : "; ") + product.getName() + 
					   (product.getNcode() != null && ! product.getNcode().trim().isEmpty() ?  " - " + product.getNcode() : "");
		   }
		}
		return str;
	}
	
	@XmlTransient
	public String getCodes() {
		String str="";
		if (products != null) {
		   for (Product product: products) {
			   str += (str.isEmpty() ? "" : ";") + product.getNcode();
		   }
		}
		return str;
	}
	
	@XmlTransient
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@XmlTransient
	public int getId_beltpp() {
		return id_beltpp;
	}
	public void setId_beltpp(int id_beltpp) {
		this.id_beltpp = id_beltpp;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBlanknumber() {
		return blanknumber;
	}
	public void setBlanknumber(String blanknumber) {
		this.blanknumber = blanknumber;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getCustomeraddress() {
		return customeraddress;
	}
	public void setCustomeraddress(String customeraddress) {
		this.customeraddress = customeraddress;
	}
	public String getCustomerunp() {
		return customerunp;
	}
	public void setCustomerunp(String customerunp) {
		this.customerunp = customerunp;
	}
	
	public String getAdditionalblanks() {
		return additionalblanks;
	}
	public void setAdditionalblanks(String additionalblanks) {
		this.additionalblanks = additionalblanks;
	}
	public Company getBeltpp() {
		return beltpp;
	}
	public void setBeltpp(Company beltpp) {
		this.beltpp = beltpp;
	}
	public String getDatestart() {
		return datestart;
	}
	public void setDatestart(String datestart) {
		this.datestart = datestart;
	}
	public String getDateexpire() {
		return dateexpire;
	}
	public void setDateexpire(String dateexpire) {
		this.dateexpire = dateexpire;
	}
	public String getExpert() {
		return expert;
	}
	public void setExpert(String expert) {
		this.expert = expert;
	}
	public String getSigner() {
		return signer;
	}
	public void setSigner(String signer) {
		this.signer = signer;
	}
	public String getSignerjob() {
		return signerjob;
	}
	public void setSignerjob(String signerjob) {
		this.signerjob = signerjob;
	}
	public String getDatecert() {
		return datecert;
	}
	public void setDatecert(String datecert) {
		this.datecert = datecert;
	}
	
	public String getDateload() {
		return dateload;
	}
	public void setDateload(String dateload) {
		this.dateload = dateload;
	}

	public String getDatestop() {
		return datestop;
	}

	public void setDatestop(String datestop) {
		this.datestop = datestop;
	}

	public String getDatechange() {
		return datechange;
	}

	public void setDatechange(String datechange) {
		this.datechange = datechange;
	}

	public String getNumberprev() {
		return numberprev;
	}

	public void setNumberprev(String numberprev) {
		this.numberprev = numberprev;
	}

	public String getNumbernext() {
		return numbernext;
	}

	public void setNumbernext(String numbernext) {
		this.numbernext = numbernext;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getProductdescription() {
		return productdescription;
	}

	public void setProductdescription(String productdescription) {
		this.productdescription = productdescription;
	}

	@XmlElementWrapper
	@XmlElement(name="factory")
	public void setFactories(List<Factory> factories) {
		this.factories = factories;
	}

	public List<Factory> getFactories() {
		return factories;
	}
		
	@XmlElementWrapper
	@XmlElement(name="branch")
	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}
	
	public List<Branch> getBranches() {
		return branches;
	}
	
	@XmlElementWrapper
	@XmlElement(name="product")
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return "OwnCertificate [id=" + id + ", id_beltpp=" + id_beltpp + ", type=" + type + ", number=" + number
				+ ", blanknumber=" + blanknumber + ", customername=" + customername + ", customeraddress="
				+ customeraddress + ", customerunp=" + customerunp + ", additionalblanks=" + additionalblanks
				+ ", beltpp=" + beltpp + ", datestart=" + datestart + ", dateexpire=" + dateexpire + ", expert="
				+ expert + ", signer=" + signer + ", signerjob=" + signerjob + ", datecert=" + datecert + ", dateload="
				+ dateload + ", datestop=" + datestop + ", datechange=" + datechange + ", numberprev=" + numberprev
				+ ", numbernext=" + numbernext + ", status=" + status + ", filename=" + filename
				+ ", productdescription=" + productdescription + ", factories=" + factories + ", branches=" + branches
				+ ", products=" + products + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalblanks == null) ? 0 : additionalblanks.hashCode());
		result = prime * result + ((beltpp == null) ? 0 : beltpp.hashCode());
		result = prime * result + ((blanknumber == null) ? 0 : blanknumber.hashCode());
		result = prime * result + ((branches == null) ? 0 : branches.hashCode());
		result = prime * result + ((customeraddress == null) ? 0 : customeraddress.hashCode());
		result = prime * result + ((customername == null) ? 0 : customername.hashCode());
		result = prime * result + ((customerunp == null) ? 0 : customerunp.hashCode());
		result = prime * result + ((datecert == null) ? 0 : datecert.hashCode());
		result = prime * result + ((datechange == null) ? 0 : datechange.hashCode());
		result = prime * result + ((dateexpire == null) ? 0 : dateexpire.hashCode());
		result = prime * result + ((dateload == null) ? 0 : dateload.hashCode());
		result = prime * result + ((datestart == null) ? 0 : datestart.hashCode());
		result = prime * result + ((datestop == null) ? 0 : datestop.hashCode());
		result = prime * result + ((expert == null) ? 0 : expert.hashCode());
		result = prime * result + ((factories == null) ? 0 : factories.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + id;
		result = prime * result + id_beltpp;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((numbernext == null) ? 0 : numbernext.hashCode());
		result = prime * result + ((numberprev == null) ? 0 : numberprev.hashCode());
		result = prime * result + ((productdescription == null) ? 0 : productdescription.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((signer == null) ? 0 : signer.hashCode());
		result = prime * result + ((signerjob == null) ? 0 : signerjob.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OwnCertificate other = (OwnCertificate) obj;
		if (additionalblanks == null) {
			if (other.additionalblanks != null)
				return false;
		} else if (!additionalblanks.equals(other.additionalblanks))
			return false;
		if (beltpp == null) {
			if (other.beltpp != null)
				return false;
		} else if (!beltpp.equals(other.beltpp))
			return false;
		if (blanknumber == null) {
			if (other.blanknumber != null)
				return false;
		} else if (!blanknumber.equals(other.blanknumber))
			return false;
		if (branches == null) {
			if (other.branches != null)
				return false;
		} else if (!branches.equals(other.branches))
			return false;
		if (customeraddress == null) {
			if (other.customeraddress != null)
				return false;
		} else if (!customeraddress.equals(other.customeraddress))
			return false;
		if (customername == null) {
			if (other.customername != null)
				return false;
		} else if (!customername.equals(other.customername))
			return false;
		if (customerunp == null) {
			if (other.customerunp != null)
				return false;
		} else if (!customerunp.equals(other.customerunp))
			return false;
		if (datecert == null) {
			if (other.datecert != null)
				return false;
		} else if (!datecert.equals(other.datecert))
			return false;
		if (datechange == null) {
			if (other.datechange != null)
				return false;
		} else if (!datechange.equals(other.datechange))
			return false;
		if (dateexpire == null) {
			if (other.dateexpire != null)
				return false;
		} else if (!dateexpire.equals(other.dateexpire))
			return false;
		if (dateload == null) {
			if (other.dateload != null)
				return false;
		} else if (!dateload.equals(other.dateload))
			return false;
		if (datestart == null) {
			if (other.datestart != null)
				return false;
		} else if (!datestart.equals(other.datestart))
			return false;
		if (datestop == null) {
			if (other.datestop != null)
				return false;
		} else if (!datestop.equals(other.datestop))
			return false;
		if (expert == null) {
			if (other.expert != null)
				return false;
		} else if (!expert.equals(other.expert))
			return false;
		if (factories == null) {
			if (other.factories != null)
				return false;
		} else if (!factories.equals(other.factories))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (id != other.id)
			return false;
		if (id_beltpp != other.id_beltpp)
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (numbernext == null) {
			if (other.numbernext != null)
				return false;
		} else if (!numbernext.equals(other.numbernext))
			return false;
		if (numberprev == null) {
			if (other.numberprev != null)
				return false;
		} else if (!numberprev.equals(other.numberprev))
			return false;
		if (productdescription == null) {
			if (other.productdescription != null)
				return false;
		} else if (!productdescription.equals(other.productdescription))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (signer == null) {
			if (other.signer != null)
				return false;
		} else if (!signer.equals(other.signer))
			return false;
		if (signerjob == null) {
			if (other.signerjob != null)
				return false;
		} else if (!signerjob.equals(other.signerjob))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
