package cci.cert.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;

import cci.cert.model.Certificate;
import cci.cert.repository.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class FTPReader {
	
	public void load(CertificateDAO dao) {
		// String server = "212.98.164.233";
		// String username = "cci.by";
		// String password = "knKI$w_J3na";
		// String directory = "tmp/minsk/";

		String server = "212.98.164.233";
		String username = "cci_ca";
		String password = "MoonLight_2014";
		String directory = "vitebsk/";

		FTPClient f = new FTPClient();
		
		try {
			f.connect(server);
			f.login(username, password);
			FTPFile[] files = f.listFiles(directory);
			InputStream input;
			XMLService xmlreader = new XMLService();
			Certificate cert = null;
			long start;
            int counter = 1;
			
			
			for (FTPFile file : files) {

				if (file.getType() == FTPFile.FILE_TYPE) {

					start = System.currentTimeMillis();
					System.out.print(file.getName());

					try {
   						input = f.retrieveFileStream(directory + file.getName());
   						
						if (input != null) {
	
							try {
								cert = xmlreader.loadCertificate(input);
							} catch (Exception ex) {
								System.out.println("Сертификат не загружен из-за ошибки: " + ex.toString());
							}
							input.close();
								
							if (!f.completePendingCommand()) {
								f.logout();
								f.disconnect();
								System.err.println("File transfer failed.");
								System.exit(1);
							}
							
							if (cert != null) {
								try {
									cert.setNomercert("Vitebsk_" + cert.getNomercert());
									cert.setOtd_id(5);
									
									if (dao.save(cert) > 0) { 
									   f.deleteFile(directory + file.getName());
									}
								} catch(Exception ex) {
									System.out.println("Сертификат не сохранен из-за ошибки: " + ex.toString());
								}
							}
						} else {
							System.out.println("Input isn't opened");
						}
					} catch (Exception ex) {
						System.out.println("Сертификат не загружен из-за ошибки: " + ex.toString());	
					}
					System.out.println(System.currentTimeMillis() - start);
				}
				if (counter++ > 20) {
					break;
				}
			}
  
			f.logout();
			f.disconnect();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

