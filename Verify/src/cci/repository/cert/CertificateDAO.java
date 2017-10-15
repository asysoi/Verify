package cci.repository.cert;

import java.util.List;
import cci.model.cert.Certificate;

public interface CertificateDAO {
		
	public List<Certificate> findByNBlanka(String number);
	
	public List<Certificate> findByNumberCert(String number);
	
	public Certificate check(Certificate cert); 
		
	public List<Certificate> findByCertificate(Certificate cert);
		
	
	// --------------  Certificate methods for REST ------------------------
	
	public Certificate getCertificateByNumber(String number, String blanknumber) throws Exception;

	
}
