package cci.cert.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import cci.cert.model.Certificate;

@Component
public class XMLService {
	static final Logger LOG = Logger.getLogger(XMLService.class);
	private final String UTF8_BOM = "\uFEFF";

	public Certificate loadCertificate(String file_path) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Unmarshaller um = context.createUnmarshaller();
		Certificate cert = (Certificate) um
				.unmarshal(new FileReader(file_path));
		return cert;
	}

	public Certificate loadCertificateFromUTF8BOOM(String file_path)
			throws Exception {
		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Unmarshaller um = context.createUnmarshaller();
		Certificate cert = (Certificate) um
				.unmarshal(convertFromUTF8BOOM(file_path));
		return cert;
	}

	private Reader convertFromUTF8BOOM(String file_path) {
		boolean firstLine = true;
		Reader ret = null;
		try {
			FileInputStream fis = new FileInputStream(file_path);
			BufferedReader r = new BufferedReader(new InputStreamReader(fis,
					"UTF8"));
			Writer wr = new StringWriter();
			for (String s = ""; (s = r.readLine()) != null;) {
				if (firstLine) {
					s = removeUTF8BOM(s);
					firstLine = false;
				}
				wr.write(s + System.getProperty("line.separator"));
				wr.flush();
			}
			r.close();
			ret = new StringReader(wr.toString());
			wr.close();
		} catch (Exception ex) {
			LOG.info("Ошибка чтения файла с конвертацией " + ex.getMessage());
		}
		return ret;
	}

	private String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
			LOG.info("UTF8 BOM deleted");

			if (!(s.indexOf("<?") != -1 && s.indexOf("xml") != -1 && s
					.indexOf("encoding") != -1)) {
				s = ("<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
						+ System.getProperty("line.separator") + s);
				LOG.info("Converted string: " + s);
			}
		} else {
			LOG.info("UTF8 BOM  NOT deleted");
		}
		return s;
	}

	public Certificate loadCertificate(InputStream input) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Unmarshaller um = context.createUnmarshaller();
		Certificate cert = (Certificate) um.unmarshal(input);
		return cert;
	}

	public void uploadCertificateToFile(Certificate cert, String file_path)
			throws Exception {
		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(cert, new File(file_path));
	}

	public String uploadCertificateToString(Certificate cert) throws Exception {
		Writer outstr = new StringWriter();

		JAXBContext context = JAXBContext.newInstance(Certificate.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(cert, outstr);

		return outstr.toString();
	}

}
