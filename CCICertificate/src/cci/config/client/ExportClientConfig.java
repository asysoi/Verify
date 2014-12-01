package cci.config.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExportClientConfig {
	private String[] headernames = new String[] { "Наименование компании", "Cтрана", "Город", "Улица"
			, "Индекс", "Номер офиса", "Номер дома", "Телефон", "Мобильный телефон", "Email", "УНП"
			, "ОКПО", "Наименование банка", "Страна банка", "Город Банка", "Улица Банка", "Индекс Банка"
			, "Номер офиса Банка", "Номер дома Банка", "Расчетный счет", "УНП Банка"
			, "Email Банка" };

	private String[] fieldnames = new String[] { "name", "country", "city", "line", "cindex",
			"office","building","work_phone","cell_phone","email","unp", "okpo", "bname", "bcountry", 
			"bcity","bline","bindex","boffice","bbuilding", "account","bunp", "bemail" 
			};
	
	private Map<String, String> headermap = new LinkedHashMap<String, String>();
	private String[] headers = new String[]{};
	private String[] fields = new String[]{};
	
	public ExportClientConfig() {
		 
		for(int i = 0; i < fieldnames.length; i++) {
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
		
		for (String key: fields) {
			list.add(headermap.get(key));
		}
		
		this.setHeaders((String[]) list.toArray(new String[list.size()]));
	}
}
