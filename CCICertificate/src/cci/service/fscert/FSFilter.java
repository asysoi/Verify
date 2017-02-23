package cci.service.fscert;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cci.service.FieldType;
import cci.service.Filter;
import cci.service.FilterCondition;
import cci.web.controller.fscert.ViewFSCertificate;
import cci.web.controller.fscert.ViewFSCertificateCondition;


@Component
@Scope("session")
public class FSFilter extends Filter {
	
	public static Logger LOG=Logger.getLogger(FSFilter.class);
	
	public FSFilter() { 

		String[] fields = new String[] {"ID","CERTNUMBER", "PARENTNUMBER", "DATEISSUE", "DATEEXPIRY","CONFIRMATION",
										"DECLARATION","CODECOUNTRYTARGET","DATECERT","LISTSCOUNT", "BRANCHNAME","BRANCHADDRESS", 
										"EXPORTERNAME", "EXPORTERADDRESS", "PRODUCERNAME", "PRODUCERADDRESS",
										"EXPERTNAME", "SIGNERNAME", "PRODUCTNAME", "BLANKNUMBER", "OTD_ID",
										"DATECERTFROM", "DATECERTTO", "COUNTFROM", "COUNTTO", "STR_OTD_ID"};
		
		String[] dbfields = new String[] {"ID", "CERTNUMBER", "PARENTNUMBER", "DATEISSUE", "DATEEXPIRY","CONFIRMATION",
										"DECLARATION","CODECOUNTRYTARGET","DATECERT","LISTSCOUNT","NAME","ADDRESS", 
										"NAME", "ADDRESS", "NAME", "ADDRESS",
										"NAME", "NAME", "TOVAR", "BLANKNUMBER", "OTD_ID", 
										"DATECERT", "DATECERT", "LISTSCOUNT", "LISTSCOUNT", "OTD_ID"};
		
		FieldType[] types = new FieldType[] { FieldType.ID, FieldType.STRING,FieldType.STRING,FieldType.DATE,FieldType.DATE,FieldType.STRING,
										FieldType.STRING, FieldType.STRING, FieldType.DATE,FieldType.NUMBER, FieldType.STRING, FieldType.STRING,  
										FieldType.STRING, FieldType.STRING, FieldType.STRING, FieldType.STRING, 
				 				        FieldType.STRING, FieldType.STRING, FieldType.TEXT,FieldType.STRING, FieldType.NUMBER, 
				 				        FieldType.DATE, FieldType.DATE, FieldType.NUMBER, FieldType.NUMBER, FieldType.NUMBER };
		
		this.init(fields, dbfields, types);
	}
		
	// -------------------------------------------------------------
	//  Return result of conversation list of conditions 
	//  to ViewCertificate in order using for View goals  
	// -------------------------------------------------------------
	public ViewFSCertificate getViewcertificate() {
		ViewFSCertificate cert = new ViewFSCertificate();
		cert.init();
		
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
					LOG.info("Error get view FS certificate."  + ex.getMessage() );
					LOG.info(fcond != null ?  fcond.toString() : " Условие не найдено ");
				}
			}
		}
		return cert;
	}
	

	// -----------------------------------------------------------------------
    //  Return list of condition operators filled in ViewCertCondition   
	// -----------------------------------------------------------------------
	public ViewFSCertificateCondition getCondition() {
		ViewFSCertificateCondition cond = new ViewFSCertificateCondition();
		
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
    				LOG.info("Error get FS view condition." + ex.getMessage());
    				LOG.info(fcond != null ?  fcond.toString() : " Условие не найдено ");
				}
			}
		}
		return cond;
	}
	
	
	// -------------------------------------------------------------------------------------------
	// Convert viewCertificate into list of conditions by setting FilterCondition value property 
	// -------------------------------------------------------------------------------------------
	public void loadViewcertificate (ViewFSCertificate cert) {
	
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
					LOG.info("Error view FS certificate load." + ex.getMessage());
					LOG.info(fcond != null ?  fcond.toString() : " Условие не найдено ");
					
				}
			}
		}
	}

	
	// ------------------------------------------------------------
	// Set operators of conditions into storage variable of filter  
	// ------------------------------------------------------------
    public void loadCondition (ViewFSCertificateCondition cond) {
    	
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
					LOG.info("Error view FS condition load." + ex.getMessage());
					LOG.info(fcond != null ?  fcond.toString() : " Условие не найдено ");
				}
			}
		}
	}
}

