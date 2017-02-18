package cci.repository.owncert;

import cci.model.owncert.OwnCertificate;
import cci.model.owncert.OwnCertificateHeaders;
import cci.model.owncert.OwnCertificates;
import cci.web.controller.owncert.OwnFilter;

public interface OwnCertificateDAO {

	OwnCertificates getOwnCertificates(OwnFilter filter, boolean b);

	OwnCertificateHeaders getOwnCertificateHeaders(OwnFilter filter, boolean b);

	OwnCertificate findOwnCertificateByID(int id) throws Exception ;

    OwnCertificate saveOwnCertificate(OwnCertificate certificate) throws Exception;

	OwnCertificate updateOwnCertificate(OwnCertificate certificate);

}
