package cci.model.cert.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.springframework.stereotype.Component;

@XmlRootElement(name = "blank")
@XmlType(propOrder = { "page", "number" })
@Component
public class Blank {
	private String number;
	private int page;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
