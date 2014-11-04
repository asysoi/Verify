package cci.cert.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cci.cert.repository.CertificateDAO;

@Component
public abstract class CERTReader {
	static final Logger LOG = Logger.getLogger(CERTReader.class);
	private final String UTF8_BOM = "\uFEFF";
	private final String UTF8 = "UTF8";

	private boolean exitflag = false;
	private boolean stopped;

	public abstract void load(CertificateDAO dao);

	// ------------------------------------------------------------------------
	// save XML file with string certificate
	// ------------------------------------------------------------------------
	protected boolean saveFileIntoLоcalDirectory(String lfile, String xmltext) {
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

	// ------------------------------------------------------------------------
	// save XML file from inputstream
	// ------------------------------------------------------------------------
	protected boolean saveFileIntoLоcalDirectory(String lfile, InputStream input) {
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

	// ---------------------------------------------------------------
	// Service method
	// ---------------------------------------------------------------
	protected String getStringFromInputStream(InputStream is) {
		
		//return readInputToString(is);
				
		byte[] sbyte = readInputStreamToByteArray(is);
		//printBytes(sbyte, "sbyte", 20);
		
		String str = (new String(sbyte)).trim();
		LOG.info("Converted input to string...");
		
		if (str.toUpperCase().indexOf("UTF-8") != -1) {
			try {
				str = (new String(sbyte, UTF8)).trim();

				if (str.startsWith(UTF8_BOM)) {
					LOG.info("UTF8 BOM found...");
					str = str.substring(1);
				}
			} catch (UnsupportedEncodingException e) {
				LOG.error("Строка не конвертируется в UTF8");
			}
		} else {
			LOG.info("UTF8 charset isn't found ...");
		}
		return str;
	}

	public static void printBytes(byte[] array, String name, int leng) {
	    for (int k = 0; k < leng; k++) {
	        System.out.println(name + "[" + k + "] = " + "0x" +
	            UnicodeFormatter.byteToHex(array[k]));
	    }
	}
	
	
	private byte[] readInputStreamToByteArray(InputStream is) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		try {
			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

		} catch (IOException e) {
			LOG.error("Ошибка загрузки строки из входного потока");
			buffer.reset();
			e.printStackTrace();
		}

		return buffer.toByteArray();
	}

	private String readInputToString(InputStream is) {

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

	// ---------------------------------------------------------------
	// Stopping thread
	// ---------------------------------------------------------------
	public void finished(boolean flag) {
		LOG.info("CERTReader finishing...");
		setExitflag(flag);

		while (!isStopped()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isExitflag() {
		return exitflag;
	}

	public void setExitflag(boolean exitflag) {
		this.exitflag = exitflag;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
