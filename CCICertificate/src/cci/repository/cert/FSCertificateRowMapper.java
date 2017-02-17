package cci.repository.cert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import cci.model.cert.fscert.Branch;
import cci.model.cert.fscert.FSCertificate;

public class FSCertificateRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FSCertificate cert = new FSCertificate(); 
		cert.setId(rs.getLong("ID"));
		cert.setCertnumber(rs.getString("CERTNUMBER"));
		cert.setParentnumber(rs.getString("PARENTNUMBER"));
		cert.setDateissue((new SimpleDateFormat("MM.dd.yyyyrs")).format(rs.getDate("dateissue")));
		cert.setDateexpiry((new SimpleDateFormat("MM.dd.yyyyrs")).format(rs.getDate("dateexpiry")));
		cert.setConfirmation(rs.getString("confirmation"));
		cert.setDeclaration(rs.getString("declaration"));
		
		if (rs.getLong("ID_BRANCH") != 0) {
		   Branch br = new Branch();
		   br.setId(rs.getLong("ID_BRANCH"));
		   cert.setBranch(br);	
		}

		return cert;
	}

}
