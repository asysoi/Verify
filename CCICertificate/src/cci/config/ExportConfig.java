package cci.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

public interface ExportConfig {

	public String[] getHeadernames();
	public void setHeadernames(String[] headernames);
	public String[] getFieldnames();
	public void setFieldnames(String[] fieldnames);
	public Map<String, String> getHeadermap();
	public void setHeadermap(Map<String, String> headermap);
	public String[] getHeaders();
	public void setHeaders(String[] headers);
	public String[] getFields();
	public void setFields(String[] fields);
	public String[] addField(String field);
	public String[] addHeader(String header);
	public String[] removeField(String field);
	public String[] removeHeader(String header);

}
