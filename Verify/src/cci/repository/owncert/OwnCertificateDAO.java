package cci.repository.owncert;

import java.util.List;

import cci.model.cert.Certificate;
import cci.model.owncert.OwnCertificate;


public interface OwnCertificateDAO {
	OwnCertificate getOwnCertificate(Certificate cert) throws Exception;
	OwnCertificate checkOwnExist(Certificate cert) throws Exception;
}
