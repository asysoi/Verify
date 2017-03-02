package cci.model;

public class Department {
	private Long id;
	private String name;
	private String code;
	private Department parent;
	private Long otd_id;

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

	public Long getOtd_id() {
		return otd_id;
	}

	public void setOtd_id(Long otd_id) {
		this.otd_id = otd_id;
	}

}
