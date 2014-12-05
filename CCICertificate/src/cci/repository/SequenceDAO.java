package cci.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class SequenceDAO {

	private NamedParameterJdbcTemplate template;
	
	// ---------------------------------------------------------------
	// get pool. return start pooling
	// ---------------------------------------------------------------
	public long getNextValuePool(String seq_name, int poolsize)
			throws Exception {

		String sql = "select value from c_sequence WHERE name = '" + seq_name
				+ "'";
		long vl = template.getJdbcOperations().queryForInt(sql);

		sql = "update c_sequence SET " + " value = value + :poolsize"
				+ " WHERE name = :seq_name";

		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"poolsize", Integer.valueOf(poolsize)).addValue("seq_name",
				seq_name);
		template.update(sql, parameters);

		return vl;
	}
}
