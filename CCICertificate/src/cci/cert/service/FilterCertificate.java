package cci.cert.service;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cci.cert.model.Certificate;
import cci.cert.web.controller.ViewCondition;
import cci.purchase.service.FilterCondition;

@Component
@Scope("session")
public class FilterCertificate extends Filter {
	static final Logger LOG = Logger.getLogger(FilterCertificate.class);
	
	public FilterCertificate() {
		String[] fields = new String[] { "CERT_ID", "FORMS", "UNN", "KONTRP",
				"KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT",
				"ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD",
				"TRANSPORT", "MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR",
				"STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS",
				"EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ",
				"FLSEZREZ", "STRANAP", "OTD_ID", "OTD_NAME", "PARENTNUMBER",
				"PARENTSTATUS", "TOVAR", "DENORM" };
		
		this.init(fields);
		
	}
	
	
	public void setFullsearchvalue(String fullsearchvalue) {
		super.setFullsearchvalue(fullsearchvalue);
		
		getConditions().get("DENORM").setOperator("like");
		getConditions().get("DENORM").setValue(fullsearchvalue);
		
	}
	
	public Certificate getCertificate() {
		Certificate cert = new Certificate();
		
		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			String setter = convertFieldNameToSetter(field);
			
			if (fcond != null) {
				try {
					Method m = cert.getClass().getDeclaredMethod(setter, new Class[] {String.class});
					m.invoke(cert, new Object[]{fcond.getValue()});
					//System.out.println("Обработано: " +  field);
				} catch (Exception ex) {
					LOG.info("Error get certificate." + ex.getMessage());
					//ex.printStackTrace();
					//System.out.println("Error get certificate." + ex.getMessage());
				}
			}
		}
		return cert;
	}
	

	
	public ViewCondition getCondition() {
		ViewCondition cond = new ViewCondition();
		
		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			String setter = convertFieldNameToSetter(field);
			
			if (fcond != null) {
				try {
					Method m = cond.getClass().getDeclaredMethod(setter, new Class[] {String.class});
					m.invoke(cond, new Object[]{fcond.getOperator()});
					//System.out.println("Обработано: " +  field);
				} catch (Exception ex) {
					//ex.printStackTrace();
					LOG.info("Error get condition." + ex.getMessage());
					//System.out.println("Error get condition." + ex.getMessage());
				}
			}
		}
		return cond;
	}
	
	
	public void loadCertificate (Certificate cert) {
	
		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			
			String getter = convertFieldNameToGetter(field);
			
			if (fcond != null) {
				try {
					Method m = cert.getClass().getDeclaredMethod(getter, new Class[] {});
					fcond.setValue((String) m.invoke(cert, new Object[]{}));
					//System.out.println("Обработано: " +  field + " Установлено значение: " + fcond.getValue());					
				} catch (Exception ex) {
					LOG.info("Error certificate load." + ex.getMessage());
					//ex.printStackTrace();
					//System.out.println("Error certificate load." + ex.getMessage());
				}
			}
		}
	}

    public void loadCondition (ViewCondition cond) {
    	
		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			String getter = convertFieldNameToGetter(field);
			
			if (fcond != null) {
				try {
					Method m = cond.getClass().getDeclaredMethod(getter, new Class[] {});
					fcond.setOperator((String) m.invoke(cond, new Object[]{}));
					
					//System.out.println("Обработано: " +  field + " Установлено значение оператора: " + fcond.getOperator());					
				} catch (Exception ex) {
					LOG.info("Error condition load." + ex.getMessage());
					//ex.printStackTrace();
					//System.out.println("Error condition load." + ex.getMessage());
				}
			}
		}
		
	}
 

    private String convertFieldNameToGetter(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1).toLowerCase();
	}


	private String convertFieldNameToSetter(String field) {
		return "set" + field.substring(0, 1).toUpperCase() + field.substring(1).toLowerCase();
	}

    
}
