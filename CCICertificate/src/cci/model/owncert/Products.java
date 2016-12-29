package cci.model.owncert;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Products {
	private List<Product> products;
	
    @XmlElement(name="product")
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
