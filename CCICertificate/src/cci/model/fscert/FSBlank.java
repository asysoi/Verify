package cci.model.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.springframework.stereotype.Component;

@XmlRootElement(name = "blank")
@XmlType(propOrder = { "page", "blanknumber" })
@Component

public class FSBlank {
	private Long id;
    private Long id_fscert;
	private Integer page;
	private String blanknumber;
	
	public String getBlanknumber() {
		return blanknumber;
	}

	public void setBlanknumber(String blanknumber) {
		this.blanknumber = blanknumber;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	@XmlTransient
	public Long getId_fscert() {
		return id_fscert;
	}

	public void setId_fscert(Long id_fscert) {
		this.id_fscert = id_fscert;
	}
	
	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Blank [id=" + id + ", id_fscert=" + id_fscert + ", page=" + page + ", blanknumber=" + blanknumber + "]";
	}
}
