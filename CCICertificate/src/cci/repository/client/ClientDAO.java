package cci.repository.client;

import java.util.List;
import java.util.Map;

import cci.model.Client;
import cci.model.cert.Company;
import cci.repository.SQLBuilder;
import cci.web.controller.client.ViewClient;

public interface ClientDAO {
	public List<ViewClient> findViewNextPage(int page, int pagesize, String orderby, String order, SQLBuilder builder);
	public int getViewPageCount(SQLBuilder builder);
	public Map<String, String> getCountriesList();
	public ViewClient findClientViewByID(Long id);
	public void saveClient(Client client);
	public Client findClientByID(Long id);
	public void updateClient(Client client);
}
