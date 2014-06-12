package cci.cert.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import cci.cert.model.Certificate;
import cci.cert.util.XMLService;

public class FTPReader {

	public static void main(String[] args) {
		String server = "212.98.164.233";
		String username = "cci.by";
		String password = "knKI$w_J3na";
		String directory = "tmp/minsk/";

		FTPClient f = new FTPClient();
		try {
			f.connect(server);
			f.login(username, password);
			FTPFile[] files = f.listFiles(directory);
			InputStream input;
			XMLService xmlreader = new XMLService();
			Certificate cert;
			long start;
			
			for (FTPFile file : files) {
				
				if (file.getType() == FTPFile.FILE_TYPE) {
					
					start = System.currentTimeMillis();
					//System.out.println(file.getName());
					
					input = f.retrieveFileStream(directory+file.getName());
					if (input != null) {
						// System.out.println(input.getClass().getName());
						try {
						   cert = xmlreader.loadCertificate(input);
						   //System.out.println(cert);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
                        input.close();
                        
                        if(!f.completePendingCommand()) {
                            f.logout();
                            f.disconnect();
                            System.err.println("File transfer failed.");
                            System.exit(1);
                        }
					} else {
						System.out.println("Input isn't opened");
					}
					System.out.println(System.currentTimeMillis() - start);
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
