package cci.cert.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repositiry.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class FTPReader {
	// private String[] ftpdirs = { "minsk", "vitebsk", "grodno", "gomel",
	// "brest", "mogilev" };
	private String[] ftpdirs = { "minsk"};
	private String ftpseparator = "/";

	@Autowired
	XMLService xmlreader;

	public void load(CertificateDAO dao) {
		// String server = "212.98.164.233";
		// String username = "cci.by";
		// String password = "knKI$w_J3na";
		// String directory = "tmp/minsk/";

		String server = "212.98.164.233";
		String username = "cci_ca";
		String password = "MoonLight_2014";
		int limit = 20;
		FTPClient f = new FTPClient();

		while (true) {
			
			for (String directory : ftpdirs) {
				try {
					f.connect(server);
					f.login(username, password);

					FTPFile[] files = f.listFiles(directory);
					InputStream input;
					Certificate cert = null;
					long start;
					long cert_id;
					int counter = 1;
					long otd_id = dao.getOtdIdBySynonimName(directory);
					System.out.println(" :otd_id=" + otd_id);

					// åñëè åñòü òàêîé îòäåëåíèå
					if (otd_id > 0) {

						for (FTPFile file : files) {
							if (file.getType() == FTPFile.FILE_TYPE) {

								start = System.currentTimeMillis();
								System.out.print(file.getName());

								try {
									System.out.print(":rem_file=" + directory
											+ ftpseparator + file.getName());
									input = f.retrieveFileStream(directory
											+ ftpseparator + file.getName());

									if (input != null) {

										try {
											cert = xmlreader
													.loadCertificate(input);
										} catch (Exception ex) {
											System.out
													.println("Ñåðòèôèêàò íå çàãðóæåí èç-çà îøèáêè: "
															+ ex.toString());
										}
										input.close();

										if (!f.completePendingCommand()) {
											f.logout();
											f.disconnect();
											System.err
													.println("File transfer failed.");
											// System.exit(1);
										}

										if (cert != null) {
											try {
												cert.setOtd_id((int) otd_id);
												cert_id = dao.save(cert);

												if (cert_id > 0) {
													input = f
															.retrieveFileStream(directory
																	+ ftpseparator
																	+ file.getName());
													String lfile = Config.REPPATH
															+ directory
															+ File.separator
															+ file.getName();

													boolean saved = saveFileIntoLîcalDirectory(
															lfile, input);
													input.close();

													if (!f.completePendingCommand()) {
														f.logout();
														f.disconnect();
														System.err
																.println("File transfer failed.");
													}
													if (saved) {
														dao.saveFile(cert_id,
																lfile);

														//f.deleteFile(directory
														//		+ ftpseparator
														//		+ file.getName());
													}

												}
											} catch (Exception ex) {
												System.out
														.println("Ñåðòèôèêàò íå ñîõðàíåí èç-çà îøèáêè: "
																+ ex.toString());
											}
										}
									} else {
										System.out
												.println(" Input isn't opened");
									}
								} catch (Exception ex) {
									System.out
											.println("Ñåðòèôèêàò íå çàãðóæåí èç-çà îøèáêè: "
													+ ex.toString());
								}
								System.out.println(System.currentTimeMillis()
										- start);
							}
							if (++counter > limit) {
								break;
							}
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
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // while (true)
	}

	private boolean saveFileIntoLîcalDirectory(String lfile, InputStream input) {
		byte[] buffer = new byte[1024];
		int bytesRead;
		boolean ret = false;

		try {
			File file = new File(lfile);
			OutputStream output = new FileOutputStream(file);

			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
			output.close();
			ret = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}
}
