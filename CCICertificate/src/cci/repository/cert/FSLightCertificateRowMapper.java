package cci.repository.cert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FSLightCertificateRowMapper implements ResultSetExtractor {

	@Override
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		StringBuffer str = new StringBuffer();
		str.append("Certificate Number;Certificate Date\n");
		
		while (rs.next()) {
			str.append(rs.getString("certnumber"));
			str.append(";");
			str.append((new SimpleDateFormat("MM.dd.yyyy")).format(rs.getDate("dateissue")));
			str.append("\n");
		}
		return str.toString();
	}

}
