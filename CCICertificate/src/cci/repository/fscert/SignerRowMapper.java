package cci.repository.fscert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import cci.model.Department;
import cci.model.fscert.Branch;
import cci.model.fscert.Expert;
import cci.model.fscert.Exporter;
import cci.model.fscert.FSCertificate;
import cci.model.fscert.Producer;
import cci.model.fscert.Signer;

public class SignerRowMapper<T> implements RowMapper {

	private String dateformat = "dd.MM.yyyy";
	
	public Signer mapRow(ResultSet rs, int rowNum) throws SQLException {
		        Signer employee = new Signer(); 
		        employee.setId(rs.getLong("ID"));
		        employee.setName(rs.getString("NAME"));
		        employee.setJob(rs.getString("JOB"));
		        employee.setFirstname(rs.getString("FIRSTNAME"));
		        employee.setLastname(rs.getString("LASTNAME"));
		        employee.setMiddlename(rs.getString("MIDDLENAME"));
		        employee.setEmail(rs.getString("EMAIL"));
		        employee.setPhone(rs.getString("PHONE"));
		        employee.setVersion(rs.getLong("VERSION"));
		        employee.setBday(rs.getDate("BDAY") == null 
		        		? null : (new SimpleDateFormat(dateformat)).format(rs.getDate("BDAY")));
		        employee.setActive(rs.getString("ACTIVE").charAt(0));
		        
				if (rs.getLong("ID_DEPARTMENT") != 0) {
				   Department dep = new Department();
				   dep.setId(rs.getLong("ID_DEPARTMENT"));
				   dep.setName(rs.getString("DEPARTMENTNAME"));
				   dep.setCode(rs.getString("DEPARTMENTCODE"));
				   dep.setId_otd(rs.getLong("ID_OTD"));
				   dep.setCode_otd(rs.getString("CODE_OTD"));
				   employee.setDepartment(dep);	
				} else {
				   employee.setDepartment(null);	
				}
				
				return employee;
			}
}
