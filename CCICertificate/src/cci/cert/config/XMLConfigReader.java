package cci.cert.config;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.dom4j.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XMLConfigReader extends PDFConfigReader {
	private final String TAG_TARGETS = "targets";
	private final String TAG_TARGET = "target";
	private final String TAG_TYPES = "producttypes";
	private final String TAG_TYPE = "type";
	
	private final String TAG_XL = "xl";
	private final String TAG_YL = "yl";
	private final String TAG_XR = "xr";
	private final String TAG_YR = "yr";
	private final String TAG_ECODE = "ecode";
	private final String TAG_NAME = "name";
	private final String TAG_ENAME = "ename";
	private final String TAG_ID = "id";
	private final String TAG_CODE = "code";
	private final String TAG_COUNTRIES = "countries";
	private final String TAG_JOB = "job";
	private final String TAG_JOBS = "jobs";
	private final String TAG_COUNTRY = "country";
	private final String TAG_OUTPUTS = "outputs";
	private final String TAG_BOXES = "boxes";
	private final String TAG_TEXTBOXES = "textboxes";
	private final String TAG_PAGE = "page";
	private final String TAG_BOX = "box";
	private final String TAG_IMAGES = "images";
	private final String TAG_IMAGEBOX = "image";
	private final String TAG_TABLE = "table";
	private final String TAG_STAMP = "stamp";
	private final String TAG_HEADERROW = "headerrow";
	private final String TAG_FOOTERROW = "footerrow";
	private final String TAG_BODYROW = "bodyrow";
	private static String filename;
	private static PDFConfigReader reader;
	private org.w3c.dom.Document doc;

	public static PDFConfigReader getInstance(String configFileName) {
		if (reader == null) {
			filename = configFileName;
			reader = new XMLConfigReader();
		}
		return reader;
	}

	private XMLConfigReader() {
		super();
		try {
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPropertyByName(String pagename, String propertyName) {
		String ret = "";

		if (doc != null) {
			NodeList pages = doc.getElementsByTagName(TAG_PAGE);

			for (int i = 0; i < pages.getLength(); i++) {
				Node page = pages.item(i);

				if (pagename.equals(findPageName(page))) {
					NamedNodeMap attrs = page.getAttributes();

					if (attrs.getNamedItem(propertyName) != null) {
						ret = attrs.getNamedItem(propertyName).getNodeValue();
					}
				}
			}
		}
		return ret;
	}

	public List<ImageBox> getListConfigImages(String pagename) {
		List<ImageBox> images = new ArrayList<ImageBox>();

		if (doc != null) {
			NodeList pages = doc.getElementsByTagName(TAG_PAGE);

			for (int i = 0; i < pages.getLength(); i++) {
				Node page = pages.item(i);

				if (pagename.equals(findPageName(page))) {
					NodeList childs = page.getChildNodes();

					for (int j = 0; j < childs.getLength(); j++) {
						Node node = childs.item(j);

						if (TAG_IMAGES.equals(node.getNodeName())) {
							NodeList list = node.getChildNodes();

							for (int k = 0; k < list.getLength(); k++) {
								Node bnode = list.item(k);

								if (TAG_IMAGEBOX.equals(bnode.getNodeName())) {
									ImageBox image = getImage(bnode);
									images.add(image);
								}
							}
						}
					}

				}
			}
		}
		return images;
	}

	private ImageBox getImage(Node node) {
		ImageBox box = new ImageBox();
		NodeList tags = node.getChildNodes();

		for (int i = 0; i < tags.getLength(); i++) {
			Node item = tags.item(i);

			if (item.getNodeType() == Element.ELEMENT_NODE) {
				String tagname = item.getNodeName();
				String tagvalue = item.getTextContent();

				if (TAG_XL.equals(tagname)) {
					box.setXl(Float.parseFloat(tagvalue));
				} else if (TAG_YL.equals(tagname)) {
					box.setYl(Float.parseFloat(tagvalue));
				} else if (TAG_XR.equals(tagname)) {
					box.setXr(Float.parseFloat(tagvalue));
				} else if (TAG_YR.equals(tagname)) {
					box.setYr(Float.parseFloat(tagvalue));
				} else if ("border".equals(tagname)) {
					box.setBorder(Integer.parseInt(tagvalue));
				} else if ("widthborderline".equals(tagname)) {
					box.setWidthBorderLine(Float.parseFloat(tagvalue));
				} else if ("filename".equals(tagname)) {
					box.setFilename(tagvalue);
				}
			}
		}
		return box;
	}

	public List<BoxConfig> getListConfigTextBoxes(String pagename) {
		return getListBoxes(pagename, TAG_TEXTBOXES);
	}

	public List<BoxConfig> getListConfigBoxes(String pagename) {
		return getListBoxes(pagename, TAG_BOXES);
	}

	public List<BoxConfig> getListConfigOutputs(String pagename) {
		return getListBoxes(pagename, TAG_OUTPUTS);
	}

	private List<BoxConfig> getListBoxes(String pagename, String listtype) {
		List<BoxConfig> boxes = new ArrayList<BoxConfig>();

		if (doc != null) {
			NodeList pages = doc.getElementsByTagName(TAG_PAGE);

			for (int i = 0; i < pages.getLength(); i++) {
				Node page = pages.item(i);

				if (pagename.equals(findPageName(page))) {
					NodeList childs = page.getChildNodes();

					for (int j = 0; j < childs.getLength(); j++) {
						Node node = childs.item(j);

						if (listtype.equals(node.getNodeName())) {
							NodeList list = node.getChildNodes();

							for (int k = 0; k < list.getLength(); k++) {
								Node bnode = list.item(k);

								if (TAG_BOX.equals(bnode.getNodeName())) {
									BoxConfig box = getConfigBox(bnode);
									boxes.add(box);
								}
							}
						}
					}

				}
			}
		}
		return boxes;
	}

	public List<TableConfig> getListConfigTables(String pagename) {
		List<TableConfig> tables = new ArrayList<TableConfig>();

		if (doc != null) {
			NodeList pages = doc.getElementsByTagName(TAG_PAGE);

			for (int i = 0; i < pages.getLength(); i++) {
				Node page = pages.item(i);

				if (pagename.equals(findPageName(page))) {
					NodeList childs = page.getChildNodes();

					for (int j = 0; j < childs.getLength(); j++) {
						Node node = childs.item(j);

						if (TAG_TABLE.equals(node.getNodeName())) {
							TableConfig table = getConfigTable(node);
							tables.add(table);
						}
					}

				}
			}
		}

		return tables;
	}

	@Override
	public List<Stamp> getListConfigStamps(String pagename) {
		List<Stamp> stamps = new ArrayList<Stamp>();

		if (doc != null) {
			NodeList pages = doc.getElementsByTagName(TAG_PAGE);

			for (int i = 0; i < pages.getLength(); i++) {
				Node page = pages.item(i);

				if (pagename.equals(findPageName(page))) {
					NodeList childs = page.getChildNodes();

					for (int j = 0; j < childs.getLength(); j++) {
						Node node = childs.item(j);

						if (TAG_STAMP.equals(node.getNodeName())) {
							Stamp stamp = getConfigStamp(node);
							stamps.add(stamp);
						}
					}

				}
			}
		}

		return stamps;
	}

	private Stamp getConfigStamp(Node node) {
		Stamp stamp = new Stamp();
		NodeList tags = node.getChildNodes();

		for (int i = 0; i < tags.getLength(); i++) {
			Node item = tags.item(i);

			if (item.getNodeType() == Element.ELEMENT_NODE) {
				String tagname = item.getNodeName();
				String tagvalue = item.getTextContent();

				if ("x".equals(tagname)) {
					stamp.setX(Float.parseFloat(tagvalue));
				} else if ("y".equals(tagname)) {
					stamp.setY(Float.parseFloat(tagvalue));
				} else if ("r".equals(tagname)) {
					stamp.setR(Float.parseFloat(tagvalue));
				}
			}

		}
		return stamp;
	}

	private TableConfig getConfigTable(Node node) {
		TableConfig table = new TableConfig();
		NodeList tags = node.getChildNodes();

		for (int i = 0; i < tags.getLength(); i++) {
			Node item = tags.item(i);

			if (item.getNodeType() == Element.ELEMENT_NODE) {
				String tagname = item.getNodeName();
				String tagvalue = item.getTextContent();

				if (TAG_XL.equals(tagname)) {
					table.setXl(Float.parseFloat(tagvalue));
				} else if (TAG_YL.equals(tagname)) {
					table.setYl(Float.parseFloat(tagvalue));
				} else if (TAG_XR.equals(tagname)) {
					table.setXr(Float.parseFloat(tagvalue));
				} else if (TAG_YR.equals(tagname)) {
					table.setYr(Float.parseFloat(tagvalue));
				} else if ("colnumber".equals(tagname)) {
					table.setColnumber(Integer.parseInt(tagvalue));
				} else if ("colwidth".equals(tagname)) {
					table.setColwidths(getColumnWidths(item));
				} else if (TAG_BODYROW.equals(tagname)) {
					table.setBodyRow(getTableRow(item));
				} else if (TAG_FOOTERROW.equals(tagname)) {
					NamedNodeMap mrow = item.getAttributes();
					table.addHeightRow(Integer.parseInt(mrow.getNamedItem(
							"height").getNodeValue()));
					table.setFooterRow(getTableRow(item));
				} else if (TAG_HEADERROW.equals(tagname)) {
					NamedNodeMap mrow = item.getAttributes();
					table.addHeightRow(Integer.parseInt(mrow.getNamedItem(
							"height").getNodeValue()));
					table.addHeaderRow(getTableRow(item));
				}
			}

		}
		return table;
	}

	private List<CTCell> getTableRow(Node item) {
		List<CTCell> row = new ArrayList<CTCell>();
		NodeList tags = item.getChildNodes();

		for (int i = 0; i < tags.getLength(); i++) {
			Node node = tags.item(i);

			if (node.getNodeType() == Element.ELEMENT_NODE) {
				String tagname = node.getNodeName();
				String tagvalue = node.getTextContent();

				if ("cell".equals(tagname)) {
					CTCell cell = new CTCell();
					NamedNodeMap nmap = node.getAttributes();
					cell.setText(tagvalue);
					cell.setNumber(Integer.parseInt(nmap.getNamedItem("number")
							.getNodeValue()));
					cell.setFont(nmap.getNamedItem("font").getNodeValue());
					cell.setFontsize(Integer.parseInt(nmap
							.getNamedItem("fsize").getNodeValue()));
					cell.setBorder(Integer.parseInt(nmap.getNamedItem("border")
							.getNodeValue()));
					cell.setAlign(convertStringInAlign(nmap.getNamedItem(
							"align").getNodeValue()));
					cell.setVerticalAlign(convertStringInAlign(nmap
							.getNamedItem("valign").getNodeValue()));
					cell.setBorderWidth(Float.parseFloat(nmap.getNamedItem(
							"bwidth").getNodeValue()));
					if (nmap.getNamedItem("colspan") != null) {
						cell.setColspan(Integer.parseInt(nmap.getNamedItem(
								"colspan").getNodeValue()));
					} else {
						cell.setColspan(1);
					}
					cell.setFill(nmap.getNamedItem("colorfill") != null);
					row.add(cell.getNumber(), cell);
				}
			}

		}
		return row;
	}

	private List<Integer> getColumnWidths(Node item) {
		List<Integer> widths = new ArrayList<Integer>();
		NodeList tags = item.getChildNodes();

		for (int i = 0; i < tags.getLength(); i++) {
			Node node = tags.item(i);

			if (node.getNodeType() == Element.ELEMENT_NODE) {
				String tagname = node.getNodeName();
				String tagvalue = node.getTextContent();

				if ("col".equals(tagname)) {
					NamedNodeMap nmap = node.getAttributes();
					widths.add(Integer.parseInt(nmap.getNamedItem("number")
							.getNodeValue()), Integer.parseInt(tagvalue));
				}
			}

		}
		return widths;
	}

	private String findPageName(Node page) {
		String pageName = null;
		NamedNodeMap attrs = page.getAttributes();

		if (attrs.getNamedItem("name") != null) {
			pageName = attrs.getNamedItem("name").getNodeValue();
		}
		return pageName;
	}

	private BoxConfig getConfigBox(Node node) {
		BoxConfig box = new BoxConfig();
		NodeList tags = node.getChildNodes();

		for (int i = 0; i < tags.getLength(); i++) {
			Node item = tags.item(i);

			if (item.getNodeType() == Element.ELEMENT_NODE) {
				String tagname = item.getNodeName();
				String tagvalue = item.getTextContent();

				if (TAG_XL.equals(tagname)) {
					box.setXl(Float.parseFloat(tagvalue));
				} else if (TAG_YL.equals(tagname)) {
					box.setYl(Float.parseFloat(tagvalue));
				} else if (TAG_XR.equals(tagname)) {
					box.setXr(Float.parseFloat(tagvalue));
				} else if (TAG_YR.equals(tagname)) {
					box.setYr(Float.parseFloat(tagvalue));
				} else if ("font".equals(tagname)) {
					try {
						box.setFont(tagvalue);
						BaseFont bf = BaseFont.createFont(
								System.getenv("windir") + "\\fonts\\"
										+ tagvalue + ".TTF",
								BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
						box.setBf(bf);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if ("fontsize".equals(tagname)) {
					box.setFontSize(Integer.parseInt(tagvalue));
				} else if ("widthtextline".equals(tagname)) {
					box.setWidthTextLine(Float.parseFloat(tagvalue));
				} else if ("galign".equals(tagname)) {
					box.setgAlign(convertStringInAlign(tagvalue));
				} else if ("valign".equals(tagname)) {
					box.setvAlign(convertStringInAlign(tagvalue));
				} else if ("border".equals(tagname)) {
					box.setBorder(Integer.parseInt(tagvalue));
				} else if ("widthborderline".equals(tagname)) {
					box.setWidthBorderLine(Float.parseFloat(tagvalue));
				} else if ("leading".equals(tagname)) {
					box.setLeading(Integer.parseInt(tagvalue));
				} else if ("colorfill".equals(tagname)) {
					box.setColorFill(tagvalue.isEmpty() ? 0f : Float
							.parseFloat(tagvalue));
				} else if ("text".equals(tagname)) {
					box.setText(tagvalue);
				} else if ("vertical".equals(tagname)) {
					box.setVertical(tagvalue.isEmpty() ? false : true);
				} else if ("rotation".equals(tagname)) {
					box.setRotation(tagvalue.isEmpty() ? 0f : Float
							.parseFloat(tagvalue));
				} else if ("map".equals(tagname)) {
					box.setMap(tagvalue);
				}
			}
		}
		return box;
	}

	private BaseColor convertToBaseColor(String tagvalue) {
		BaseColor bc;
		if (tagvalue.isEmpty()) {
			bc = BaseColor.WHITE;
		} else {
			bc = new BaseColor(Integer.parseInt(tagvalue));
		}
		return bc;
	}

	private int convertStringInAlign(String tagvalue) {
		if (tagvalue.equals("BASELINE")) {
			return com.itextpdf.text.Element.ALIGN_BASELINE;
		} else if (tagvalue.equals("BOTTOM")) {
			return com.itextpdf.text.Element.ALIGN_BOTTOM;
		} else if (tagvalue.equals("CENTER")) {
			return com.itextpdf.text.Element.ALIGN_CENTER;
		} else if (tagvalue.equals("JUSTIFIED")) {
			return com.itextpdf.text.Element.ALIGN_JUSTIFIED;
		} else if (tagvalue.equals("JUSTIFIED_ALL")) {
			return com.itextpdf.text.Element.ALIGN_JUSTIFIED_ALL;
		} else if (tagvalue.equals("LEFT")) {
			return com.itextpdf.text.Element.ALIGN_LEFT;
		} else if (tagvalue.equals("MIDDLE")) {
			return com.itextpdf.text.Element.ALIGN_MIDDLE;
		} else if (tagvalue.equals("RIGHT")) {
			return com.itextpdf.text.Element.ALIGN_RIGHT;
		} else if (tagvalue.equals("TOP")) {
			return com.itextpdf.text.Element.ALIGN_TOP;
		} else if (tagvalue.equals("UNDEFINED")) {
			return com.itextpdf.text.Element.ALIGN_UNDEFINED;
		}
		return -1;
	}
	
}
