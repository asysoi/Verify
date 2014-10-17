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
import cci.cert.certificate.CCIProperty;
import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repository.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class FTPReader {
	static final Logger LOG = Logger.getLogger(FTPReader.class);

	@Autowired
	XMLService xmlreader;

	private boolean exitflag = false;
	private boolean stopped;
	CCIProperty props = CCIProperty.getInstance();

	public void load(CertificateDAO dao) {
		FTPClient ftp = new FTPClient();
		stopped = false;

		while (!exitflag) {

			for (String directory : Config.ftpdirs) {
				try {
					ftp.connect(props.getProperty(Config.URL));
					ftp.login(props.getProperty(Config.LOGIN), props.getProperty(Config.PSW));

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
											+ props.getProperty(Config.FTPSEPARATOR)
											+ file.getName());
									input = ftp.retrieveFileStream(directory
											+ props.getProperty(Config.FTPSEPARATOR)
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
											cert = null;
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

												String lfile = props.getProperty(Config.REPPATH)
														+ directory
														+ File.separator
														+ file.getName();

												if (checkcert == null) {
													cert_id = dao.save(cert);
													
													if (cert_id > 0) {
														LOG.info("Сертификат с номером "
																+ cert_id
																+ " добавлен в базу данных");
														saved = saveFileIntoLоcalDirectory(
																lfile, xmltext);

														if (saved) {
															LOG.info("Файл сертификата с номером "
																	+ cert_id
																	+ " сохранен в локальном хранилище");
															dao.saveFile(
																	cert_id,
																	lfile);

															if (Boolean.parseBoolean(props.getProperty(Config.ISDELETE))) {
																ftp.deleteFile(directory
																		+ props.getProperty(Config.FTPSEPARATOR)
																		+ file.getName());
																LOG.info("Файл "
																		+ directory
																		+ props.getProperty(Config.FTPSEPARATOR)
																		+ file.getName()
																		+ " удален с FTP");
															}
														}
													} else {
														LOG.info("Сертификат с номером  "
																+ cert_id
																+ " НЕ МОЖЕТ БЫТЬ добавлен в базу данных. Ошибка загрузки сертификата...");
													}
												} else {
													if (!checkcert.equals(cert)) {
														LOG.info("Сертификатc номером "
																+ cert.getNomercert()
																+ " изменился. Выполняется обновление... ");
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
														LOG.info("Сертификат c номером "
																+ cert.getNomercert()
																+ " уже зарегистрирован. Пропуск... ");
													}

													if (Boolean.parseBoolean(props.getProperty(Config.ISDELETE))) {
														ftp.deleteFile(directory
																+ props.getProperty(Config.FTPSEPARATOR)
																+ file.getName());
														
														LOG.info("Файл "
																+ directory
																+ props.getProperty(Config.FTPSEPARATOR)
																+ file.getName()
																+ " удален с FTP сервера");
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
								LOG.info("Время загрузки сертификата из файла "
										+ directory + props.getProperty(Config.FTPSEPARATOR)
										+ file.getName() + " составила: "
										+ (System.currentTimeMillis() - start));
							}
							if (++counter > Integer.parseInt(props.getProperty(Config.FILES)) || exitflag) {
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

				if (exitflag) {
					break;
				}
			}

			if (exitflag) {
				break;
			}

			try {
				LOG.info("Пауза в чтении FTP сервера");
				Thread.sleep(Integer.parseInt(props.getProperty(Config.DELAY)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		stopped = true;
		LOG.info("FTPReader finished");
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

	public void finished(boolean flag) {
		LOG.info("FTPReader finishing...");
		this.exitflag = flag;

		while (!stopped) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
