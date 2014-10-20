package cci.cert.config;

import java.util.List;

public abstract class PDFConfigReader {
	private PDFPageConfig pconfig;

	public abstract List<BoxConfig> getListConfigTextBoxes(String pagename);

	public abstract List<BoxConfig> getListConfigBoxes(String pagename);

	public abstract List<TableConfig> getListConfigTables(String pagename);

	public abstract List<Stamp> getListConfigStamps(String pagename);

	public abstract List<BoxConfig> getListConfigOutputs(String pagename);
	
	public abstract List<ImageBox> getListConfigImages(String pagename);

	public abstract String getPropertyByName(String pagename, String propertyName);
	
	
	public PDFPageConfig getPDFPageConfig(String pagename) {
		if (pconfig == null) {
			pconfig = new PDFPageConfig();
		}

		// load page properties
		pconfig.setTextboxes(getListConfigTextBoxes(pagename));
		pconfig.setBoxes(getListConfigBoxes(pagename));
		pconfig.setTables(getListConfigTables(pagename));
		pconfig.setStamps(getListConfigStamps(pagename));
		pconfig.setOutputs(getListConfigOutputs(pagename));
		pconfig.setImages(getListConfigImages(pagename));

		// load page attributes
		pconfig.setName(pagename);
		pconfig.setNextPage(getPropertyByName(pagename, "nextpage"));
		pconfig.setColor(getPropertyByName(pagename, "color"));
		pconfig.setHeight(Float.parseFloat(getPropertyByName(pagename, "height")));
		pconfig.setWidth(Float.parseFloat(getPropertyByName(pagename, "width")));

		return pconfig;
	}
}
