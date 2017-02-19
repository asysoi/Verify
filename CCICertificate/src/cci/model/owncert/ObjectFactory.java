package cci.model.owncert;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
class ObjectFactory {
    OwnCertificate createOwnCertificate() {
        return new OwnCertificate();
    }
    
    Product createProduct() {
    	return new Product();
    }
    
    Products createProducts() {
    	return new Products();
    }
}