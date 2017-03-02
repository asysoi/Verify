package cci.repository.staff;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cci.repository.SQLBuilder;
import cci.repository.client.JDBCClientDAO;
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
}
