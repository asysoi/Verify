package cci.web.controller;

public class HeaderTableView {
	private int position; 
	private String dbfield;
	private int width;
	private String name;
	private String link;
	private boolean selected;
	private String selection;

	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getDbfield() {
		return dbfield;
	}
	public void setDbfield(String dbfield) {
		this.dbfield = dbfield;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}
	
	@Override
	public String toString() {
		return "HeaderTableView [width=" + width + ", name=" + name + ", link="
				+ link + "]";
	}
}
