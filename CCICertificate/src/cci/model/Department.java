package cci.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Component;

@XmlRootElement(name = "department")
@Component
public class Department {
	private Long id;
	private String name;
	private String code;
	private Department parent;
	private Long id_otd;

	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	@XmlTransient
	public Long getId_otd() {
		return id_otd;
	}

	public void setId_otd(Long id_otd) {
		this.id_otd = id_otd;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", code=" + code + ", parent=" + parent + ", id_otd="
				+ id_otd + "]";
	}
}
