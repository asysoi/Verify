package cci.config.cert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExportCertConfig {
	
	private String[] headernames = new String[] { "Форма сертификата", "УНП",
			"Грузоотправитель.Полное наименование", "Грузоотправитель.Краткое наименование",
			"Адрес грузоотправителя", "Грузополучатель", "Адрес грузополучателя",
			"Дата сертификата", "Номер сертификата", "Эксперт", "Номер бланка",
			"Заявитель", "Средства транспорта", "Маршрут",
			"Для служебных отметок", "Страна выдачи", "Страна предоставления",
			"Статус", "Количество доп.листов", "УНП Экспортера",
			"Экспортер. Полное наименование",
			"Экспортер. Сокращенное наименование",
			"Адрес экспортера", "Импортер",
			"Адрес импортера", "Флаг СЭЗ", "СЭЗ", "Флаг резидента СЭЗ", "Страна происхождения",
			"Отделение БелТПП", "Номер замененного сертификата",
			"Статус замененного сертификата", "Свод товарных позиций" };

	private String[] fieldnames = new String[] { "FORMS", "UNN", "KONTRP",
			"KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT",
			"NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD", "TRANSPORT",
			"MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR", "STATUS",
			"KOLDOPLIST", "UNNEXP", "EXPP", "EXPS", "EXPADRESS",
			"IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ", "FLSEZREZ",
			"STRANAP", "OTD_NAME", "PARENTNUMBER", "PARENTSTATUS", "TOVAR" };
	
	private Map<String, String> headermap = new LinkedHashMap<String, String>();
	private String[] headers = new String[]{};
	private String[] fields = new String[]{};
	
	public ExportCertConfig() {
		 
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
