package cci.model.cert.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.stereotype.Component;
import cci.model.Employee;

//------------------------------------------
// Expert extends branch employee for XML needs         
//------------------------------------------
@XmlRootElement(name = "expert")
@Component
public class Expert extends Employee {

}
