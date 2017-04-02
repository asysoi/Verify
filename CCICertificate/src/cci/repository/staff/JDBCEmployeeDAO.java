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
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cci.model.Client;
import cci.model.ClientLocale;
import cci.model.Department;
import cci.model.Employee;
import cci.model.EmployeeLocale;
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
		
		if  (employee != null) {
			sql = "select * from CCI_EMPLOYEE_LOCALE WHERE IDEMPLOYEE = ? ORDER BY LOCALE";
			List<EmployeeLocale> locales = template.getJdbcOperations().query(sql, new Object[] { employee.getId() },
					new BeanPropertyRowMapper<EmployeeLocale>(EmployeeLocale.class));
			if (locales != null && locales.size() > 0) {
			   int lid = 0;	
			   for (EmployeeLocale locale : locales) {
				   locale.setId(lid++);
			   }
			   employee.setLocales(locales);
			}
		}
		return employee;
	}

	
	// ---------------------------------------------------------------
	// Update existing employee  
	// ---------------------------------------------------------------
	public Employee updateEmployee(Employee employee) throws Exception{
		String sql = "update cci_employee set "
				+ "name = :name, job = :job, firstname = :firstname,"
				+ "middlename = :middlename, lastname=:lastname, "
				+ "phone=:phone, email=:email, bday=TO_DATE(:bday,'DD.MM.YY'), "
				+ "id_department = :department.id, version = :version + 1  WHERE id = :id";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(employee);
		
		try {
			int row = template.update(sql, parameters);
			
			if (row > 0) {
				
				template.getJdbcOperations().update(
						"delete from CCI_EMPLOYEE_LOCALE where idemployee = ?",
						employee.getId());
				
				sql = "insert into CCI_EMPLOYEE_LOCALE(idemployee, locale, name, job) values ("
						+ employee.getId() + ", :locale, :name, :job)";
				
				LOG.info("Employee locales: " + employee.getLocales());
				
				if (employee.getLocales() != null && employee.getLocales().size() > 0) {
					SqlParameterSource[] batch = SqlParameterSourceUtils
							.createBatch(employee.getLocales().toArray());
					int[] updateCounts = template.batchUpdate(sql, batch);
				}
				
				employee.setVersion(employee.getVersion() + 1);
				
			} else {
				throw new RuntimeException("Не удалось изменить сотрудника " + employee.getName() + " по неизвестной причине.");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return employee;
	}

	// ---------------------------------------------------------------
	// Save new employee  
	// ---------------------------------------------------------------
	public Employee saveEmployee(Employee employee) throws Exception {
        LOG.info("\n\nEmployee save starting............................");
        long id = 0;
		String sql = "insert into cci_employee( "
				+ "name, job, firstname, middlename, lastname, "
				+ "phone, email, bday, id_department) "   
				+ "values ( :name, :job, :firstname, :middlename, :lastname, "
				+ ":phone, :email, TO_DATE(:bday,'DD.MM.YY'), :department.id) ";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(employee);
		// template.update(sql, parameters);
		
		try {

			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			int row = template.update(sql, parameters, keyHolder,
					new String[] { "id" });
			id = keyHolder.getKey().intValue();

			if (row > 0) {
				sql = "insert into CCI_EMPLOYEE_LOCALE(idemployee, locale, name, job) values ("
						+ id + ",:locale, :name, :job)";
				
				if (employee.getLocales() != null && employee.getLocales().size() > 0) {
					SqlParameterSource[] batch = SqlParameterSourceUtils
							.createBatch(employee.getLocales().toArray());
					int[] updateCounts = template.batchUpdate(sql, batch);
				}
				
				employee.setId(id);
			}
			employee.setVersion(employee.getVersion() + 1);
			
		} catch (Exception ex) {
			LOG.info("Error - client save: " + ex.toString());
		}
		
		return employee;
	}
	
	
	
}
