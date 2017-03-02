package cci.repository.staff;

import java.util.List;

import cci.repository.SQLBuilder;
import cci.web.controller.fscert.ViewFSCertificate;

public interface EmployeeDAO {

	int getViewPageCount(SQLBuilder builder);
	List<ViewFSCertificate> findViewNextPage(String[] fields, int page, int pagesize, int pagecount, String orderby, String order, SQLBuilder builder);
	

}
