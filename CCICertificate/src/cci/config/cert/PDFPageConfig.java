package cci.config.cert;

import java.util.ArrayList;
import java.util.List;


public class PDFPageConfig {
	private String name;
    private String color;
    private float height;
    private float width;
    private List<BoxConfig> textboxes;
    private List<BoxConfig> boxes;
    private List<TableConfig> tables;
    private List<Stamp> stamps;
    private List<BoxConfig> outputs;
    private List<ImageBox> images;
    private String nextPage;
	
     public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public List<BoxConfig> getTextboxes() {
		return textboxes;
	}
	public void setTextboxes(List<BoxConfig> textboxes) {
		this.textboxes = textboxes;
	}
	public List<BoxConfig> getBoxes() {
		return boxes;
	}
	public void setBoxes(List<BoxConfig> boxes) {
		this.boxes = boxes;
	}
	public void addTable(TableConfig table) {
		if (tables != null) {
			tables.add(table);
		}
	}
	public List<TableConfig> getTables() {
		return tables;
	}
	public void setTables(List<TableConfig> tables) {
		this.tables = tables;
	}
	
	public List<Stamp> getStamps() {
		return stamps;
	}
	public void setStamps(List<Stamp> stamps) {
		this.stamps = stamps;
	}
	public List<BoxConfig> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<BoxConfig> outputs) {
		this.outputs = outputs;
	}
	public String getNextPage() {
		return nextPage;
	}
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}
	public List<ImageBox> getImages() {
		return images;
	}
	public void setImages(List<ImageBox> images) {
		this.images = images;
	}
	
	public void addImage(ImageBox image) {
		if (images == null) {
			images = new ArrayList<ImageBox>();
		} 
		images.add(image);
	}
	     
}
