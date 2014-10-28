package cci.cert.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cci.cert.certificate.CCIProperty;
import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repository.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class FSReader extends CERTReader {
	static final Logger LOG = Logger.getLogger(FTPReader.class);

	@Autowired
	XMLService xmlreader;

	CCIProperty props = CCIProperty.getInstance();

	public void load(CertificateDAO dao) {
		setStopped(false);

		while (!isExitflag()) {

			for (String directory : Config.ftpdirs) {
				try {

					Certificate cert = null;
					Certificate checkcert = null;
					long start;
					long cert_id;
					int counter = 1;

					String[] files = new File(props.getProperty(Config.XMLPATH)
							+ directory).list();
					long otd_id = dao.getOtdIdBySynonimName(directory);
					LOG.info("ID Отделения " + directory + " : " + otd_id);

					if (otd_id > 0) {
						LOG.info("Найдено файлов: " + files.length);

						for (String filename : files) {
							start = System.currentTimeMillis();

							try {
								String fullfilename = props
										.getProperty(Config.XMLPATH)
										+ directory + File.separator + filename;
								// + FileSystems.getDefault().getSeparator()

								LOG.info("Найден файл: " + fullfilename);

								try {
									cert = xmlreader
											.loadCertificate(fullfilename);
									LOG.info("    Время загрузки и парсинга файла составило: " + 
											+ (System.currentTimeMillis() - start));
									
								} catch (Exception ex) {
									ex.printStackTrace();
								}

								if (cert != null) {
									try {
										cert.setOtd_id((int) otd_id);
										checkcert = dao.check(cert);

										boolean saved;

										String lfile = props
												.getProperty(Config.REPPATH)
												+ directory
												+ File.separator
												+ filename;

										if (checkcert == null) {
											cert_id = dao.save(cert);

											if (cert_id > 0) {
												LOG.info("    Сертификат с номером "
														+ cert_id
														+ " добавлен в базу данных");
												saved = copyFile(fullfilename,
														lfile);

												if (saved) {
													LOG.info("    Файл сертификата с номером "
															+ cert_id
															+ " сохранен в локальном хранилище");
													dao.saveFile(cert_id, lfile);

													if (Boolean
															.parseBoolean(props
																	.getProperty(Config.ISDELETE))) {
														deleteFile(fullfilename);
													}
												}
											} else {
												LOG.info("    Сертификат с номером  "
														+ cert_id
														+ " НЕ МОЖЕТ БЫТЬ добавлен в базу данных. Ошибка загрузки сертификата...");
											}
										} else {
											if (!checkcert.equals(cert)) {
												LOG.info("    Сертификатc номером "
														+ cert.getNomercert()
														+ " изменился. Выполняется обновление... ");
												cert_id = checkcert
														.getCert_id();
												cert.setCert_id(cert_id);
												dao.update(cert);
												LOG.info("    Время обновления составило: " + 
														+ (System.currentTimeMillis() - start));


												saved = copyFile(fullfilename,
														lfile);

												if (saved) {
													dao.saveFile(cert_id, lfile);
												}
											} else {
												LOG.info("    Сертификат c номером "
														+ cert.getNomercert()
														+ " уже зарегистрирован. Пропуск... ");
											}

											if (Boolean
													.parseBoolean(props
															.getProperty(Config.ISDELETE))) {
												deleteFile(fullfilename);

											}
										}

									} catch (Exception ex) {
										LOG.error("    Ошибка сохранения сертификата: "
												+ ex.toString());
										ex.printStackTrace();
									}
								}
							} catch (Exception ex) {
								LOG.error("    Сертификат не загружен из-за ошибки: "
										+ ex.toString());
								ex.printStackTrace();
							}
							LOG.info("    Суммарное время загрузки сертификата из файла "
									+ directory
									+ props.getProperty(Config.FTPSEPARATOR)
									+ filename + " составила: "
									+ (System.currentTimeMillis() - start));

							if (++counter > Integer.parseInt(props
									.getProperty(Config.FILES)) || isExitflag()) {
								break;
							}
						}
					}
				} catch (Exception e) {
					LOG.error("Ошибка: " + e.getMessage());
				}

				if (isExitflag()) {
					break;
				}
			}

			if (isExitflag()) {
				break;
			}

			try {
				LOG.info("Пауза в чтении сертификатов");
				Thread.sleep(Integer.parseInt(props.getProperty(Config.DELAY)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		setStopped(true);
		LOG.info("FTPReader finished");
	}

	// -------------------------------------------------------
	// File copy
	// -------------------------------------------------------
	private boolean copyFile(String fullfilename, String lfile)
			throws Exception {
		boolean saved = false;
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		try {
			sourceChannel = new FileInputStream(new File(fullfilename))
					.getChannel();
			destChannel = new FileOutputStream(new File(lfile)).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
			saved = true;
		} finally {
			sourceChannel.close();
			destChannel.close();
		}

		return saved;
	}

	// -------------------------------------------------------
	// File delete
	// -------------------------------------------------------
	private void deleteFile(String fullfilename) {
		if ((new File(fullfilename)).delete()) {
			LOG.info("Файл " + fullfilename + " удален");
		} else {
			LOG.info("Файл " + fullfilename + " НЕ удален");
		}
	}

}
