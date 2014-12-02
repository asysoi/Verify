package cci.service.purchase;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cci.service.FieldType;
import cci.service.Filter;
import cci.service.FilterCondition;
import cci.web.controller.purchase.ViewPurchase;
import cci.web.controller.purchase.ViewPurchaseCondition;

public class PurchaseFilter extends Filter {
	public static Logger LOG = LogManager.getLogger(PurchaseFilter.class);

	public PurchaseFilter() {
		String[] fields = new String[] { "ID", "product", "company", "department", "unit", "productproperty"
				  , "pchdatefrom", "pchdateto", "pricefrom", "priceto", "volumefrom", "volumeto", 
					"id_product","id_otd", "id_company"};

		String[] dbfields = new String[] {"ID", "product", "company", "department", "unit", "productproperty"
				  , "pchdate", "pchdate", "price", "price", "volume", "volume", "id_product","id_otd", "id_company"};

		FieldType[] types = new FieldType[] { FieldType.ID, FieldType.STRING, FieldType.STRING, FieldType.STRING,
				FieldType.STRING, FieldType.STRING, FieldType.DATE, FieldType.DATE, FieldType.NUMBER, FieldType.NUMBER,
				FieldType.NUMBER, FieldType.NUMBER, FieldType.NUMBER, FieldType.NUMBER, FieldType.NUMBER };

		this.init(fields, dbfields, types);
	}

	public ViewPurchase getViewpurchase() {
		ViewPurchase obj = new ViewPurchase();

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

	public ViewPurchaseCondition getCondition() {
		ViewPurchaseCondition obj = new ViewPurchaseCondition();

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

	public void loadViewpurchase(ViewPurchase obj) {

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

	public void loadCondition(ViewPurchaseCondition obj) {

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
