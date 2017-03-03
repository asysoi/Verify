package cci.model;

public class Department {
	private Long id;
	private String name;
	private String code;
	private Department parent;
	private Long id_otd;

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

	public Long getId_otd() {
		return id_otd;
	}

	public void setId_otd(Long id_otd) {
		this.id_otd = id_otd;
	}

	

}
