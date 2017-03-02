package cci.service.staff;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cci.model.Employee;
import cci.repository.SQLBuilder;
import cci.repository.staff.EmployeeDAO;
import cci.web.controller.client.ViewClient;
import cci.web.controller.fscert.ViewFSCertificate;
import cci.web.controller.staff.ViewEmployee;

public class EmployeeService {
    private static final Logger LOG=Logger.getLogger(EmployeeService.class);
    
	@Autowired
	private EmployeeDAO employeeDAO;

	
	// ------------------------------------------------------------------------------
	//  This method returns count of pages in certificate list 
	// ------------------------------------------------------------------------------
	public int getViewPageCount(SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));
	
		int counter = 0;
		try {
			counter = employeeDAO.getViewPageCount(builder);
		} catch (Exception ex) {
			LOG.info(ex.getMessage());
		}
		return counter;
	}

	// ------------------------------------------------------------------------------
	//  This method returns a current page of the certificate's list
	// ------------------------------------------------------------------------------
	public List<ViewFSCertificate> readCertificatesPage(String[] fields, int page, int pagesize, int pagecount,
			String orderby, String order, SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<ViewFSCertificate> certs = null;

		try {
			certs = employeeDAO.findViewNextPage(fields, page, pagesize, pagecount, orderby,
					order, builder);
		} catch (Exception ex) {
			LOG.info(ex.getMessage());
		}
		return certs;
	}

	
	
	public List<ViewClient> readClientsPage(int page, int pagesize, String orderby, String order, SQLBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ViewEmployee> readEmployeesPage(int page, int pagesize, String orderby, String order,
			SQLBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}

	public Employee readEmployeeView(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		
	}

	public void updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		
	}

	public Employee readEmployee(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List readEmployees(String orderby, String order, SQLBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}

}
