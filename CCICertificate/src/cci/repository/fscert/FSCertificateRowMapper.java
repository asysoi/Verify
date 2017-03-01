package cci.repository.fscert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import cci.model.fscert.Branch;
import cci.model.fscert.Expert;
import cci.model.fscert.Exporter;
import cci.model.fscert.FSCertificate;
import cci.model.fscert.Producer;
import cci.model.fscert.Signer;

public class FSCertificateRowMapper implements RowMapper {
    private String dateformat = "dd.MM.yyyy";
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        FSCertificate cert = new FSCertificate(); 
		cert.setId(rs.getLong("ID"));
		cert.setCertnumber(rs.getString("CERTNUMBER"));
		cert.setParentnumber(rs.getString("PARENTNUMBER"));
		cert.setDateissue((new SimpleDateFormat(dateformat)).format(rs.getDate("dateissue")));
		cert.setDateexpiry((new SimpleDateFormat(dateformat)).format(rs.getDate("dateexpiry")));
		cert.setConfirmation(rs.getString("confirmation"));
		cert.setDeclaration(rs.getString("declaration"));
		cert.setCodecountrytarget(rs.getString("codecountrytarget"));
		cert.setDatecert((new SimpleDateFormat(dateformat)).format(rs.getDate("datecert")));
		cert.setListscount(Integer.valueOf(rs.getInt("LISTSCOUNT")));
		cert.setOtd_id(rs.getInt("OTD_ID"));
		
		if (rs.getLong("ID_BRANCH") != 0) {
		   Branch obj = new Branch();
		   obj.setId(rs.getLong("ID_BRANCH"));
		   cert.setBranch(obj);	
		}

		if (rs.getLong("ID_EXPORTER") != 0) {
			Exporter obj = new Exporter();
			obj.setId(rs.getLong("ID_EXPORTER"));
			cert.setExporter(obj);	
		}
		
		if (rs.getLong("ID_PRODUCER") != 0) {
			Producer obj = new Producer();
			obj.setId(rs.getLong("ID_PRODUCER"));
			cert.setProducer(obj);	
		}
		
		if (rs.getLong("ID_EXPERT") != 0) {
			Expert obj = new Expert();
			obj.setId(rs.getLong("ID_EXPERT"));
			cert.setExpert(obj);	
		}
		
		if (rs.getLong("ID_SIGNER") != 0) {
			Signer obj = new Signer();
			obj.setId(rs.getLong("ID_SIGNER"));
			cert.setSigner(obj);	
		}
		
		return cert;
	}

}
