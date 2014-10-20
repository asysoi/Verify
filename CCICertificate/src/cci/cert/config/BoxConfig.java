package cci.cert.config;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;

public class BoxConfig {
	private float xl;
	private float yl;
	private float xr;
	private float yr;
	private String font;
	private BaseFont bf;
	private int fontSize;
	private float widthTextLine;
	private int vAlign;
	private int gAlign;
	private int border;
	private float widthBorderLine;
	private float rotation;
	private int runDirection;
	private int arabicOptions;
	private int leading; 
	private float gray;
	private float colorFill; 
	private String text;
	private boolean vertical = false;
	private String map;
	 
			
	public BoxConfig(float xl, float yl, float xr, float yr, BaseFont bf, int fontSize,
			float widthTextLine, int leading, int gAlign, int vAlign, int border,
			float widthBorderLine) {
		super();
		this.xl = xl;
		this.yl = yl;
		this.xr = xr;
		this.yr = yr;
		this.bf = bf;
		this.fontSize = fontSize;
		this.widthTextLine = widthTextLine;
		this.vAlign = vAlign;
		this.gAlign = gAlign;
		this.border = border;
		this.widthBorderLine = widthBorderLine;
		this.leading = leading;
	}
	
	public BoxConfig() {
	}

	public void setRectangle(float xl, float yl, float xr, float yr,float widthTextLine ) {
		this.xl = xl;
		this.yl = yl;
		this.xr = xr;
		this.yr = yr;
		this.widthTextLine = widthTextLine;
	}

	public void setRectangle(float xl, float yl, float xr, float yr) {
		this.xl = xl;
		this.yl = yl;
		this.xr = xr;
		this.yr = yr;
	}

	
	public float getRotation() {
		
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public int getRunDirection() {
		return runDirection;
	}

	public void setRunDirection(int runDirection) {
		this.runDirection = runDirection;
	}

	public int getArabicOptions() {
		return arabicOptions;
	}

	public void setArabicOptions(int arabicOptions) {
		this.arabicOptions = arabicOptions;
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

	public BaseFont getBf() {
		return bf;
	}

	public void setBf(BaseFont bf) {
		this.bf = bf;
	}

	public int getvAlign() {
		return vAlign;
	}

	public void setvAlign(int vAlign) {
		this.vAlign = vAlign;
	}

	public int getgAlign() {
		return gAlign;
	}

	public void setgAlign(int gAlign) {
		this.gAlign = gAlign;
	}

	public float getWidthTextLine() {
		return widthTextLine;
	}

	public void setWidthTextLine(float widthTextLine) {
		this.widthTextLine = widthTextLine;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public float getWidthBorderLine() {
		return widthBorderLine;
	}

	public void setWidthBorderLine(float widthBorderLine) {
		this.widthBorderLine = widthBorderLine;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getLeading() {
		return leading;
	}

	public void setLeading(int leading) {
		this.leading = leading;
	}

	public float getGray() {
		return gray;
	}

	public void setGray(float gray) {
		this.gray = gray;
	}

	public float getColorFill() {
		return colorFill;
	}

	public void setColorFill(float colorFill) {
		this.colorFill = colorFill;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "BoxConfig [xl=" + xl + ", yl=" + yl + ", xr=" + xr + ", yr="
				+ yr + ", fontSize=" + fontSize + ", border=" + border
				+ ", text=" + text + ", map=" + map + "]";
	}

}
