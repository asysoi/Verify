package cci.repository.fscert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FSResultSetExtractor<T> implements ResultSetExtractor<FSTranslate> {

	@Override
	public FSTranslate extractData(ResultSet rs) throws SQLException, DataAccessException {
		FSTranslate fstranslate = new FSTranslate();
		
		while (rs.next()) {
		     Map row  = new HashMap();
		     row.put("ru", rs.getString("ru"));
		     row.put("en", rs.getString("en"));
		     fstranslate.put(rs.getString("key"), row);
		}
		return fstranslate;
	}

}