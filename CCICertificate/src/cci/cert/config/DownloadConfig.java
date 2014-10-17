package cci.cert.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class DownloadConfig {
	static final Logger LOG = Logger.getLogger(DownloadConfig.class);

	private String[] headernames = new String[] { "Форма сертификата", "УНП",
			"Экспортер.Полное наименование", "Экспортер. Краткое наименование",
			"Адрес экспортера", "Импортер", "Адрес импортера",
			"Дата сертификата", "Номер сертификата", "Эксперт", "Номер бланка",
			"Руководитель", "Средства транспорта", "Маршрут",
			"Для служебных отметок", "СТрана выдачи", "Страна предоставления",
			"Статус", "Количество доп.листов", "FLEXP", "УНП Грузоотправителя",
			"Грузотправитель. Полное наименование",
			"Грузоотправитель. Сокращенное наименование",
			"Адрес грузоотправителя", "FLIMP", "Грузополучатель",
			"Адрес грузополучателя", "Флаг СЭЗ", "СЭЗ", "FLSEZREZ", "STRANAP",
			"Отделение БелТПП", "Номер замененного сертификата",
			"Статус замененного сертификата", "Товарные позиции" };

	private String[] fieldnames = new String[] { "FORMS", "UNN", "KONTRP",
			"KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT",
			"NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD", "TRANSPORT",
			"MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR", "STATUS",
			"KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS", "EXPADRESS",
			"FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ", "FLSEZREZ",
			"STRANAP", "OTD_NAME", "PARENTNUMBER", "PARENTSTATUS", "TOVAR" };
	
	private Map<String, String> headermap = new LinkedHashMap<String, String>();
	private String[] headers = new String[]{};
	private String[] fields = new String[]{};
	
	public DownloadConfig() {
		 
		for(int i = 0; i < fieldnames.length; i++) {
			headermap.put(fieldnames[i], headernames[i]);
		}
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
