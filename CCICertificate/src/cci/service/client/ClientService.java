package cci.service.client;

import java.util.List;
import java.util.Locale;
import java.util.Map;


import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.model.Client;
import cci.repository.SQLBuilder;
import cci.repository.client.ClientDAO;
import cci.web.controller.client.ViewClient;

@Component
public class ClientService {
	//public static Logger LOG=LogManager.getLogger(ClientService.class);
	public static Logger LOG=Logger.getLogger(ClientService.class);
	
	@Autowired
	private ClientDAO clientDAO;

	private Map<String, Map<String, String>> countries = null;

	// ---------------------------------------------------------------
	// Get Clients page
	// ---------------------------------------------------------------
	public List<ViewClient> readClientsPage(int page, int pagesize, String orderby,
			String order, SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<ViewClient> list = null;

		try {
			list = clientDAO.findViewNextPage(page, pagesize, orderby, order,
					builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

	// ---------------------------------------------------------------
	// Get Client count in list
	// ---------------------------------------------------------------
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

	// ---------------------------------------------------------------
	// Get Countries list
	// ---------------------------------------------------------------
	public Map<String, String> getCountriesList(String lang) {
		if (countries == null) {
			Locale.setDefault(new Locale("en", "en"));
			try {
				countries = clientDAO.getCountriesList();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return countries.get(lang);
	}

	// ---------------------------------------------------------------
	// Get Client View
	// ---------------------------------------------------------------
	public Client readClientView(Long id) {
		Locale.setDefault(new Locale("en", "en"));

		ViewClient client = null;
		try {
			client = clientDAO.findClientViewByID(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return client;
	}

	// ---------------------------------------------------------------
	// Get Client 
	// ---------------------------------------------------------------
	public Client readClient(Long id) {
		Locale.setDefault(new Locale("en", "en"));

		Client client = null;
		try {
			client = clientDAO.findClientByID(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return client;
	}
	
	
	// ---------------------------------------------------------------
	// Save Client
	// ---------------------------------------------------------------
	public void saveClient(Client client) {
		Locale.setDefault(new Locale("en", "en"));

		try {
			clientDAO.saveClient(client);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// Update Client
	// ---------------------------------------------------------------
	public void updateClient(Client client) {
		Locale.setDefault(new Locale("en", "en"));

		try {
			clientDAO.updateClient(client);
		} catch (Exception ex) {
 		   ex.printStackTrace();
		}
	}

	// ---------------------------------------------------------------
	// Get CLients List
	// ---------------------------------------------------------------
	public List<ViewClient> readClients(String orderby, String order,
			SQLBuilder builder) {
		Locale.setDefault(new Locale("en", "en"));

		List<ViewClient> clients = null;

		try {
			clients = clientDAO.getClients(orderby,order, builder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return clients;
	}
}
