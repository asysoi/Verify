package cci.cert.certificate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CCIProperty {
	private Map<String, Object> pmap = new HashMap<String, Object>();
	private Properties props = new Properties();     	
    private static CCIProperty repository;
    
    public static CCIProperty getInstance() {
    	if (repository == null) {
    		repository = new CCIProperty();
    	}
    	return repository;
    }
    
    @Deprecated
    public void put(String key, Object value) {
    	pmap.put(key, value);
    }
    
    @Deprecated
    public Object get(String key) {
    	return pmap.get(key);
    }
    
    @Deprecated
    public Map<String, Object> getMap() {
    	return pmap;
    }
    
    public void setProperties(Properties pr) {
    	props = pr;
    }
    
    public Properties getProperties() {
    	return props;
    }
    
    public void setProperty(String key, String value) {
    	props.setProperty(key, value);
    }

    public String getProperty(String key) {
    	return  props.getProperty(key);
    }
}
