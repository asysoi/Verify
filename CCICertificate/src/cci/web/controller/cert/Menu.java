package cci.web.controller.cert;

public class Menu {
	private String beltpp = "БелТПП";
	private String main = "Главная";
	private String cert = "Сертификаты";

	public String getBeltpp() {
		return beltpp;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

}
