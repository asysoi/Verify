package cci.repository.staff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import cci.model.Department;
import cci.model.Employee;
import cci.model.fscert.Branch;
import cci.model.fscert.Expert;
import cci.model.fscert.Exporter;
import cci.model.fscert.FSCertificate;
import cci.model.fscert.Producer;
import cci.model.fscert.Signer;

public class EmployeeRowMapper<T> implements RowMapper {

	private String dateformat = "dd.MM.yyyy";
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        Employee employee = new Employee(); 
		        employee.setId(rs.getLong("ID"));
		        employee.setName(rs.getString("NAME"));
		        employee.setJob(rs.getString("JOB"));
		        employee.setRuname(rs.getString("RUNAME"));
		        employee.setRujob(rs.getString("RUJOB"));
		        employee.setEnname(rs.getString("ENNAME"));
		        employee.setEnjob(rs.getString("ENJOB"));
		        employee.setFirstname(rs.getString("FIRSTNAME"));
		        employee.setLastname(rs.getString("LASTNAME"));
		        employee.setMiddlename(rs.getString("MIDDLENAME"));
		        employee.setEmail(rs.getString("EMAIL"));
		        employee.setPhone(rs.getString("PHONE"));
		        employee.setBday((new SimpleDateFormat(dateformat)).format(rs.getDate("BDAY")));
		        employee.setActive(rs.getString("ACTIVE").charAt(0));
		        
				
				if (rs.getLong("ID_DEPARTMENT") != 0) {
				   Department dep = new Department();
				   dep.setId(rs.getLong("ID_DEPARTMENT"));
				   dep.setName(rs.getString("DEPARTMENTNAME"));
				   dep.setCode(rs.getString("DEPARTMENTCODE"));
				   dep.setId_otd(rs.getLong("ID_OTD"));
				   employee.setDepartment(dep);	
				} else {
				   employee.setDepartment(null);	
				}
				
				return employee;
			}

}
