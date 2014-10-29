package cci.cert.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@XmlRootElement(name = "cert")
@Component
public class Certificate {
	static final Logger LOG = Logger.getLogger(Certificate.class);
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
	private int otd_id;
	private String otd_name;
	private String otd_address_index;
	private String otd_address_city;
	private String otd_address_line;
	private String otd_address_home;
	private String parentnumber = "";
	private String parentstatus = "";
	private String codestranav;
	private String codestranapr;
	private String codestranap;
	private String category;
	
	private List<Product> products;
	private String childnumber = "";
	private Integer child_id = 0;

	public String getChildnumber() {
		return childnumber;
	}

	public void setChildnumber(String childnumber) {
		this.childnumber = childnumber;
	}

	public Integer getChild_id() {
		return child_id;
	}

	public void setChild_id(Integer child_id) {
		this.child_id = child_id;
	}

	public String getParentnumber() {
		return parentnumber;
	}

	public void setParentnumber(String parentnumber) {
		this.parentnumber = parentnumber;
	}

	public String getParentstatus() {
		return parentstatus;
	}

	public void setParentstatus(String parentstatus) {
		this.parentstatus = parentstatus;
	}

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
		// return kontrp.length() > 100 ? kontrp.substring(1, 100) + " ..." :
		// kontrp;
		return (kontrp != null && kontrp.length() > 100) ? kontrp.substring(1,
				100) + " ..." : kontrp;
	}

	public int getOtd_id() {
		return otd_id;
	}

	public void setOtd_id(int otd_id) {
		this.otd_id = otd_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCodestranav() {
		return codestranav;
	}

	public void setCodestranav(String codestranav) {
		this.codestranav = codestranav;
	}

	public String getCodestranapr() {
		return codestranapr;
	}

	public void setCodestranapr(String codestranapr) {
		this.codestranapr = codestranapr;
	}

	public String getCodestranap() {
		return codestranap;
	}

	public void setCodestranap(String codestranap) {
		this.codestranap = codestranap;
	}
	
	
	
	// old version. see new version	
	public int hashCodeOld() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adress == null) ? 0 : adress.hashCode());
		result = prime * result
				+ ((adressimp == null) ? 0 : adressimp.hashCode());
		result = prime * result
				+ ((adresspol == null) ? 0 : adresspol.hashCode());
		result = prime * result
				+ ((datacert == null) ? 0 : datacert.hashCode());
		result = prime * result
				+ ((expadress == null) ? 0 : expadress.hashCode());
		result = prime * result + ((expert == null) ? 0 : expert.hashCode());
		result = prime * result + ((expp == null) ? 0 : expp.hashCode());
		result = prime * result + ((exps == null) ? 0 : exps.hashCode());
		result = prime * result + ((flexp == null) ? 0 : flexp.hashCode());
		result = prime * result + ((flimp == null) ? 0 : flimp.hashCode());
		result = prime * result + ((flsez == null) ? 0 : flsez.hashCode());
		result = prime * result
				+ ((flsezrez == null) ? 0 : flsezrez.hashCode());
		result = prime * result + ((forms == null) ? 0 : forms.hashCode());
		result = prime * result
				+ ((importer == null) ? 0 : importer.hashCode());
		result = prime * result
				+ ((koldoplist == null) ? 0 : koldoplist.hashCode());
		result = prime * result + ((kontrp == null) ? 0 : kontrp.hashCode());
		result = prime * result + ((kontrs == null) ? 0 : kontrs.hashCode());
		result = prime * result
				+ ((marshrut == null) ? 0 : marshrut.hashCode());
		result = prime * result + ((nblanka == null) ? 0 : nblanka.hashCode());
		result = prime * result
				+ ((nomercert == null) ? 0 : nomercert.hashCode());
		result = prime * result + otd_id;
		result = prime * result + ((otmetka == null) ? 0 : otmetka.hashCode());
		result = prime * result
				+ ((poluchat == null) ? 0 : poluchat.hashCode());
		result = prime * result + ((rukovod == null) ? 0 : rukovod.hashCode());
		result = prime * result + ((sez == null) ? 0 : sez.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stranap == null) ? 0 : stranap.hashCode());
		result = prime * result
				+ ((stranapr == null) ? 0 : stranapr.hashCode());
		result = prime * result + ((stranav == null) ? 0 : stranav.hashCode());
		result = prime * result
				+ ((transport == null) ? 0 : transport.hashCode());
		result = prime * result + ((unn == null) ? 0 : unn.hashCode());
		result = prime * result + ((unnexp == null) ? 0 : unnexp.hashCode());
		return result;
	}

	// old version. see new version
	public boolean equals(Object obj) {
		//LOG.info("---------------------- > Starting certificate comparing.... < -------------------------");
		
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			LOG.info(" Compare FALSE :  " + 1);
			return false;
		}
		if (getClass() != obj.getClass()) {
			LOG.info(" Compare FALSE :  " + 2);
			return false;
		}
		Certificate other = (Certificate) obj;
		//LOG.info(this);
		//LOG.info(other);
		
		if (adress == null) {
			if (other.adress != null && other.adress.length() > 0) {
				LOG.info(" Compare FALSE :  " + 3);
				return false;
			}
		} else if (!adress.equals(other.adress)) {
			LOG.info(" Compare FALSE :  " + 4);
			return false;
		}
		if (adressimp == null) {
			if (other.adressimp != null && other.adressimp.length() > 0) {
				LOG.info(" Compare FALSE :  " + 5);
				return false;
			}
		} else if (!adressimp.equals(other.adressimp)) {
			LOG.info(" Compare FALSE :  " + 6);
			return false;
		}
		if (adresspol == null) {
			if (other.adresspol != null && other.adresspol.length() > 0) {
				LOG.info(" Compare FALSE :  " + 7);
				return false;
			}
		} else if (!adresspol.equals(other.adresspol)) {
			LOG.info(" Compare FALSE :  " + 8);
			return false;
		}
		
		
		if (category == null) {
			if (other.category != null && other.category.length() > 0) {
				LOG.info(" Compare FALSE :  " + 101);
				return false;
			}
		} else if (!category.equals(other.category)) {
			LOG.info(" Compare FALSE :  " + 102);
			return false; }
		if (codestranap == null) {
			if (other.codestranap != null && other.codestranap.length() > 0) {
				LOG.info(" Compare FALSE :  " + 103);
				return false; }
		} else if (!codestranap.equals(other.codestranap)) {
			LOG.info(" Compare FALSE :  " + 104);
				return false; }
		if (codestranapr == null) {
			if (other.codestranapr != null && other.codestranapr.length() > 0) {
				LOG.info(" Compare FALSE :  " + 105);
				return false; }
		} else if (!codestranapr.equals(other.codestranapr)) {
			LOG.info(" Compare FALSE :  " + 106);
			return false;
		}
		if (codestranav == null) {
			if (other.codestranav != null && other.codestranav.length() > 0) {
				LOG.info(" Compare FALSE :  " + 107);
				return false; }
		} else if (!codestranav.equals(other.codestranav)) {
			LOG.info(" Compare FALSE :  " + 108);
			return false;
		}
		if (parentnumber == null) {
			if (other.parentnumber != null && other.parentnumber.length() > 0) {
				LOG.info(" Compare FALSE :  " + 109);
				return false; }
		} else if (!parentnumber.equals(other.parentnumber)) {
			LOG.info(" Compare FALSE :  " + 110);
			return false;
		}
		if (parentstatus == null) {
			if (other.parentstatus != null && other.parentstatus.length() > 0) {
				LOG.info(" Compare FALSE :  " + 111);
				return false; }
		} else if (!parentstatus.equals(other.parentstatus)) {
			LOG.info(" Compare FALSE :  " + 112);
			return false;
		}
		
		
		if (datacert == null) {
			if (other.datacert != null && other.datacert.length() > 0) {
				LOG.info(" Compare FALSE :  " + 9);
				return false;
			}
		} else if (!datacert.equals(other.datacert)) {
			LOG.info(" Compare FALSE :  " + 10);
			return false;
		}
		if (expadress == null) {
			if (other.expadress != null && other.expadress.length() > 0) {
				LOG.info(" Compare FALSE :  " + 11);
				return false;
			}
		} else if (!expadress.equals(other.expadress)) {
			LOG.info(" Compare FALSE :  " + 12);
			return false;
		}
		if (expert == null) {
			if (other.expert != null && other.expert.length() > 0) {
				LOG.info(" Compare FALSE :  " + 13);
				return false;
			}
		} else if (!expert.equals(other.expert)) {
			LOG.info(" Compare FALSE :  " + 14);
			return false;
		}
		if (expp == null) {
			if (other.expp != null && other.expp.length() > 0) {
				LOG.info(" Compare FALSE :  " + 15);
				return false;
			}
		} else if (!expp.equals(other.expp)) {
			LOG.info(" Compare FALSE :  " + 16);
			return false;
		}
		if (exps == null) {
			if (other.exps != null && other.exps.length() > 0) {
				LOG.info(" Compare FALSE :  " + 17);
				return false;
			}
		} else if (!exps.equals(other.exps)) {
			LOG.info(" Compare FALSE :  " + 18);
			return false;
		}
		if (flexp == null) {
			if (other.flexp != null && other.flexp.length() > 0) {
				LOG.info(" Compare FALSE :  " + 19);
				return false;
			}
		} else if (!flexp.equals(other.flexp)) {
			LOG.info(" Compare FALSE :  " + 20);
			return false;
		}
		if (flimp == null) {
			if (other.flimp != null && other.flimp.length() > 0) {
				LOG.info(" Compare FALSE :  " + 21);
				return false;
			}
		} else if (!flimp.equals(other.flimp)) {
			LOG.info(" Compare FALSE :  " + 22);
			return false;
		}
		if (flsez == null) {
			if (other.flsez != null && other.flsez.length() > 0) {
				LOG.info(" Compare FALSE :  " + 23);
				return false;
			}
		} else if (!flsez.equals(other.flsez)) {
			LOG.info(" Compare FALSE :  " + 24);
			return false;
		}
		if (flsezrez == null) {
			if (other.flsezrez != null && other.flsezrez.length() > 0) {
				LOG.info(" Compare FALSE :  " + 25);
				return false;
			}
		} else if (!flsezrez.equals(other.flsezrez)) {
			LOG.info(" Compare FALSE :  " + 26);
			return false;
		}
		if (forms == null) {
			if (other.forms != null && other.forms.length() > 0) {
				LOG.info(" Compare FALSE :  " + 27);
				return false;
			}
		} else if (!forms.equals(other.forms)) {
			LOG.info(" Compare FALSE :  " + 28);
			return false;
		}
		if (importer == null) {
			if (other.importer != null && other.importer.length() > 0) {
				LOG.info(" Compare FALSE :  " + 29);
				return false;
			}
		} else if (!importer.equals(other.importer)) {
			LOG.info(" Compare FALSE :  " + 30);
			return false;
		}
		if (koldoplist == null) {
			if (other.koldoplist != null && other.koldoplist.length() > 0) {
				LOG.info(" Compare FALSE :  " + 31);
				return false;
			}
		} else if (!koldoplist.equals(other.koldoplist)) {
			LOG.info(" Compare FALSE :  " + 32);
			return false;
		}
		if (kontrp == null) {
			if (other.kontrp != null && other.kontrp.length() > 0) {
				LOG.info(" Compare FALSE :  " + 33);
				return false;
			}
		} else if (!kontrp.equals(other.kontrp)) {
			LOG.info(" Compare FALSE :  " + 34);
			return false;
		}
		if (kontrs == null) {
			if (other.kontrs != null && other.kontrs.length() > 0) {
				LOG.info(" Compare FALSE :  " + 36);
				return false;
			}
		} else if (!kontrs.equals(other.kontrs)) {
			LOG.info(" Compare FALSE :  " + 37);
			return false;
		}
		if (marshrut == null) {
			if (other.marshrut != null && other.marshrut.length() > 0) {
				LOG.info(" Compare FALSE :  " + 38);
				return false;
			}
		} else if (!marshrut.equals(other.marshrut)) {
			LOG.info(" Compare FALSE :  " + 39 + " " + marshrut + "  |  "
					+ other.getMarshrut());
			return false;
		}
		if (nblanka == null) {
			if (other.nblanka != null && other.nblanka.length() > 0) {
				LOG.info(" Compare FALSE :  " + 40);
				return false;
			}
		} else if (!nblanka.equals(other.nblanka)) {
			LOG.info(" Compare FALSE :  " + 41);
			return false;
		}
		if (nomercert == null) {
			if (other.nomercert != null && other.nomercert.length() > 0) {
				LOG.info(" Compare FALSE :  " + 42);
				return false;
			}
		} else if (!nomercert.equals(other.nomercert)) {
			LOG.info(" Compare FALSE :  " + 43);
			return false;
		}
		if (otd_id != other.otd_id) {
			LOG.info(" Compare FALSE :  " + 44);
			return false;
		}
		if (otmetka == null) {
			if (other.otmetka != null && other.otmetka.length() > 0) {
				LOG.info(" Compare FALSE :  " + 45);
				return false;
			}
		} else if (!otmetka.equals(other.otmetka)) {
			LOG.info(" Compare FALSE :  " + 46 + " " + otmetka + "  |  "
					+ other.getOtmetka());
			return false;
		}
		
		if (poluchat == null) {
			if (other.poluchat != null && other.poluchat.length() > 0) {
				LOG.info(" Compare FALSE :  " + 47);
				return false;
			}
		} else if (!poluchat.equals(other.poluchat)) {
			LOG.info(" Compare FALSE :  " + 48);
			return false;
		}
		if (rukovod == null) {
			if (other.rukovod != null && other.rukovod.length() > 0) {
				LOG.info(" Compare FALSE :  " + 49);
				return false;
			}
		} else if (!rukovod.equals(other.rukovod)) {
			LOG.info(" Compare FALSE :  " + 50);
			return false;
		}
		if (sez == null) {
			if (other.sez != null && other.sez.length() > 0) {
				LOG.info(" Compare FALSE :  " + 51);
				return false;
			}
		} else if (!sez.equals(other.sez)) {
			LOG.info(" Compare FALSE :  " + 52);
			return false;
		}
		if (status == null) {
			if (other.status != null && other.status.length() > 0) {
				LOG.info(" Compare FALSE :  " + 53);
				return false;
			}
		} else if (!status.equals(other.status)) {
			LOG.info(" Compare FALSE :  " + 54);
			return false;
		}
		if (stranap == null) {
			if (other.stranap != null && other.stranap.length() > 0) {
				LOG.info(" Compare FALSE :  " + 55);
				return false;
			}
		} else if (!stranap.equals(other.stranap)) {
			LOG.info(" Compare FALSE :  " + 56);
			return false;
		}
		if (stranapr == null) {
			if (other.stranapr != null && other.stranapr.length() > 0) {
				LOG.info(" Compare FALSE :  " + 57);
				return false;
			}
		} else if (!stranapr.equals(other.stranapr)) {
			LOG.info(" Compare FALSE :  " + 58);
			return false;
		}
		if (stranav == null) {
			if (other.stranav != null && other.stranav.length() > 0) {
				LOG.info(" Compare FALSE :  " + 59);
				return false;
			}
		} else if (!stranav.equals(other.stranav)) {
			LOG.info(" Compare FALSE :  " + 60);
			return false;
		}
		if (transport == null) {
			if (other.transport != null && other.transport.length() > 0) {
				LOG.info(" Compare FALSE :  " + 61);
				return false;
			}
		} else if (!transport.equals(other.transport)) {
			LOG.info(" Compare FALSE :  " + 62);
			return false;
		}
		if (unn == null) {
			if (other.unn != null && other.unn.length() > 0) {
				LOG.info(" Compare FALSE :  " + 63);
				return false;
			}
		} else if (!unn.equals(other.unn)) {
			LOG.info(" Compare FALSE :  " + 64);
			return false;
		}
		if (unnexp == null) {
			if (other.unnexp != null && other.unnexp.length() > 0) {
				LOG.info(" Compare FALSE :  " + 65);
				return false;
			}
		} else if (!unnexp.equals(other.unnexp)) {
			LOG.info(" Compare FALSE :  " + 66);
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adress == null) ? 0 : adress.hashCode());
		result = prime * result
				+ ((adressimp == null) ? 0 : adressimp.hashCode());
		result = prime * result
				+ ((adresspol == null) ? 0 : adresspol.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((codestranap == null) ? 0 : codestranap.hashCode());
		result = prime * result
				+ ((codestranapr == null) ? 0 : codestranapr.hashCode());
		result = prime * result
				+ ((codestranav == null) ? 0 : codestranav.hashCode());
		result = prime * result
				+ ((datacert == null) ? 0 : datacert.hashCode());
		result = prime * result
				+ ((expadress == null) ? 0 : expadress.hashCode());
		result = prime * result + ((expert == null) ? 0 : expert.hashCode());
		result = prime * result + ((expp == null) ? 0 : expp.hashCode());
		result = prime * result + ((exps == null) ? 0 : exps.hashCode());
		result = prime * result + ((flexp == null) ? 0 : flexp.hashCode());
		result = prime * result + ((flimp == null) ? 0 : flimp.hashCode());
		result = prime * result + ((flsez == null) ? 0 : flsez.hashCode());
		result = prime * result
				+ ((flsezrez == null) ? 0 : flsezrez.hashCode());
		result = prime * result + ((forms == null) ? 0 : forms.hashCode());
		result = prime * result
				+ ((importer == null) ? 0 : importer.hashCode());
		result = prime * result
				+ ((koldoplist == null) ? 0 : koldoplist.hashCode());
		result = prime * result + ((kontrp == null) ? 0 : kontrp.hashCode());
		result = prime * result + ((kontrs == null) ? 0 : kontrs.hashCode());
		result = prime * result
				+ ((marshrut == null) ? 0 : marshrut.hashCode());
		result = prime * result + ((nblanka == null) ? 0 : nblanka.hashCode());
		result = prime * result
				+ ((nomercert == null) ? 0 : nomercert.hashCode());
		result = prime * result + ((otmetka == null) ? 0 : otmetka.hashCode());
		result = prime * result
				+ ((parentnumber == null) ? 0 : parentnumber.hashCode());
		result = prime * result
				+ ((parentstatus == null) ? 0 : parentstatus.hashCode());
		result = prime * result
				+ ((poluchat == null) ? 0 : poluchat.hashCode());
		result = prime * result + ((rukovod == null) ? 0 : rukovod.hashCode());
		result = prime * result + ((sez == null) ? 0 : sez.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stranap == null) ? 0 : stranap.hashCode());
		result = prime * result
				+ ((stranapr == null) ? 0 : stranapr.hashCode());
		result = prime * result + ((stranav == null) ? 0 : stranav.hashCode());
		result = prime * result
				+ ((transport == null) ? 0 : transport.hashCode());
		result = prime * result + ((unn == null) ? 0 : unn.hashCode());
		result = prime * result + ((unnexp == null) ? 0 : unnexp.hashCode());
		return result;
	}

	public boolean equals_new(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Certificate other = (Certificate) obj;
		if (adress == null) {
			if (other.adress != null)
				return false;
		} else if (!adress.equals(other.adress))
			return false;
		if (adressimp == null) {
			if (other.adressimp != null)
				return false;
		} else if (!adressimp.equals(other.adressimp))
			return false;
		if (adresspol == null) {
			if (other.adresspol != null)
				return false;
		} else if (!adresspol.equals(other.adresspol))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (codestranap == null) {
			if (other.codestranap != null)
				return false;
		} else if (!codestranap.equals(other.codestranap))
			return false;
		if (codestranapr == null) {
			if (other.codestranapr != null)
				return false;
		} else if (!codestranapr.equals(other.codestranapr))
			return false;
		if (codestranav == null) {
			if (other.codestranav != null)
				return false;
		} else if (!codestranav.equals(other.codestranav))
			return false;
		if (datacert == null) {
			if (other.datacert != null)
				return false;
		} else if (!datacert.equals(other.datacert))
			return false;
		if (expadress == null) {
			if (other.expadress != null)
				return false;
		} else if (!expadress.equals(other.expadress))
			return false;
		if (expert == null) {
			if (other.expert != null)
				return false;
		} else if (!expert.equals(other.expert))
			return false;
		if (expp == null) {
			if (other.expp != null)
				return false;
		} else if (!expp.equals(other.expp))
			return false;
		if (exps == null) {
			if (other.exps != null)
				return false;
		} else if (!exps.equals(other.exps))
			return false;
		if (flexp == null) {
			if (other.flexp != null)
				return false;
		} else if (!flexp.equals(other.flexp))
			return false;
		if (flimp == null) {
			if (other.flimp != null)
				return false;
		} else if (!flimp.equals(other.flimp))
			return false;
		if (flsez == null) {
			if (other.flsez != null)
				return false;
		} else if (!flsez.equals(other.flsez))
			return false;
		if (flsezrez == null) {
			if (other.flsezrez != null)
				return false;
		} else if (!flsezrez.equals(other.flsezrez))
			return false;
		if (forms == null) {
			if (other.forms != null)
				return false;
		} else if (!forms.equals(other.forms))
			return false;
		if (importer == null) {
			if (other.importer != null)
				return false;
		} else if (!importer.equals(other.importer))
			return false;
		if (koldoplist == null) {
			if (other.koldoplist != null)
				return false;
		} else if (!koldoplist.equals(other.koldoplist))
			return false;
		if (kontrp == null) {
			if (other.kontrp != null)
				return false;
		} else if (!kontrp.equals(other.kontrp))
			return false;
		if (kontrs == null) {
			if (other.kontrs != null)
				return false;
		} else if (!kontrs.equals(other.kontrs))
			return false;
		if (marshrut == null) {
			if (other.marshrut != null)
				return false;
		} else if (!marshrut.equals(other.marshrut))
			return false;
		if (nblanka == null) {
			if (other.nblanka != null)
				return false;
		} else if (!nblanka.equals(other.nblanka))
			return false;
		if (nomercert == null) {
			if (other.nomercert != null)
				return false;
		} else if (!nomercert.equals(other.nomercert))
			return false;
		if (otmetka == null) {
			if (other.otmetka != null)
				return false;
		} else if (!otmetka.equals(other.otmetka))
			return false;
		if (parentnumber == null) {
			if (other.parentnumber != null)
				return false;
		} else if (!parentnumber.equals(other.parentnumber))
			return false;
		if (parentstatus == null) {
			if (other.parentstatus != null)
				return false;
		} else if (!parentstatus.equals(other.parentstatus))
			return false;
		if (poluchat == null) {
			if (other.poluchat != null)
				return false;
		} else if (!poluchat.equals(other.poluchat))
			return false;
		if (rukovod == null) {
			if (other.rukovod != null)
				return false;
		} else if (!rukovod.equals(other.rukovod))
			return false;
		if (sez == null) {
			if (other.sez != null)
				return false;
		} else if (!sez.equals(other.sez))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (stranap == null) {
			if (other.stranap != null)
				return false;
		} else if (!stranap.equals(other.stranap))
			return false;
		if (stranapr == null) {
			if (other.stranapr != null)
				return false;
		} else if (!stranapr.equals(other.stranapr))
			return false;
		if (stranav == null) {
			if (other.stranav != null)
				return false;
		} else if (!stranav.equals(other.stranav))
			return false;
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
			return false;
		if (unn == null) {
			if (other.unn != null)
				return false;
		} else if (!unn.equals(other.unn))
			return false;
		if (unnexp == null) {
			if (other.unnexp != null)
				return false;
		} else if (!unnexp.equals(other.unnexp))
			return false;
		LOG.info("---------------------- > Finished certificate comparing! < -------------------------");
		return true;
	}

	@Override
	public String toString() {
		return "Certificate [cert_id=" + cert_id + ", forms=" + forms
				+ ", unn=" + unn + ", kontrp=" + kontrp + ", kontrs=" + kontrs
				+ ", adress=" + adress + ", poluchat=" + poluchat
				+ ", adresspol=" + adresspol + ", datacert=" + datacert
				+ ", nomercert=" + nomercert + ", expert=" + expert
				+ ", nblanka=" + nblanka + ", rukovod=" + rukovod
				+ ", transport=" + transport + ", marshrut=" + marshrut
				+ ", otmetka=" + otmetka + ", stranav=" + stranav
				+ ", stranapr=" + stranapr + ", status=" + status
				+ ", koldoplist=" + koldoplist + ", flexp=" + flexp
				+ ", unnexp=" + unnexp + ", expp=" + expp + ", exps=" + exps
				+ ", expadress=" + expadress + ", flimp=" + flimp
				+ ", importer=" + importer + ", adressimp=" + adressimp
				+ ", flsez=" + flsez + ", sez=" + sez + ", flsezrez="
				+ flsezrez + ", stranap=" + stranap + ", otd_id=" + otd_id
				+ ", otd_name=" + otd_name + ", parentnumber=" + parentnumber
				+ ", parentstatus=" + parentstatus + ", codestranav=" + codestranav 
				+ ", codestranapr="	+ codestranapr + ", codestranap=" + codestranap + ", category="
				+ category + "]";
	}
	
}
