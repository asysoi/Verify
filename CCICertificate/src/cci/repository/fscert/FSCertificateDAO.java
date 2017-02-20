package cci.repository.fscert;

import java.util.List;

import cci.model.fscert.FSCertificate;
import cci.model.owncert.OwnCertificate;
import cci.repository.SQLBuilder;
import cci.web.controller.fscert.FSFilter;
import cci.web.controller.fscert.ViewFSCertificate;

public interface FSCertificateDAO {

	// --------------------  FS Certificate methods RESTFUL ---------------------------- //	
	public FSCertificate saveFSCertificate(FSCertificate certificate) throws Exception;

	public FSCertificate updateFSCertificate(FSCertificate certificate, String branch_id) throws Exception;

	public FSCertificate getFSCertificateByNumber(String number) throws Exception;

	public String getFSCertificates(FSFilter filter) throws Exception;

	public String deleteFSCertificate(String certnumber, String branch_id) throws Exception;
	

	// --------------------  FS Certificate methods Web    ---------------------------- //
	public int getViewPageCount(SQLBuilder builder);

	public List<ViewFSCertificate> findViewNextPage(String[] fields, int page, int pagesize, int pagecount, String orderby,
			String order, SQLBuilder builder);

	public List<ViewFSCertificate> getCertificates(String[] fields, String orderby, String order, SQLBuilder builder);

	public FSCertificate findFSCertificateByID(int id) throws Exception;
	
}
