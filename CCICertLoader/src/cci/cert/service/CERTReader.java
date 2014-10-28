package cci.cert.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cci.cert.repository.CertificateDAO;

@Component
public abstract class CERTReader {
	static final Logger LOG = Logger.getLogger(CERTReader.class);

	private boolean exitflag = false;
	private boolean stopped;
	
	public abstract void load(CertificateDAO dao);
		
	//------------------------------------------------------------------------
	// save XML file with string certificate  
	//------------------------------------------------------------------------
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

	//------------------------------------------------------------------------
	// save XML file from inputstream 
	//------------------------------------------------------------------------
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
	protected static String getStringFromInputStream(InputStream is) {

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
