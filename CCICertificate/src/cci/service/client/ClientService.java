package cci.service.client;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cci.model.purchase.Company;
import cci.repository.SQLBuilder;
import cci.repository.client.ClientDAO;

@Component
public class ClientService {

	@Autowired
	private ClientDAO clientDAO;
	
	public List<Company> readClientsPage(int page, int pagesize,
			String orderby, String order, SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<Company> list = null;

		try {
			list = clientDAO.findViewNextPage(page, pagesize, orderby,
					order, builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

	public int getViewPageCount(SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));
		int counter = 0;
		try {
			counter = clientDAO.getViewPageCount(builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return counter;
	}

}
