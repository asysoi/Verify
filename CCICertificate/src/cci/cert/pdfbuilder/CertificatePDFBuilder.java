package cci.cert.pdfbuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cci.cert.config.PDFConfigReader;
import cci.cert.config.PDFPageConfig;
import cci.cert.config.XMLConfigReader;
import cci.cert.model.Certificate;
import cci.cert.model.Product;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class CertificatePDFBuilder {
	public static final String FONT = "c:/windows/fonts/ARIAL.TTF";
	private Document document;
	private PdfWriter writer;
	private PDFConfigReader xreader;
	private PDFPageConfig pconfig;
	
	public static void main(String[] arg) {
		CertificatePDFBuilder builder = new CertificatePDFBuilder();
		Certificate cert = new Certificate();
		cert.setKontrp("Exporter");
		
		List<Product> products = new ArrayList<Product>();
		Product product = new Product();
		product.setNumerator("1");
		product.setTovar("Tovar");
		product.setVidup("vidup");
		product.setVes("ves");
		product.setSchet("schet");
		products.add(product);
		cert.setProducts(products);
		
		try {
		   builder.createPdf("d:\\temp\\cert\\certificate.pdf", cert, "D:\\Java\\git\\CCICertificate\\CCICertificate\\WebContent\\resources\\config\\pages.xml");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void createPdf(String outfilename, Certificate cert,
			String configFileName) throws IOException, DocumentException {
		// step 1
		document = new Document(PageSize.A4, 5, 5, 5, 5);

		// step 2
		writer = PdfWriter.getInstance(document,
				new FileOutputStream(outfilename));
		// step 3
		document.open();

		// step 4
		xreader = XMLConfigReader.getInstance(configFileName);
		createMain(cert);

		// step 5
		document.close();
	}
	
	

	private void createMain(Certificate cert) throws DocumentException, IOException {
		String pagename; 
		
		if (cert.getForms() != null) {
			System.out.println("Form name: " + cert.getForms());
			pagename = cert.getForms().trim();
		} else {
			System.out.println("Form name: NULL");
			pagename = PDFBuilderFactory.PAGE_CT1;
		}
		
		cert.setCurrentlist(0);  // start from main certification list
		
		while (cert.getIterator().hasNext()) {
		    pconfig = xreader.getPDFPageConfig(pagename);
		    PDFBuilder pmaker = PDFBuilderFactory.getPADFBuilder(pagename);
		    pmaker.createPDFPage(writer, cert, pconfig);
		    pagename = pconfig.getNextPage();
		    cert.setCurrentlist(cert.getCurrentlist() + 1);
;		    document.newPage();
		}
	}
	
}
