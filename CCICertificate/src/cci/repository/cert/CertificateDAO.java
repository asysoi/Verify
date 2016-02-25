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
	
	public List<Certificate> findViewNextPage(String[] dbfields, int page, int pagesize, String orderby, String order, SQLBuilder builder);

	public int save(Certificate cert);

	public void update(Certificate cert);
	
	public int getViewPageCount(SQLBuilder builder);

	public Map<String, String> getDepartmentsList();
	
	public Map<String,String> getACL();

	public Map<String, String> getCountriesList();

	public List<String> getFormsList();

	public List<Certificate> getCertificates(String[] dbfields, String orderby, String order,
			SQLBuilder builder);

	public List<Report> getReport(String[] fields, SQLBuilder builder, Boolean onfilter);

	public List<Certificate> findViewNextReportPage(String[] dbfields, int page, int pagesize,
			String orderby, String order, SQLBuilder builder);

	public int getViewPageReportCount(SQLBuilder builder);

	public List<Certificate> getReportCertificates(String[] dbfields, String orderby,
			String order, SQLBuilder builder);
	
}
