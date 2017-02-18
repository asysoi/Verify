package cci.model.fscert;

import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.stereotype.Component;
import cci.model.Employee;

//------------------------------------------
// Signer extends branch employee for XML needs         
//------------------------------------------
@XmlRootElement(name = "signer")
@Component
public class Signer extends Employee {

}
