package cci.pdfbuilder.fscert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import cci.config.cert.PDFConfigReader;
import cci.config.cert.PDFPageConfig;
import cci.config.cert.XMLConfigReader;
import cci.model.cert.Certificate;
import cci.model.cert.Product;
import cci.model.fscert.FSCertificate;
import cci.pdfbuilder.PDFBuilder;
import cci.pdfbuilder.PDFBuilderFactory;
import cci.pdfbuilder.cert.CertificatePDFBuilder;
import cci.service.CountryConverter;
import cci.service.FilterCondition;
import cci.service.cert.CertFilter;

public class FSPDFBuilder extends PDFBuilder {
	public static Logger LOG=Logger.getLogger(FSPDFBuilder.class);
	
	public static final String FONT_DIR = "resources/fonts/";
	private Document document;
	private PdfWriter writer;
	private PDFConfigReader xreader;
	private PDFPageConfig pconfig;
	private String fontpath;
	
	
	public void createPdf(String outfilename, Object cert,
			String configFileName, String fpath) throws IOException, DocumentException {
		fontpath = fpath;
		// step 1
		document = new Document(PageSize.A4, 0, 0, 0, 0);
		// step 2
		writer = PdfWriter.getInstance(document, new FileOutputStream(outfilename));
		// step 3
		document.open();
		// step 4
		xreader = XMLConfigReader.getInstance(configFileName, fontpath);
		createMain(cert);
		// step 5
		document.close();
	}
	
	private void createMain(Object cert) throws DocumentException, IOException {
		String pagename  = PDFBuilderFactory.PAGE_FS;; 
		//cert.setCurrentlist(0);  // start from main certification list
		//while (cert.getIterator().hasNext()) {
		    pconfig = xreader.getPDFPageConfig(pagename);
		    createPDFPage(writer, cert, pconfig);
		    pagename = pconfig.getNextPage();
		    //cert.setCurrentlist(cert.getCurrentlist() + 1);
		    document.newPage();
		//}
	}
	
	protected String getCertificateTextByMap(Object object, String map) {

		FSCertificate fs = (FSCertificate) object;
		String str = "";

		if ("exporter".equals(map)) {
			str = renderString(fs.getExporter().getName() + ", " + fs.getExporter().getAddress(), "\n");
		} else if ("branch".equals(map)) {
			str = renderString(fs.getBranch().getName() + ", " + fs.getBranch().getAddress(), "\n");
		} else  {
			
			String getter = convertFieldNameToGetter(map);
			Method m = getMethod(fs, getter, new Class[] {});
			try {
				if (m != null) {
					str =  m.invoke(fs, new Object[]{}).toString();
				}
			} catch (Exception ex) {
				LOG.info("Error data read." + ex.getMessage());
			}
		} 

		return str;
	}

	 private Method getMethod(Object obj, String name, Class[] params) {
			Method m = null;
			try {
				 try {
				    m = obj.getClass().getMethod(name, params);
				 } catch (Exception ex) {
					m = obj.getClass().getSuperclass().getMethod(name, params); 
				 }
			} catch (Exception ex) {
				LOG.info("Method not found: " + ex.getMessage());
			}
			return m;
	 }
	    
	 private String convertFieldNameToGetter(String field) {
			return "get" + field.substring(0, 1).toUpperCase() + field.substring(1).toLowerCase();
	 }

	 private String convertFieldNameToSetter(String field) {
			return "set" + field.substring(0, 1).toUpperCase() + field.substring(1).toLowerCase();
	 }
}

