package cci.cert.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import cci.cert.model.Certificate;

@Component
public class XMLService {

	public Certificate loadCertificate(String file_path) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Unmarshaller um = context.createUnmarshaller();
		Certificate cert = (Certificate) um
				.unmarshal(new FileReader(file_path));
		return cert;
	}
	
	public Certificate loadCertificate(InputStream input) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Unmarshaller um = context.createUnmarshaller();
		Certificate cert = (Certificate) um
				.unmarshal(input);
		return cert;
	}
	

	public void uploadCertificateToFile(Certificate cert, String file_path)
			throws Exception {
		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(cert, new File(file_path));
	}
	
	public String uploadCertificateToString(Certificate cert)
			throws Exception {
		Writer outstr = new StringWriter();
		
		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(cert, outstr);
        
        return outstr.toString();
	}

}
