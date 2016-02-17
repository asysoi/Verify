package cci.repository.cert;

import java.util.List;
import java.util.Map;

import cci.model.cert.Certificate;
import cci.model.cert.Report;
import cci.repository.SQLBuilder;
import cci.service.FilterCondition;

public interface CertificateDAO {

	public Certificate findByID(Long id);
	
	public List<Certificate> findByNBlanka(String number);
	
	public List<Certificate> findByNumberCert(String number);
	
	public Certificate check(Certificate cert); 
	
	public List<Certificate> findAll();
		
	public List<Certificate> findByCertificate(Certificate cert);
	
	public List<Certificate> findViewNextPage(int page, int pagesize, String orderby, String order, SQLBuilder builder);

	public int save(Certificate cert);

	public void update(Certificate cert);
	
	public int getViewPageCount(SQLBuilder builder);

	public List<String> getDepartmentsList();
	
	public Map<String,String> getACL();

	public Map<String, String> getCountriesList();

	public List<String> getFormsList();

	public List<Certificate> getCertificates(String orderby, String order,
			SQLBuilder builder);

	public List<Report> getReport(String[] fields, SQLBuilder builder, Boolean onfilter);

	public List<Certificate> findViewNextReportPage(int page, int pagesize,
			String orderby, String order, String datefrom, String dateto,
			String otd_name);

	public int getViewPageReportCount(String datefrom, String dateto,
			String otd_name);
	
}
