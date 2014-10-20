package cci.cert.config;

import java.util.ArrayList;
import java.util.List;

public class TableConfig {
	private float xl;
	private float yl;
	private float xr;
	private float yr;
	private float height;
	private float width;
	private int colnumber;
	private List<Integer> colwidths;
	private List<List<CTCell>> header;
	private List<CTCell> bodyRow;
	private List<CTCell> footerRow;
	private List<Float> heightrows;
	private float workHeight;
	
	public List<CTCell> getFooterRow() {
		return footerRow;
	}

	public void setFooterRow(List<CTCell> footerrow) {
		this.footerRow = footerrow;
	}

	public List<List<CTCell>> getHeader() {
		return header;
	}

	public void setHeader(List<List<CTCell>> header) {
		this.header = header;
	}

	public List<CTCell> getBodyRow() {
		return bodyRow;
	}

	public void setBodyRow(List<CTCell> bodyrow) {
		this.bodyRow = bodyrow;
	}

	public float getXl() {
		return xl;
	}

	public void setXl(float xl) {
		this.xl = xl;
	}

	public float getYl() {
		return yl;
	}

	public void setYl(float yl) {
		this.yl = yl;
	}

	public float getXr() {
		return xr;
	}

	public void setXr(float xr) {
		this.xr = xr;
	}

	public float getYr() { 
		return yr;
	}

	public void setYr(float yr) {
		this.yr = yr;
	}

	public float getHeight() {
		return yr - yl;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return xr - xl;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public int getColnumber() {
		return colnumber;
	}

	public void setColnumber(int colnumber) {
		this.colnumber = colnumber;
	}

	public List<Integer> getColwidths() {
		return colwidths;
	}

	public void setColwidths(List<Integer> colwidths) {
		this.colwidths = colwidths;
	}

	public void addHeaderRow(List<CTCell> row) {
		if (header == null) {
			header = new ArrayList<List<CTCell>>();
		}
		header.add(row);
	}

	public List<Float> getHeightrows() {
		return heightrows;
	}

	public void setHeightrows(List<Float> heightrows) {
		this.heightrows = heightrows;
	}

	public void addHeightRow(int height) {
		if (heightrows == null) {
			heightrows = new ArrayList<Float>();
		}
		heightrows.add(new Float(height));
	}

	public float getWorkHeight() {
		if (workHeight == 0f) {
			if (heightrows != null) {
				float ht = 0;
				for (int i = 0; i < heightrows.size(); i++) {
					ht += heightrows.get(i);
				}
				workHeight = getHeight() - ht;
			}
		}
		return workHeight;
	}

}
