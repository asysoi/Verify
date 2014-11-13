package cci.cert.service;

import java.lang.reflect.Method;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import cci.cert.web.controller.ViewCertificate;
import cci.cert.web.controller.ViewCondition;
import cci.purchase.service.FilterCondition;

@Component
@Scope("session")
public class FilterCertificate extends Filter {
	
	public FilterCertificate() {
		String[] fields = new String[] { "CERT_ID", "FORMS", "UNN", "KONTRP",
				"KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT",
				"ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD",
				"TRANSPORT", "MARSHRUT", "OTMETKA", "CODESTRANAV", "CODESTRANAPR",
				"STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS",
				"EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ",
				"FLSEZREZ", "STRANAP", "OTD_ID", "OTD_NAME", "PARENTNUMBER",
				"PARENTSTATUS", "TOVAR", "DENORM", "KRITER", "SCHET", "DATEFROM", "DATETO" };
		
		String[] dbfields = new String[] { "CERT_ID", "FORMS", "UNN", "KONTRP",
				"KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT",
				"ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD",
				"TRANSPORT", "MARSHRUT", "OTMETKA", "CODESTRANAV", "CODESTRANAPR",
				"STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPORTER", "EXPS",
				"EXPADRESS", "FLIMP", "IMPORTERFULL", "ADRESSIMP", "FLSEZ", "SEZ",
				"FLSEZREZ", "STRANAP", "OTD_ID", "OTD_NAME", "PARENTNUMBER",
				"PARENTSTATUS", "TOVAR", "DENORM", "KRITER", "SCHET", "ISSUEDATE", "ISSUEDATE" };
		
		FieldType[] types = new FieldType[] {
				FieldType.ID, FieldType.STRING, FieldType.STRING,FieldType.STRING,FieldType.STRING,FieldType.STRING,FieldType.STRING,
				FieldType.STRING,FieldType.DATE,FieldType.DATE, FieldType.STRING, FieldType.STRING, FieldType.STRING, 
				FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.NUMBER, FieldType.STRING,FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.STRING, FieldType.DATE, FieldType.DATE};
		
		
		this.init(fields, dbfields, types);
	}
	
	public void setFullsearchvalue(String fullsearchvalue) {
		super.setFullsearchvalue(fullsearchvalue);
		
		getConditions().get("DENORM").setOperator("like");
		getConditions().get("DENORM").setValue(fullsearchvalue);
		
	}
	
	private Method getMethod(Object obj, String name, Class[] params) {
		Method m = null;
		try {
			 try {
			    m = obj.getClass().getMethod(name, params);
			 } catch (Exception ex) {
				m = obj.getClass().getSuperclass().getMethod(name, params); 
			 }
			 
		} catch (Exception ex) {
			//LOG.info("Error get nethod: " + ex.getMessage());
		}
         
		//if (m != null) {
		//	System.out.println("Метод: " + m.getName());
		//}
		return m;
	}

	
	public ViewCertificate getViewcertificate() {
		ViewCertificate cert = new ViewCertificate();
		
		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			String setter = convertFieldNameToSetter(field);
			
			if (fcond != null) {
				try {
					Method m = getMethod(cert, setter, new Class[] {String.class});
					if (m != null) {
					    m.invoke(cert, new Object[]{fcond.getValue()});
					}
				} catch (Exception ex) {
					//LOG.info("Error get certificate." + ex.getMessage());
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
					Method m = getMethod(cond, setter, new Class[] {String.class});
					if (m != null) {
					    m.invoke(cond, new Object[]{fcond.getOperator()});
					}
				} catch (Exception ex) {
    				//LOG.info("Error get condition." + ex.getMessage());
				}
			}
		}
		return cond;
	}
	
	
	public void loadViewcertificate (ViewCertificate cert) {
	
		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			
			String getter = convertFieldNameToGetter(field);
			
			if (fcond != null) {
				try {
					Method m = getMethod(cert, getter, new Class[] {});
					if (m != null) {
						fcond.setValue((String) m.invoke(cert, new Object[]{}));
					}
				} catch (Exception ex) {
					//LOG.info("Error certificate load." + ex.getMessage());
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
					Method m = getMethod(cond, getter, new Class[] {});
					if (m != null) {
						fcond.setOperator((String) m.invoke(cond, new Object[]{}));
					}
				} catch (Exception ex) {
					//LOG.info("Error condition load." + ex.getMessage());
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
