package cci.service.cert;

import java.lang.reflect.Method;


import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cci.service.FieldType;
import cci.service.Filter;
import cci.service.FilterCondition;
import cci.web.controller.cert.ViewCertificate;
import cci.web.controller.cert.ViewCertCondition;
import cci.web.controller.client.ClientController;

@Component
@Scope("session")
public class CertFilter extends Filter {
	
	// public static Logger LOG=LogManager.getLogger(CertFilter.class);
	public static Logger LOG=Logger.getLogger(CertFilter.class);
	
	public CertFilter() {
		String[] fields = new String[] { "CERT_ID", "FORMS", "UNN", "KONTRP",
				"KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT",
				"ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD",
				"TRANSPORT", "MARSHRUT", "OTMETKA", "CODESTRANAV", "CODESTRANAPR",
				"STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS",
				"EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ",
				"FLSEZREZ", "STRANAP", "OTD_ID", "OTD_NAME", "PARENTNUMBER",
				"PARENTSTATUS", "TOVAR", "DENORM", "KRITER", "SCHET", "DATEFROM", "DATETO", 
				"DOPLISTFROM", "DOPLISTTO", "STR_OTD_ID" };
		
		String[] dbfields = new String[] { "CERT_ID", "FORMS", "UNN", "KONTRP",
				"KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT",
				"ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD",
				"TRANSPORT", "MARSHRUT", "OTMETKA", "CODESTRANAV", "CODESTRANAPR",
				"STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPORTER", "EXPS",
				"EXPADRESS", "FLIMP", "IMPORTERFULL", "ADRESSIMP", "FLSEZ", "SEZ",
				"FLSEZREZ", "STRANAP", "OTD_ID", "OTD_NAME", "PARENTNUMBER",
				"PARENTSTATUS", "TOVAR", "DENORM", "KRITER", "SCHET", "ISSUEDATE", "ISSUEDATE", 
				"KOLDOPLIST", "KOLDOPLIST", "OTD_ID"};
		
		FieldType[] types = new FieldType[] {
				FieldType.ID, FieldType.STRING, FieldType.STRING,FieldType.STRING,FieldType.STRING,FieldType.STRING,FieldType.STRING,
				FieldType.STRING,FieldType.DATE,FieldType.DATE, FieldType.STRING, FieldType.STRING, FieldType.STRING, 
				FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.NUMBER, FieldType.STRING,FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.NUMBER,FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.STRING, FieldType.DATE, FieldType.DATE, 
				FieldType.NUMBER, FieldType.NUMBER, FieldType.NUMBER};
		
		
		this.init(fields, dbfields, types);
	}
		
	// -----------------------------------------------
	//  Return result of conversation list of conditions to ViewCertificate in order using for View goals  
	// -----------------------------------------------
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
					LOG.info("Error get certificate." + ex.getMessage());
				}
			}
		}
		return cert;
	}
	

	// -----------------------------------------------
    //  Return list of condition operators filled in ViewCertCondition   
	// -----------------------------------------------
	public ViewCertCondition getCondition() {
		ViewCertCondition cond = new ViewCertCondition();
		
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
    				LOG.info("Error get condition." + ex.getMessage());
				}
			}
		}
		return cond;
	}
	
	
	// ------------------------------------------------------------
	// Convert viewCertificate into list of conditions by setting FilterCondition value property 
	// ------------------------------------------------------------
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
					LOG.info("Error certificate load." + ex.getMessage());
				}
			}
		}
	}

	
	// ------------------------------------------------------------
	// Set operators of conditions into storage variable of filter  
	// ------------------------------------------------------------
    public void loadCondition (ViewCertCondition cond) {
    	
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
					LOG.info("Error condition load." + ex.getMessage());
				}
			}
		}
		
	}
}
