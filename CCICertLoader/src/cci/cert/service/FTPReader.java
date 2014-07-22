package cci.cert.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.cert.certificate.CCICertLoader;
import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repositiry.CertificateDAO;
import cci.cert.util.XMLService;


@Component
public class FTPReader {
	static final Logger LOG = Logger.getLogger(FTPReader.class);
	
	@Autowired
	XMLService xmlreader;

	public void load(CertificateDAO dao) {
		FTPClient ftp = new FTPClient();

		while (true) {

			for (String directory : Config.ftpdirs) {
				try {
					ftp.connect(Config.ftpserver);
					ftp.login(Config.username, Config.password);

					FTPFile[] files = ftp.listFiles(directory);
					InputStream input;
					Certificate cert = null, checkcert = null;
					String xmltext;
					long start;
					long cert_id;
					int counter = 1;

					long otd_id = dao.getOtdIdBySynonimName(directory);
					LOG.info("ID Отделения: " + otd_id);

					// если есть такой отделение
					if (otd_id > 0) {

						for (FTPFile file : files) {
							if (file.getType() == FTPFile.FILE_TYPE) {

								start = System.currentTimeMillis();
								
								try {
									LOG.info("Найден FTP файл: " + directory
											+ Config.ftpseparator
											+ file.getName());
									input = ftp.retrieveFileStream(directory
											+ Config.ftpseparator
											+ file.getName());

									if (input != null) {
										xmltext = getStringFromInputStream(input);

										try {
											cert = xmlreader
													.loadCertificate(new ByteArrayInputStream(
															xmltext.getBytes()));
										} catch (Exception ex) {
											LOG.error("Ошибка загрузки сертификата: "
															+ ex.toString());
											ex.printStackTrace();
										}
										input.close();

										if (!ftp.completePendingCommand()) {
											ftp.logout();
											ftp.disconnect();
											LOG.error("File transfer failed.");
										}

										if (cert != null) {
											try {
												cert.setOtd_id((int) otd_id);
												checkcert = dao.check(cert); // проверить
																				// наличие
												boolean saved;

												String lfile = Config.REPPATH
														+ directory
														+ File.separator
														+ file.getName();

												if (checkcert == null) {
													cert_id = dao.save(cert);
													LOG.info("Сертификат с номером " + cert_id + " добавлен в базу данных");

													if (cert_id > 0) {
														saved = saveFileIntoLоcalDirectory(
																lfile, xmltext);

														if (saved) {
															LOG.info("Файл сертификата с номером " + cert_id + " сохранен в локальном хранилище");
															dao.saveFile(
																	cert_id,
																	lfile);
															
	 
															if (Config.isdelete) {
																ftp.deleteFile(directory
																		+ Config.ftpseparator
																		+ file.getName());
																LOG.info("Файл " + directory
																		+ Config.ftpseparator
																		+ file.getName() + " удален с FTP");
															}
														}
													}
												} else {
													if (!checkcert.equals(cert)) {
														LOG.info("Сертификатc номером " + cert.getNomercert() + " изменился. Выполняется обновление... ");
														cert_id = checkcert
																.getCert_id();
														cert.setCert_id(cert_id);
														dao.update(cert);

														saved = saveFileIntoLоcalDirectory(
																lfile, xmltext);

														if (saved) {
															dao.saveFile(
																	cert_id,
																	lfile);
														}
													} else {
														LOG.info("Сертификат c номером " + cert.getNomercert() + " уже зарегистрирован. Пропуск... ");
													}

													if (Config.isdelete) {
														ftp.deleteFile(directory
																+ Config.ftpseparator
																+ file.getName());
													}
												}
												
											} catch (Exception ex) {
												LOG.error("Ошибка сохранения сертификата: "
																+ ex.toString());
												ex.printStackTrace();
											}
										}
									} else {
										LOG.info("FTP Input не был открыт");
									}
								} catch (Exception ex) {
									LOG.error("Сертификат не загружен из-за ошибки: "
													+ ex.toString());
									ex.printStackTrace();
								}
								LOG.info("Время загрузки сертификата из файла " + directory
										+ Config.ftpseparator
										+ file.getName() + " составила: " + (System.currentTimeMillis()
										- start));
							}
							if (++counter > Config.filelimit) {
								break;
							}
						}
					}
					ftp.logout();
					ftp.disconnect();

				} catch (SocketException e) {
					LOG.error("Ошибка на уровне работы сокета");
					e.printStackTrace();
				} catch (IOException e) {
					LOG.error("Ошибка ввода-вывода");
					e.printStackTrace();
				}
			}

			try {
				LOG.info("Пауза в чтении FTP сервера");
				Thread.sleep(Config.processdelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
	}

	
	private boolean saveFileIntoLоcalDirectory(String lfile, String xmltext) {
		OutputStream fop = null;
		boolean ret = false;

		try {
			File file = new File(lfile);
			fop = new FileOutputStream(file);

			if (!file.exists()) {
				file.createNewFile();
			}

			byte[] contentInBytes = xmltext.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			ret = true;
		} catch (IOException e) {
			LOG.error("Ошибка ввода вывода при сохранении файла " + lfile);
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				LOG.error("Ошибка ввода вывода при сохранении файла " + lfile);
				e.printStackTrace();
			}
		}

		return ret;
	}

	private boolean saveFileIntoLоcalDirectory(String lfile, InputStream input) {
		byte[] buffer = new byte[1024];
		int bytesRead;
		boolean ret = false;

		try {
			File file = new File(lfile);
			OutputStream output = new FileOutputStream(file);

			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
			output.flush();
			output.close();
			ret = true;
		} catch (Exception ex) {
			LOG.error("Ошибка ввода вывода при сохранении файла " + lfile);
			ex.printStackTrace();
		}
		return ret;
	}

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			LOG.error("Ошибка загрузки строки из входного потока");
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
}
