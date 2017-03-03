package cci.service.staff;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import cci.service.FieldType;
import cci.service.Filter;
import cci.service.FilterCondition;
import cci.web.controller.staff.ViewEmployee;
import cci.web.controller.staff.ViewEmployeeConfition;

public class EmployeeFilter extends Filter {
	public static Logger LOG = Logger.getLogger(EmployeeFilter.class);

	public EmployeeFilter() {
		String[] fields = new String[] { "ID", "NAME", "JOB", "DEPARTMENTNAME", "ID_OTD", "ID_DEPARTMENT",  
				"PHONE", "EMAIL", "BDAY", "LASTNAME", "FIRSTNAME", "MIDDLENAME", "BDAYFROM", "BDAYTO"};

		String[] dbfields = new String[] {"ID", "RUNAME", "RUJOB", "NAME", "ID_OTD", "ID_DEPARTMENT", 
				"PHONE", "EMAIL", "BDAY", "LASTNAME", "FIRSTNAME", "MIDDLENAME", "BDAY", "BDAY"};

		FieldType[] types = new FieldType[] { FieldType.ID, FieldType.STRING, FieldType.STRING, FieldType.STRING, FieldType.NUMBER, FieldType.NUMBER,   
		            FieldType.STRING, FieldType.STRING, FieldType.DATE, FieldType.STRING,FieldType.STRING,FieldType.STRING, 
		            FieldType.DATE, FieldType.DATE };

		this.init(fields, dbfields, types);
	}

	public ViewEmployee getViewemployee() {
		ViewEmployee obj = new ViewEmployee();

		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			String setter = convertFieldNameToSetter(field);

			if (fcond != null) {
				try {
					Method m = getMethod(obj, setter,
							new Class[] { String.class });
					if (m != null) {
						m.invoke(obj, new Object[] { fcond.getValue() });
					}
				} catch (Exception ex) {
					LOG.info("Error get certificate." + ex.getMessage());
				}
			}
		}
		return obj;
	}

	public ViewEmployeeConfition getCondition() {
		ViewEmployeeConfition obj = new ViewEmployeeConfition();

		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			String setter = convertFieldNameToSetter(field);

			if (fcond != null) {
				try {
					Method m = getMethod(obj, setter,
							new Class[] { String.class });
					if (m != null) {
						m.invoke(obj, new Object[] { fcond.getOperator() });
					}
				} catch (Exception ex) {
					LOG.info("Error get condition." + ex.getMessage());
				}
			}
		}
		return obj;
	}

	public void loadViewEmployee(ViewEmployee obj) {

		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);

			String getter = convertFieldNameToGetter(field);

			if (fcond != null) {
				try {
					Method m = getMethod(obj, getter, new Class[] {});
					if (m != null) {
						fcond.setValue((String) m.invoke(obj, new Object[] {}));
					}
				} catch (Exception ex) {
					LOG.info("Error certificate load." + ex.getMessage());
				}
			}
		}
	}

	public void loadCondition(ViewEmployeeConfition obj) {

		for (String field : getConditions().keySet()) {
			FilterCondition fcond = getConditions().get(field);
			String getter = convertFieldNameToGetter(field);

			if (fcond != null) {
				try {
					Method m = getMethod(obj, getter, new Class[] {});
					if (m != null) {
						fcond.setOperator((String) m.invoke(obj,
								new Object[] {}));
					}
				} catch (Exception ex) {
					LOG.info("Error condition load." + ex.getMessage());
				}
			}
		}

	}

}
