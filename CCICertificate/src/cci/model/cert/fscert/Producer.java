package cci.model.cert.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.stereotype.Component;
import cci.model.Client;

//------------------------------------------
// Producer extends common class Client         
//------------------------------------------
@XmlRootElement(name = "producer")
@Component
public class Producer extends Client {

}
