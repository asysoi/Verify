package cci.repository.client;

import java.util.List;
import cci.model.purchase.Company;
import cci.repository.SQLBuilder;

public interface ClientDAO {
	public List<Company> findViewNextPage(int page, int pagesize, String orderby, String order, SQLBuilder builder);
	public int getViewPageCount(SQLBuilder builder);
}
