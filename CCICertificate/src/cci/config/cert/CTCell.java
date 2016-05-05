package cci.config.cert;

import com.itextpdf.text.pdf.BaseFont;

public class CTCell {
	private String text;
	private int number;
	private String font;
	private BaseFont bf;
	private int fontsize;
	private int align;
	private int border;
	private float borderWidth;
	private int verticalAlign;
	private int colspan;
	private boolean fill;
	

	
	public BaseFont getBf() {
		return bf;
	}

	public void setBf(BaseFont bf) {
		this.bf = bf;
	}

	public int getColspan() {
		return colspan;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getFontsize() {
		return fontsize;
	}

	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getVerticalAlign() {
		return verticalAlign;
	}

	public float getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(float f) {
		this.borderWidth = f;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public void setVerticalAlign(int verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

}
