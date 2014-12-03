package cci.config.purchase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExportPurchaseConfig {
	
	private String[] headernames = new String[] { "Дата совершения сделки",
			"Товар", "Продавец", "Покупатель", "Характеристика товара", "Цена",
			"Количество", "Единица измерения" };

	private String[] fieldnames = new String[] { "PCHDATE", "PRODUCT", "COMPANY",
			"DEPARTMENT", "PRODUCTPROPERTY", "PRICE", "VOLUME", "UNIT" };
	
	private Map<String, String> headermap = new LinkedHashMap<String, String>();
	
	private String[] headers = new String[] {};
	private String[] fields = new String[] {};

	public ExportPurchaseConfig() {

		for (int i = 0; i < fieldnames.length; i++) {
			headermap.put(fieldnames[i], headernames[i]);
		}

		headers = headernames;
		fields = fieldnames;
	}

	public String[] getHeadernames() {
		return headernames;
	}

	public void setHeadernames(String[] headernames) {
		this.headernames = headernames;
	}

	public String[] getFieldnames() {
		return fieldnames;
	}

	public void setFieldnames(String[] fieldnames) {
		this.fieldnames = fieldnames;
	}

	public Map<String, String> getHeadermap() {
		return headermap;
	}

	public void setHeadermap(Map<String, String> headermap) {
		this.headermap = headermap;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
		List<String> list = new ArrayList<String>();

		for (String key : fields) {
			list.add(headermap.get(key));
		}

		this.setHeaders((String[]) list.toArray(new String[list.size()]));
	}
}
