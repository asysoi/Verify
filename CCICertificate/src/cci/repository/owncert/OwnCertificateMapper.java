package cci.repository.owncert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import cci.model.Company;
import cci.model.OwnCertificate;


public class OwnCertificateMapper implements RowMapper {
	
	 public OwnCertificate mapRow(ResultSet resultSet, int row) throws SQLException {
		    OwnCertificate cert = new OwnCertificate();
		    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		    SimpleDateFormat time = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		    
		    cert.setId(resultSet.getInt("id"));
		    cert.setId_beltpp(resultSet.getInt("id_beltpp"));
		    cert.setType(resultSet.getString("type"));
			cert.setNumber(resultSet.getString("number"));
			cert.setBlanknumber(resultSet.getString("blanknumber"));
			cert.setCustomername(resultSet.getString("customername"));
			cert.setCustomeraddress(resultSet.getString("customeraddress"));
			cert.setCustomerunp(resultSet.getString("customerunp"));
			cert.setFactoryaddress(resultSet.getString("factoryaddress"));
			cert.setBranches(resultSet.getString("branches"));
			cert.setAdditionallists(resultSet.getString("additionallists"));
			cert.setDatestart(formatter.format(resultSet.getDate("datestart")));
			cert.setDateexpire(formatter.format(resultSet.getDate("dateexpire")));
			cert.setExpert(resultSet.getString("expert"));
			cert.setSigner(resultSet.getString("signer"));
			cert.setSignerjob(resultSet.getString("signerjob"));
			cert.setDatecert(formatter.format(resultSet.getDate("datecert")));
			cert.setDateload(time.format(resultSet.getTimestamp("dateload")));	
			
            Company beltpp = new Company();
			beltpp.setName(resultSet.getString("beltppname"));
			beltpp.setAddress(resultSet.getString("beltppaddress"));
			beltpp.setUnp(resultSet.getString("beltppunp"));
			cert.setBeltpp(beltpp);
			
		    return cert;
		  }
}


