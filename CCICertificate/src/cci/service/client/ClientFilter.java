package cci.service.client;

import java.lang.reflect.Method;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


import org.apache.log4j.Logger;

import cci.service.FieldType;
import cci.service.Filter;
import cci.service.FilterCondition;
import cci.web.controller.client.ViewClientCondition;
import cci.web.controller.client.ViewClient;

public class ClientFilter extends Filter {

	public static Logger LOG = Logger.getLogger(ClientFilter.class);

	public ClientFilter() {
		String[] fields = new String[] { "ID", "NAME", "CITY", "STREET",
				"CINDEX", "OFFICE", "BUILDING", "PHONE", "CELL",
				"UNP", "OKPO", "BNAME", "BCITY", "BSTREET", "BINDEX", "BOFFICE",
				"BBUILDING", "ACCOUNT", "BUNP", "EMAIL", "BEMAIL", "CODECOUNTRY", "BCODECOUNTRY" };

		String[] dbfields = new String[] {"ID", "NAME", "CITY", "STREET",
				"CINDEX", "OFFICE", "BUILDING", "PHONE", "CELL",
				"UNP", "OKPO", "BNAME", "BCITY", "BSTREET", "BINDEX", "BOFFICE",
				"BBUILDING", "ACCOUNT", "BUNP", "EMAIL", "BEMAIL", "CODECOUNTRY", "BCODECOUNTRY" };

		FieldType[] types = new FieldType[] { FieldType.ID, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.STRING};

		this.init(fields, dbfields, types);
	}

	public ViewClient getViewclient() {
		ViewClient obj = new ViewClient();

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

	public ViewClientCondition getCondition() {
		ViewClientCondition obj = new ViewClientCondition();

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

	public void loadViewclient(ViewClient obj) {

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

	public void loadCondition(ViewClientCondition obj) {

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
