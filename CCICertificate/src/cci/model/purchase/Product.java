package cci.model.purchase;

public class Product {
	private long id;
	private String name;
	private Long id_parent;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId_parent() {
		return id_parent;
	}
	public void setId_parent(Long id_parent) {
		this.id_parent = id_parent;
	}
}
