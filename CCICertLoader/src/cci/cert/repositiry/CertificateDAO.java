package cci.cert.repositiry;

import java.util.List;

import cci.cert.model.Certificate;

public interface CertificateDAO {

	public Certificate findByID(Long id);
	
	public List<Certificate> findByNBlanka(String number);
	
	public List<Certificate> findByNumberCert(String number);
	
	public Certificate check(Certificate cert); 
	
	public List<Certificate> findAll();
		
	public List<Certificate> findByCertificate(Certificate cert);
	
	public List<Certificate> findNextPage(int pageindex, int pagesize);

	public long save(Certificate cert);

	public void update(Certificate cert);

	public long getOtdIdBySynonimName(String directory);

	public int saveFile(long cert_id, String lfile);
	
}
