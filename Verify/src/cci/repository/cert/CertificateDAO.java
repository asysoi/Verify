package cci.repository.cert;

import java.util.List;
import cci.model.cert.Certificate;
import cci.model.owncert.OwnCertificate;

public interface CertificateDAO {
		
	public Certificate checkCT(Certificate cert);
	public Certificate getCertificate(String number, String blanknumber, String date);
	
}
