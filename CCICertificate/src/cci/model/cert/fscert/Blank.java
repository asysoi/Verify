package cci.model.cert.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.springframework.stereotype.Component;

@XmlRootElement(name = "blank")
@XmlType(propOrder = { "page", "blanknumber" })
@Component

public class Blank {
	private Integer page;
	private String blanknumber;

	public String getBlanknumber() {
		return blanknumber;
	}

	public void setBlanknumber(String blanknumber) {
		this.blanknumber = blanknumber;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "Blank [page=" + page + ", blanknumber=" + blanknumber + "]";
	}
}
