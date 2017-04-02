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
			 String key = rs.getString("KEY");
			 String locale = rs.getString("LOCALE");
			 String text = rs.getString("TEXT");
			 
			 Map row;
			 if (fstranslate.containsKey(key)) {
		        row = (Map) fstranslate.get(key);
			 } else {
				row = new HashMap(); 
			 }
		     row.put(locale, text);
		     
		     fstranslate.put(key, row);
		}
		return fstranslate;
	}

}
