package cci.cert.repository;

import java.util.List;

import cci.cert.model.Certificate;
import cci.purchase.service.Filter;

public interface CertificateDAO {

	public Certificate findByID(Long id);
	
	public List<Certificate> findByNBlanka(String number);
	
	public List<Certificate> findByNumberCert(String number);
	
	public Certificate check(Certificate cert); 
	
	public List<Certificate> findAll();
		
	public List<Certificate> findByCertificate(Certificate cert);
	
	public List<Certificate> findViewNextPage(int page, int pagesize, String orderby, String order, Filter filter);

	public int save(Certificate cert);

	public void update(Certificate cert);
	
	public int getViewPageCount(Filter filter);
	
}
