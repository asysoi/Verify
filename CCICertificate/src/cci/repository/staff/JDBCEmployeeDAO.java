package cci.repository.staff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cci.model.Client;
import cci.model.Department;
import cci.model.Employee;
import cci.model.fscert.FSCertificate;
import cci.repository.SQLBuilder;
import cci.repository.client.JDBCClientDAO;
import cci.repository.fscert.FSCertificateRowMapper;
import cci.service.SQLQueryUnit;
import cci.web.controller.staff.ViewEmployee;

@Repository
public class JDBCEmployeeDAO implements EmployeeDAO {

	public static Logger LOG=Logger.getLogger(JDBCEmployeeDAO.class);
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	// ---------------------------------------------------------------
	// Get the page of clients
	// ---------------------------------------------------------------
	public List<ViewEmployee> findViewNextPage(int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {

		SQLQueryUnit filter = builder.getSQLUnitWhereClause();
	    Map<String, Object> params = filter.getParams();
	    LOG.info("Client SQLQueryUnit : " + filter);
	    
		String sql = " SELECT employee.* "
				+ " FROM (SELECT t.*, ROW_NUMBER() OVER " + " (ORDER BY t."
				+ orderby + " " + order + ", t.ID " + order + ") rw "
				+ " FROM EMPLOYEE_VIEW t " + filter.getClause() + " )"
				+ " employee " + " WHERE employee.rw > " + ((page - 1) * pagesize)
				+ " AND employee.rw <= " + (page * pagesize);

		LOG.info("Next clients page : " + sql);

	    return this.template.query(sql,	params, 
	    		new BeanPropertyRowMapper<ViewEmployee>(ViewEmployee.class));
		
	}

	// ---------------------------------------------------------------
	// Get count clients in the list 
	// ---------------------------------------------------------------
	public int getViewPageCount(SQLBuilder builder) {
		long start = System.currentTimeMillis();
		
        SQLQueryUnit qunit = builder.getSQLUnitWhereClause();
		String sql = "SELECT count(*) FROM EMPLOYEE_VIEW "
				+ qunit.getClause();

		Integer count = this.template.queryForObject(sql, qunit.getParams(), Integer.class);
		
		LOG.info(sql);
		return count.intValue();
	}

	// ---------------------------------------------------------------
	// Get list of CCI departments  
	// ---------------------------------------------------------------
	public Map<String, Department> getDepartmentsList() throws Exception{
		String sql = "SELECT * FROM CCI_DEPARTMENT Order by id_otd";

        return template.query(sql, new ResultSetExtractor<Map<String, Department>>(){
			
		    public Map<String, Department> extractData(ResultSet rs) throws SQLException,DataAccessException {
		        HashMap<String,Department> mapRet= new HashMap<String,Department>();
		        while(rs.next()){
		        	Department department  = new Department();
		        	department.setId(rs.getLong("id"));
		        	department.setName(rs.getString("name"));
		        	department.setCode(rs.getString("code"));
		        	Long parentid = rs.getLong("id_parent");
		        	department.setId_otd(rs.getLong("id_otd"));
		        	
		        	if (parentid != null) {
			        	Department parent = new Department();
			        	parent.setId(parentid);
			        	department.setParent(parent);
		        	} else {
		        		department.setParent(null);
		        	}
		            mapRet.put(Integer.toString(rs.getInt("id")), department);
		        }
		        
		        for (Map.Entry<String, Department> dep : mapRet.entrySet()) {
		        	if (dep.getValue().getParent() != null) {
		        		String idparent = Long.toString(dep.getValue().getParent().getId());
		        		
		        		if (mapRet.containsKey(idparent)) {
		        			dep.getValue().setParent(mapRet.get(idparent));
		        		}
		        	}
		        }
		        return mapRet;
		    }
		});	
     }

	// ---------------------------------------------------------------
	// Get employee by ID  
	// ---------------------------------------------------------------
	public Employee findEmployeeByID(Long id) throws Exception {
		String sql = "select * from EMPLOYEE_VIEW WHERE id = ?";
		
		Employee employee = template.getJdbcOperations()
				.queryForObject(
						sql,
						new Object[] { id },
						new EmployeeRowMapper<Employee>());
		
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
