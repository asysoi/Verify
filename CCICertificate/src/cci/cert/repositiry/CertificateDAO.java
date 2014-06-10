package cci.cert.repositiry;

import java.util.List;

import cci.cert.model.Certificate;

public interface CertificateDAO {

	public Certificate findByID(Long id);
	
	public List<Certificate> findByNBlanka(String number);
	
	public List<Certificate> findByNumberCert(String number);
	
	public Certificate check(String num, String blank, String date); 
	
	public List<Certificate> findAll();
		
	public List<Certificate> findByCertificate(Certificate cert);
	
	public List<Certificate> findNextPage(int pageindex, int pagesize);

	public void save(Certificate cert);

	public void update(Certificate cert);
	
	
	
}
