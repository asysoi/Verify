package cci.service.staff;

import java.util.List;

import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cci.model.Client;
import cci.model.Department;
import cci.model.Employee;
import cci.repository.SQLBuilder;
import cci.repository.staff.EmployeeDAO;
import cci.web.controller.staff.ViewEmployee;

@Component
public class EmployeeService {
    private static final Logger LOG=Logger.getLogger(EmployeeService.class);
    
	private Map<String, Department> departments = null;
	
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
	public List<ViewEmployee> readEmployeesPage(int page, int pagesize, String orderby, String order,
			SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<ViewEmployee> employees = null;

		try {
			employees = employeeDAO.findViewNextPage(page, pagesize, orderby, order,
					builder);
		} catch (Exception ex) {
			LOG.info(ex.getMessage());
		}
		return employees;
	}
	
	// ------------------------------------------------------------------------------
	//  This method returns a current page of the certificate's list
	// ------------------------------------------------------------------------------
	public Map<String, Department> getDepartmentsList() {
			if (departments == null) {
				Locale.setDefault(new Locale("en", "en"));
				try {
					departments = employeeDAO.getDepartmentsList();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return departments;
	}
		
	// ------------------------------------------------------------------------------
	//  This method read employee record for editing
	// ------------------------------------------------------------------------------
	public Employee readEmployee(Long id) throws Exception{
		Locale.setDefault(new Locale("en", "en"));
		Employee employee = null;
		try {
			employee = employeeDAO.findEmployeeByID(id);
			LOG.info(employee);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return employee;
	}
	

	// ------------------------------------------------------------------------------
	//  This method add employee into DB
	// ------------------------------------------------------------------------------
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Employee saveEmployee(Employee employee) {

		return null;
	}

	// ------------------------------------------------------------------------------
	//  This method update employee in DB
	// ------------------------------------------------------------------------------
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Employee updateEmployee(Employee employee) {
		Locale.setDefault(new Locale("en", "en"));
        Employee remployee = null;
		try {
			remployee = employeeDAO.updateEmployee(employee);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
		return remployee;
	}

	
	

	
	
	
	public Employee readEmployeeView(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List readEmployees(String orderby, String order, SQLBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
