package cci.pdfbuilder.cert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cci.config.cert.PDFConfigReader;
import cci.config.cert.PDFPageConfig;
import cci.config.cert.XMLConfigReader;
import cci.model.cert.Certificate;
import cci.model.cert.Product;
import cci.pdfbuilder.PDFBuilder;
import cci.pdfbuilder.PDFBuilderFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class CertificatePDFBuilder {
	public static final String FONT_DIR = "resources/fonts/";
	private Document document;
	private PdfWriter writer;
	private PDFConfigReader xreader;
	private PDFPageConfig pconfig;
	private String fontpath;
	
	public static void main(String[] arg) {
		CertificatePDFBuilder builder = new CertificatePDFBuilder();
		Certificate cert = new Certificate();
		cert.setKontrp("Exporter Address Exporter");
		cert.setKontrp("����������");
		cert.setImporter("Importer Importer Importer Importer Importer Importer Importer Importer Importer Importer Importer Importer");
		cert.setMarshrut("by track");
		cert.setNomercert("BYAZ1234567890");
		cert.setNblanka("000678");
		cert.setDatacert("23.12.2016");
		cert.setStranap("�����������");
		cert.setStranapr("���������� ��������");
		cert.setStranav("�����������");
		cert.setEotd_name("Minsk branch of BelCCI");
		cert.setExpert("Minsk");
		cert.setRukovod("Minsk");
		cert.setOtmetka("Note about textil certificate");
		cert.setOtmetka("������� � ����� 5 �� ������ ������� ...");
		cert.setEotd_addr_city("Minsk");
		cert.setCategory("Category");
		
		cert.setForms(PDFBuilderFactory.PAGE_CT1);
		
		List<Product> products = new ArrayList<Product>();
		String tovar = " ��� ����� ";
		
		for (int i = 1; i < 50; i++) {
				Product product = new Product();
				product.setNumerator(i + ".");
				product.setTovar("Tovar " + tovar);
				product.setVidup("vidup " + i);
				product.setVes("ves " + i);
				product.setSchet("schet");
				products.add(product);
				tovar += " | XXXXXXXXXXXXXXXXXXXXX ";
		}
		cert.setProducts(products);
		
		try {
		   builder.createPdf("c:\\tmp\\cert\\certificate.pdf", cert, "C:\\Java\\git\\Verify\\Verify\\WebContent\\resources\\config\\pages.xml", "c:\\Windows\\Fonts\\");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void createPdf(String outfilename, Certificate cert,
			String configFileName, String fpath) throws IOException, DocumentException {
		
		fontpath = fpath;
		
		// step 1
		document = new Document(PageSize.A4, 0, 0, 0, 0);

		// step 2
		writer = PdfWriter.getInstance(document,
				new FileOutputStream(outfilename));
		// step 3
		document.open();

		// step 4
		xreader = XMLConfigReader.getInstance(configFileName, fontpath);
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
			System.out.println("����� �����: " + cert.getCurrentlist());
		    pconfig = xreader.getPDFPageConfig(pagename);
		    PDFBuilder pmaker = PDFBuilderFactory.getPADFBuilder(pagename);
		    pmaker.createPDFPage(writer, cert, pconfig);
		    pagename = pconfig.getNextPage();
		    cert.setCurrentlist(cert.getCurrentlist() + 1);
		    document.newPage();
		}
	}
	
}
