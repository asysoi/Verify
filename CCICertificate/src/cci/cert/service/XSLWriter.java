package cci.cert.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import cci.cert.model.Certificate;
import cci.cert.web.controller.ViewCertificate;
import cci.purchase.service.FilterCondition;

public class XSLWriter {
	//static final Logger LOG = Logger.getLogger(FilterCertificate.class);

	public Workbook getWorkbook(List<Certificate> certs, String[] headers, String[] dbfields) {
		long start = System.currentTimeMillis();
		
		Workbook workbook = new SXSSFWorkbook();
		// Workbook workbook = new HSSFWorkbook();

		// Create a blank sheet
		Sheet sheet = workbook.createSheet("Лист Сертификатов");
		createRow(sheet, 0, headers);
		

		int i = 1;
		for (Certificate cert : certs) {
			createRow(sheet, i++, getData(cert, dbfields));
		}
		long end = System.currentTimeMillis();
		System.out.println("Duration: " + (end - start));

		return workbook;
	}
	
	private void createRow(Sheet sheet, int rownum, Object[] data) {
		Row row = sheet.createRow(rownum);
		Cell cell;
		int cellnum = 0;
		//System.out.println("Row: " + rownum);

		for (Object obj : data) {
			cell = row.createCell(cellnum++);
			if (obj instanceof String)
				cell.setCellValue((String) obj);
			else if (obj instanceof Integer)
				cell.setCellValue((Integer) obj);
		}
	}
	
	private Object[] getData(Certificate cert, String[] dbfields) {
		List<Object> data = new ArrayList<Object>();

		for (String field : dbfields) {
			String getter = convertFieldNameToGetter(field);

			try {
				Method m = getMethod(cert, getter, new Class[] {});
				if (m != null) {
					data.add((String) m.invoke(cert, new Object[] {}));
				}
			} catch (Exception ex) {
				//LOG.info("Error certificate load." + ex.getMessage());
			}
		}

		return data.toArray();
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

		return m;
	}

	private String convertFieldNameToGetter(String field) {
		return "get" + field.substring(0, 1).toUpperCase()
				+ field.substring(1).toLowerCase();
	}

	private String convertFieldNameToSetter(String field) {
		return "set" + field.substring(0, 1).toUpperCase()
				+ field.substring(1).toLowerCase();
	}
}
