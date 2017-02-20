package cci.repository.fscert;


import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cci.model.Client;
import cci.model.Employee;
import cci.model.cert.Certificate;
import cci.model.fscert.Branch;
import cci.model.fscert.Expert;
import cci.model.fscert.Exporter;
import cci.model.fscert.FSBlank;
import cci.model.fscert.FSCertificate;
import cci.model.fscert.FSProduct;
import cci.model.fscert.Producer;
import cci.model.fscert.Signer;
import cci.model.owncert.OwnCertificate;
import cci.model.owncert.OwnCertificateExport;
import cci.model.owncert.Product;
import cci.repository.SQLBuilder;
import cci.repository.owncert.OwnCertificateMapper;
import cci.service.SQLQueryUnit;
import cci.web.controller.fscert.FSFilter;
import cci.web.controller.fscert.ViewFSCertificate;

@Repository
public class JDBCFSCertificateDAO implements FSCertificateDAO {

	private static final Logger LOG = Logger.getLogger(JDBCFSCertificateDAO.class);
	
	private NamedParameterJdbcTemplate template;
	private DataSource ds; 

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
		ds = dataSource;
	}
	
	
	// ------------------------------------------------------------------------------------------------------
	//
	//  Methods are applicable for Free Sale Certificate database manipulation GRUD service 
	//
	// ------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------
	//--------------------------------------------------------------------	
	//            ADD new FS Certificate for WEB or REST service request 
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------
	public FSCertificate saveFSCertificate(FSCertificate cert) throws Exception {
			long id = 0;
	 		if (cert.getCertnumber().isEmpty()) {
	 			throw new RuntimeException("Добвляемый в базу сертификат должен иметь номер.");
	 		}
			
			if (cert.getBranch()!= null && cert.getBranch().getId() == 0 ) cert.getBranch().setId(findOrCreateBranchID(cert.getBranch()));
			if (cert.getExporter()!=null && cert.getExporter().getId() == 0 ) cert.getExporter().setId(findOrCreateClientID(cert.getExporter()));
			if (cert.getProducer()!=null && cert.getProducer().getId() == 0 ) cert.getProducer().setId(findOrCreateClientID(cert.getProducer()));
			if (cert.getExpert()!=null && cert.getExpert().getId() == 0 ) cert.getExpert().setId(findOrCreateEmployeeID(cert.getExpert()));
			if (cert.getSigner()!=null && cert.getSigner().getId() == 0 ) cert.getSigner().setId(findOrCreateEmployeeID(cert.getSigner()));
			
			LOG.info(cert);
			
			String sql = "insert into fs_cert(certnumber, parentnumber, dateissue, dateexpiry, confirmation, declaration, codecountrytarget, "
					    + " datecert, id_branch, id_exporter, id_producer, id_expert, id_signer) "
					    + " values (:certnumber, :parentnumber, "
					    + " TO_DATE(:dateissue,'DD.MM.YY'), "
					    + " TO_DATE(:dateexpiry,'DD.MM.YY'), "
					    + " :confirmation, :declaration, :codecountrytarget,  :datecert "
					    + ((cert.getBranch() != null) ?  ", :branch.id " : ", :branch")
					    + ((cert.getExporter() != null) ?  ", :exporter.id " : ", :exporter ")
					    + ((cert.getProducer() != null) ?  ", :producer.id " : ", :producer ")
					    + ((cert.getExpert() != null) ?  ", :expert.id " : ", :expert ")
					    + ((cert.getSigner() != null) ?  ", :signer.id " : ", :signer ")	
					    + ")";
			LOG.info(sql);
			
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);
			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			
			int row = template.update(sql, parameters, keyHolder,
					new String[] { "id" });
			id = keyHolder.getKey().intValue();

			if (row > 0) {
				sql = "insert into fs_product(id_fscert, numerator, tovar) values ("
						+ id + ", :numerator, :tovar)";
				if (cert.getProducts() != null && cert.getProducts().size() > 0) {
					SqlParameterSource[] batch = SqlParameterSourceUtils
							.createBatch(cert.getProducts().toArray());
					int[] updateCounts = template.batchUpdate(sql, batch);
				}
				
				sql = "insert into fs_blank(id_fscert, page, blanknumber) values ("
						+ id + ", :page, :blanknumber)";
				
				if (cert.getBlanks() != null && cert.getBlanks().size() > 0) {
					SqlParameterSource[] batch = SqlParameterSourceUtils
							.createBatch(cert.getBlanks().toArray());
					int[] updateCounts = template.batchUpdate(sql, batch);
				}

				cert.setId(id);
			}
			return cert;
	}

		
	// -------------------------------------------
	// Get id of branch
	// -------------------------------------------
	private long findOrCreateBranchID(Branch branch) throws Exception {
			    String where = createClientWhereClause(branch);
			    
				if (where.isEmpty()) {
					throw new RuntimeException("Отсутствует информация об отделении БелТПП");
				}
					
				long id = 0;
				String sql = "SELECT id FROM fs_branch " + where;
				
				try {
					SqlParameterSource parameters = new BeanPropertySqlParameterSource(branch);
					Branch client = this.template.queryForObject(sql, 
									parameters,
									new BeanPropertyRowMapper<Branch>(Branch.class));
					
					id = client.getId();
					
				} catch (Exception ex) {
					
	                LOG.info(ex.getMessage());
					if (id == 0) {
					  sql = "insert into fs_branch(name, codecountry, cindex, city, line, office, building," + 
							  	"work_phone, cell_phone, email, unp, okpo, account, bname, bindex, bcodecountry," + 
			                    "bcity, bline, boffice, bbuilding, bemail, bunp) " +  
								"values(:name, :codecountry, :cindex, :city, :line, :office, :building," + 
							  	":work_phone, :cell_phone, :email, :unp, :okpo, :account, :bname, :bindex, :bcodecountry," + 
			                    ":bcity, :bline, :boffice, :bbuilding,:bemail, :bunp) ";
					  
					  SqlParameterSource parameters = new BeanPropertySqlParameterSource(branch);
					  GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
					  
					  int row = template.update(sql, parameters, keyHolder,
							new String[] { "id" });
					  id = keyHolder.getKey().longValue();
				    }
				}
				return id;
		}
		
		// ----------------------------------------------------------------------------------------------
		//  make where clause for client 
		//-----------------------------------------------------------------------------------------------
		private String createClientWhereClause(Client client) {
			String sqlwhere = "";
			
			if (client.getName() != null && !client.getName().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " name = :name ";   
			}    
			if (client.getUnp() != null && !client.getUnp().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " unp = :unp ";   
			}
			if (client.getCity() != null && !client.getCity().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " city = :city ";   
			}
			if (client.getLine() != null && !client.getLine().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " line = :line ";   
			}
			if (client.getCindex() != null && !client.getCindex().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " cindex = :cindex ";   
			}
			if (client.getOffice() != null && !client.getOffice().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " office = :office ";   
			}
			if (client.getBuilding() != null && !client.getBuilding().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " building = :building ";   
			}
			if (sqlwhere.length() != 0)  sqlwhere += " AND ROWNUM = 1 ";
				
			return sqlwhere;
		}

		// ------------------------------------------------------------------------------
		// Get id of client / create client /Exporter / Producer
		// ------------------------------------------------------------------------------
		private long findOrCreateClientID(Client client) throws Exception{
		    String where = createClientWhereClause(client);
		    
			if (where.isEmpty()) {
				throw new RuntimeException("Нет никакой информации о предприятии");
			}
				
			long id = 0;
			String sql = "SELECT id FROM cci_client " + where;
			
			try {
				SqlParameterSource parameters = 
						new BeanPropertySqlParameterSource(client);
				Client company = this.template.queryForObject(sql, 
								parameters,
								new BeanPropertyRowMapper<Client>(Client.class));
				
				id = company.getId();
				
			} catch (Exception ex) {
				
				LOG.info(ex.getMessage());;
				if (id == 0) {
				  sql = "insert into cci_client(id, name, codecountry, cindex, city, line, office, building," + 
						  	"work_phone, cell_phone, email, unp, okpo, account, bname, bindex, bcodecountry," + 
		                    "bcity, bline, boffice, bbuilding, bemail, bunp) " +  
							"values(id_client_seq.nextval, :name, :codecountry, :cindex, :city, :line, :office, :building," + 
						  	":work_phone, :cell_phone, :email, :unp, :okpo, :account, :bname, :bindex, :bcodecountry," + 
		                    ":bcity, :bline, :boffice, :bbuilding, :bemail, :bunp) ";
				  
				  SqlParameterSource parameters = new BeanPropertySqlParameterSource(client);
				  GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
				  
				  int row = template.update(sql, parameters, keyHolder,
						new String[] { "id" });
				  id = keyHolder.getKey().longValue();
			    }
			}
			return id;
	     }

		
		// ----------------------------------------------------------------------------------------------------
		//  Get id of employee / create employee if it doesn't exist
		// ----------------------------------------------------------------------------------------------------
		private long findOrCreateEmployeeID(Employee emp) throws Exception{
			String where = createEmployeeWhereClause(emp);
			
			if (where.isEmpty()) {
				throw new RuntimeException("Отсутствует информация об эксперте");
			}
			
			String sql = "SELECT id FROM cci_employee " + where ;
			long id = 0;
			
			try {
				SqlParameterSource parameters = 
						new BeanPropertySqlParameterSource(emp);
				
				Employee expert = this.template.queryForObject(sql, 
								parameters,
								new BeanPropertyRowMapper<Employee>(Employee.class));
				id = expert.getId();
				
				//id = this.template.getJdbcOperations().queryForObject(sql, 
				//		new Object[] {emp.getName(), emp.getJob()},
				//		Long.class);
			} catch (Exception ex) {
			   LOG.info(ex.getMessage());	
			   if (id == 0) {
				  sql = "insert into cci_employee(name, job) " +  
							"values( :name, :job) ";
				  
				  SqlParameterSource parameters = new BeanPropertySqlParameterSource(emp);
				  GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
				  
				  template.update(sql, parameters, keyHolder,
						new String[] { "id" });
				  id = keyHolder.getKey().longValue();
			    }
			}
			return id;
		}

		// ------------------------------------------------------------------------
		//  make where clause for employee 
		//-------------------------------------------------------------------------
		private String createEmployeeWhereClause(Employee emp) {
	        String sqlwhere = "";
			
			if (emp.getName() != null && !emp.getName().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " name = :name ";   
			}    
			if (emp.getJob() != null && !emp.getJob().isEmpty()) {
				if (sqlwhere.length() == 0) { 	sqlwhere += " WHERE ";	} else {sqlwhere += " AND ";}
				sqlwhere += " job = :job ";   
			}
			if (sqlwhere.length() != 0)  sqlwhere += " AND ROWNUM = 1 ";

			return sqlwhere;
		}

		//--------------------------------------------------------------------
		//--------------------------------------------------------------------
		//            GET FS Certificate WEB or REST service 
		//--------------------------------------------------------------------
		//--------------------------------------------------------------------
		public FSCertificate getFSCertificateByNumber(String number) throws Exception {
	        FSCertificate rcert;
			
			String sql = "select * from FS_CERT WHERE CERTNUMBER = ?";
			rcert = template.getJdbcOperations().queryForObject(
						sql,
						new Object[] { number},
						new FSCertificateRowMapper());
			
	        // If certificate found fill in  all linked objects 
			if (rcert != null) {
			    if (rcert.getBranch() != null ) {
			    	Branch branch = template.getJdbcOperations().queryForObject(
							"select * from fs_branch where id = ? ",
							new Object[] {rcert.getBranch().getId() },
							new BeanPropertyRowMapper<Branch>(Branch.class));
			    	rcert.setBranch(branch);
			    }
			    
			    if (rcert.getExporter() != null ) {
			    	Exporter obj = template.getJdbcOperations().queryForObject(
							"select * from cci_client where id = ? ",
							new Object[] {rcert.getExporter().getId() },
							new BeanPropertyRowMapper<Exporter>(Exporter.class));
			    	rcert.setExporter(obj);
			    }
			    
			    if (rcert.getProducer() != null ) {
			    	Producer obj = template.getJdbcOperations().queryForObject(
							"select * from cci_client where id = ? ",
							new Object[] {rcert.getProducer().getId() },
							new BeanPropertyRowMapper<Producer>(Producer.class));
			    	rcert.setProducer(obj);
			    }
			    
			    if (rcert.getExpert() != null ) {
			    	Expert obj = template.getJdbcOperations().queryForObject(
							"select * from cci_employee where id = ? ",
							new Object[] {rcert.getExpert().getId() },
							new BeanPropertyRowMapper<Expert>(Expert.class));
			    	rcert.setExpert(obj);
			    }

			    if (rcert.getSigner() != null ) {
			    	Signer obj = template.getJdbcOperations().queryForObject(
							"select * from cci_employee where id = ? ",
							new Object[] {rcert.getSigner().getId() },
							new BeanPropertyRowMapper<Signer>(Signer.class));
			    	rcert.setSigner(obj);
			    }
			    
			    if (rcert != null) {
					sql = "select * from FS_PRODUCT WHERE ID_FSCERT = ?  ORDER BY id";
					rcert.setProducts(template.getJdbcOperations().query(sql,
							new Object[] { rcert.getId() },
							new BeanPropertyRowMapper<FSProduct>(FSProduct.class)));
					
					sql = "select * from FS_BLANK WHERE ID_FSCERT = ?  ORDER BY id";
					rcert.setBlanks(template.getJdbcOperations().query(sql,
							new Object[] { rcert.getId() },
							new BeanPropertyRowMapper<FSBlank>(FSBlank.class)));
			    }
			}
			
			return rcert;
		}
		
		//--------------------------------------------------------------------
		//--------------------------------------------------------------------	
		//            UPDATE FS Certificate WEB or REST service 
		//--------------------------------------------------------------------
		//--------------------------------------------------------------------
		public FSCertificate updateFSCertificate(FSCertificate cert, String branch_id) throws Exception {
			
	 		if (cert.getId() == 0) cert.setId(getFSCertificateIdByNUmber(cert.getCertnumber()));
			if (cert.getBranch()!= null && cert.getBranch().getId() == 0 ) cert.getBranch().setId(findOrCreateBranchID(cert.getBranch()));
			if (cert.getExporter()!=null && cert.getExporter().getId() == 0 ) cert.getExporter().setId(findOrCreateClientID(cert.getExporter()));
			if (cert.getProducer()!=null && cert.getProducer().getId() == 0 ) cert.getProducer().setId(findOrCreateClientID(cert.getProducer()));
			if (cert.getExpert()!=null && cert.getExpert().getId() == 0 ) cert.getExpert().setId(findOrCreateEmployeeID(cert.getExpert()));
			if (cert.getSigner()!=null && cert.getSigner().getId() == 0 ) cert.getSigner().setId(findOrCreateEmployeeID(cert.getSigner()));
			
			LOG.info(cert);
			
			String sql = "UPDATE fs_cert SET parentnumber = :parentnumber, dateissue = TO_DATE(:dateissue,'DD.MM.YY'), "
					    + " dateexpiry = TO_DATE(:dateexpiry,'DD.MM.YY'), confirmation = :confirmation, " 
					    + " declaration = :declaration, codecountrytarget=:codecountrytarget, datecert=:datecert,"
					    + " id_branch = " + ((cert.getBranch() != null) ?  ":branch.id, " : ":branch,")  
					    + " id_exporter = " + ((cert.getExporter() != null) ?  ":exporter.id, " : ":exporter, ") 
					    + " id_producer = " + ((cert.getProducer() != null) ?  ":producer.id, " : ":producer, ")
					    + " id_expert =  " + ((cert.getExpert() != null) ?  ":expert.id, " : ":expert, ")
					    + " id_signer = " + ((cert.getSigner() != null) ?  ":signer.id " : ":signer ")
					    + " WHERE certnumber = :certnumber "; 

			LOG.info(sql);
			
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);
			int row = template.update(sql, parameters);

			if (row > 0) {
				
				template.getJdbcOperations().update(
						"delete from FS_PRODUCT where id_fscert = ?",
						cert.getId());
				
				sql = "insert into fs_product(id_fscert, numerator, tovar) values ("
						+ cert.getId() + ", :numerator, :tovar)";
				if (cert.getProducts() != null && cert.getProducts().size() > 0) {
					SqlParameterSource[] batch = SqlParameterSourceUtils
							.createBatch(cert.getProducts().toArray());
					int[] updateCounts = template.batchUpdate(sql, batch);
					LOG.info("Added products : " +  updateCounts.toString());
				}
				
				template.getJdbcOperations().update(
						"delete from FS_BLANK where id_fscert = ?",
						cert.getId());
				
				sql = "insert into fs_blank(id_fscert, page, blanknumber) values ("
						+ cert.getId() + ", :page, :blanknumber)";
				
				if (cert.getBlanks() != null && cert.getBlanks().size() > 0) {
					SqlParameterSource[] batch = SqlParameterSourceUtils
							.createBatch(cert.getBlanks().toArray());
					int[] updateCounts = template.batchUpdate(sql, batch);
					LOG.info("Added blank : " +  updateCounts.toString());
				}
	 
			} else {
				throw new RuntimeException("Не удалось изменить сертификат номер " + cert.getCertnumber() + " по неизвестной причине.");
			}
			return cert;
		}

		//--------------------------------------------------------------------	
		//            GET ID certificate by its numbers 
		//--------------------------------------------------------------------
		private long getFSCertificateIdByNUmber(String certnumber) {
			String sql = "select id from FS_CERT WHERE CERTNUMBER = ?";
			
			long id = template.getJdbcOperations().queryForObject(
	                sql, new Object[] { certnumber }, Long.class);
			
			return id;
		}

		//--------------------------------------------------------------------
		//--------------------------------------------------------------------	
		//            GET list of certificate numbers in CVS string format 
		//--------------------------------------------------------------------
		//--------------------------------------------------------------------
		public String getFSCertificates(FSFilter filter) throws Exception {
			String ret = null;
			
			String sql = "select certnumber, dateissue from fs_cert " 
			              + filter.getWhereLikeClause() 
			              + " ORDER by dateissue";
			
			LOG.info(sql);
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(filter);		
			
			return this.template.query(sql,	parameters, 
					new FSLightCertificateRowMapper());
		}

		//--------------------------------------------------------------------
		//--------------------------------------------------------------------	
		//            DELETE FS certificate by certificate number 
		//--------------------------------------------------------------------
		//--------------------------------------------------------------------
		public String deleteFSCertificate(String certnumber, String branch_id) throws Exception {
	        String rnumber = null;
	        long id = getFSCertificateIdByNUmber(certnumber);
	        
	    	template.getJdbcOperations().update(
					"delete from FS_PRODUCT where id_fscert = ?",
					Long.valueOf(id));
		
	    	template.getJdbcOperations().update(
					"delete from FS_BLANK where id_fscert = ?",
					Long.valueOf(id));
	    	
	    	template.getJdbcOperations().update(
					"delete from FS_CERT where id = ?",
					Long.valueOf(id));
	    	
	    	rnumber = certnumber;
	    	LOG.info("Certificate " + rnumber + " deleted !");
			return rnumber;
		}




		// ------------------------------------------------------------------------------------------------------
		// ------------------------------------------------------------------------------------------------------		
		//
		//   WEB  WEB  WEB WEB WEB WEB  
		//
		// ------------------------------------------------------------------------------------------------------
		// ------------------------------------------------------------------------------------------------------
		// ------------------------------------------------------------------------------
		//  This method returns count of pages in certificate list 
		// ------------------------------------------------------------------------------
		public int getViewPageCount(SQLBuilder builder) {
	        SQLQueryUnit qunit = builder.getSQLUnitWhereClause();     
			String sql = "SELECT count(*) FROM FSCERTVIEW "
					+ qunit.getClause();
		
			Integer count = this.template.queryForObject(sql, qunit.getParams(), Integer.class);
			
			LOG.info(sql);
			return count.intValue();
		}


		// ------------------------------------------------------------------------------
		//  This method returns a current page of the certificate's list
		// ------------------------------------------------------------------------------
		public List<ViewFSCertificate> findViewNextPage(String[] dbfields, int page, int pagesize, int pagecount,
				String orderby, String order, SQLBuilder builder) {
			String sql; 
			String flist = "id";
			
			for (String field : dbfields) {
			    flist += ", " + field;  	
			}
	        SQLQueryUnit filter = builder.getSQLUnitWhereClause();
	        Map<String, Object> params = filter.getParams();
	        LOG.info("SQLQueryUnit : " + filter);
	        
	        
	        if (pagesize < pagecount) {
	        	sql = "select " + flist 
					+ " from fscertview where id in "
					+ " (select  a.id "
					+ " from (SELECT id FROM (select id from fscertview "
					+  filter.getClause()
					+ " ORDER by " +  orderby + " " + order + ", id " + order  
					+ ") where rownum <= :highposition "    
					+ ") a left join (SELECT id FROM (select id from fscertview "
					+  filter.getClause()
					+ " ORDER by " +  orderby + " " + order + ", id " + order
					+ ") where rownum <= :lowposition "   
					+ ") b on a.id = b.id where b.id is null)" 
					+ " ORDER by " +  orderby + " " + order + ", cert_id " + order;
	       		params.put("highposition", Integer.valueOf(page * pagesize));
	    		params.put("lowposition", Integer.valueOf((page - 1) * pagesize));
	        } else {
	        	sql = "select " + flist 
	    				+ " from fscertview "
	    				+  filter.getClause()
	    				+ " ORDER by " +  orderby + " " + order + ", id " + order;  
	        }
			
			LOG.info("Next page : " + sql);
			
			if (pagecount != 0) {
			    return this.template.query(sql,	params, 
					new BeanPropertyRowMapper<ViewFSCertificate>(ViewFSCertificate.class));
			} else {
				return null;
			}
		
		}

		// ------------------------------------------------------------------------------
		//  This method returns list of FS certificates for export to Excel file
		// ------------------------------------------------------------------------------
		public List<ViewFSCertificate> getCertificates(String[] dbfields, String orderby, String order,
				SQLBuilder builder) {
			
			String flist = "id";
			
			for (String field : dbfields) {
			    flist += ", " + field;  	
			} 
			
			flist = "*";
			
			SQLQueryUnit filter = builder.getSQLUnitWhereClause();
	    	Map<String, Object> params = filter.getParams();

			String sql = " SELECT " + flist + " FROM FSCERTVIEW " 
					+ filter.getClause() + " ORDER BY " +  orderby + " " + order;

			LOG.info("Get certificates: " + sql);
			
			return this.template.query(sql,	params, 
					new BeanPropertyRowMapper<ViewFSCertificate>(ViewFSCertificate.class));
		}


		// ---------------------------------------------------------------
		// поиск единственного сертификата по id -> PS
		// ---------------------------------------------------------------
		public FSCertificate findFSCertificateByID(int id) {
			String sql = "select * from certview WHERE id = ?";
			
			FSCertificate cert = template.getJdbcOperations()
					.queryForObject(
							sql,
							new Object[] { id },
							new BeanPropertyRowMapper<FSCertificate>(FSCertificate.class));
			
			sql = "select * from ownproduct WHERE id_certificate = ? ORDER BY id";
			
			cert.setProducts(template.getJdbcOperations().query(sql,
					new Object[] { cert.getId() },
					new BeanPropertyRowMapper<FSProduct>(FSProduct.class)));
			return cert;
		}

	
}
