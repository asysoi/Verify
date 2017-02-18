package cci.model.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.stereotype.Component;
import cci.model.Client;

//------------------------------------------
// Exporter extends common class Client         
//------------------------------------------
@XmlRootElement(name = "exporter")
@Component
public class Exporter extends Client {

}
