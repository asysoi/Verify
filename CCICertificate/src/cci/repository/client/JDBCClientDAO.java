package cci.repository.client;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import cci.model.purchase.Company;
import cci.repository.SQLBuilder;

@Repository
public class JDBCClientDAO implements ClientDAO {
	
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

    public List<Company> findViewNextPage(int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {

			String sql = " SELECT client.* "
					+ " FROM (SELECT t.*, ROW_NUMBER() OVER " + " (ORDER BY t."
					+ orderby + " " + order + ", t.ID " + order + ") rw "
					+ " FROM CLIENT_VIEW t " + builder.getWhereClause() + " )"
					+ " client " + " WHERE cert.rw > " + ((page - 1) * pagesize)
					+ " AND cert.rw <= " + (page * pagesize);

			System.out.println("Next page : " + sql);
			
			return this.template.getJdbcOperations().query(sql,
					new BeanPropertyRowMapper<Company>(Company.class));	
	}

	public int getViewPageCount(SQLBuilder builder) {
		long start = System.currentTimeMillis();

		String sql = "SELECT count(*) FROM CLIENT_VIEW "
				+ builder.getWhereClause();

		int count = this.template.getJdbcOperations().queryForInt(sql);

		System.out.println(sql);
		System.out.println("Query time: "
				+ (System.currentTimeMillis() - start));

		return count;
	}

}
