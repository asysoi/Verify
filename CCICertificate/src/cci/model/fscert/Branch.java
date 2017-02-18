package cci.model.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.springframework.stereotype.Component;
import cci.model.Client;

// ------------------------------------------
// Branch of CCI extends common class Client         
// ------------------------------------------
@XmlRootElement(name = "branch")
@Component

public class Branch extends Client {

}
