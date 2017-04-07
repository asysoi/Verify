package cci.pdfbuilder.fscert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import cci.config.cert.PDFConfigReader;
import cci.config.cert.PDFPageConfig;
import cci.config.cert.XMLConfigReader;
import cci.model.fscert.FSCertificate;
import cci.model.fscert.FSProduct;
import cci.pdfbuilder.PDFBuilder;
import cci.pdfbuilder.PDFBuilderFactory;

public class FSPDFBuilder extends PDFBuilder {
	public static Logger LOG=Logger.getLogger(FSPDFBuilder.class);
	
	public static final String FONT_DIR = "resources/fonts/";
	private Document document;
	private PdfWriter writer;
	private PDFConfigReader xreader;
	private PDFPageConfig pconfig;
	private String fontpath;
	private float maxHeightTable = 685f;
	
	public void createPdf(String outfilename, Object cert,
			String configFileName, String fpath, String countryname, boolean flagOriginal) throws IOException, DocumentException {
		fontpath = fpath;
		// step 1
		document = new Document(PageSize.A4, 72f, 72f, 54f, 54f);
		// step 2
		writer = PdfWriter.getInstance(document, new FileOutputStream(outfilename));
		// step 3
		document.open();
		// step 4
		xreader = XMLConfigReader.getInstance(configFileName, fontpath);
		createContent(cert, countryname, flagOriginal);
		// step 5
		document.close();
	}
	
	private void createContent(Object cert, String countryname, boolean flagOriginal) throws DocumentException, IOException {
		String pagename  = PDFBuilderFactory.PAGE_FS;; 
	    pconfig = xreader.getPDFPageConfig(pagename);
	    createPDFPage(writer, cert, pconfig, countryname, flagOriginal);
	    pagename = pconfig.getNextPage();

	}
	
	public int createPDFPage(PdfWriter writer, Object certificate,
					PDFPageConfig pconfig, String countryname, boolean flagOriginal) throws DocumentException, IOException {
		
		    int pagecount = 1;
		    FSCertificate cert = (FSCertificate) certificate;
		    BaseFont bf = BaseFont.createFont(
					fontpath + "ARIAL.ttf", 
					BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
		    
		    Font chFont = new Font(bf, 12, Font.BOLD);
		    Font bigFont = new Font(bf, 20, Font.BOLD);
	        Font prgFont = new Font(bf, 12, Font.NORMAL);;
	        
	        PdfPTable table = new PdfPTable(3);
	        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	        table.setTotalWidth(460f);
	        table.setLockedWidth(true);
	        
	        addBelCCIHeader(table, cert, chFont, 0, flagOriginal);
	        addBranchInfo(table, cert, prgFont);
	        addFSCertificateHeader(table, cert, bigFont, flagOriginal);
	        addFSNumber(table, cert, prgFont);
	        
	        if (cert.getParentnumber() != null) {
	        	addCellToTable(table, 3, "Дубликат сертификата от ХХХХХХ № " + cert.getParentnumber(), Element.ALIGN_LEFT, prgFont, 0f, 0f, 14f);
	        }
	        addCellToTable(table, 3, "Выдан для предоставления в : " + countryname, Element.ALIGN_LEFT, prgFont, 0f, 0f, 15f);
	        
	        addCellToTable(table, 3, "Экспортер : ", Element.ALIGN_LEFT, prgFont, 0f, 0f, 15f);
	        addCellToTable(table, 3, cert.getExporter().getName() + ", " + cert.getExporter().getAddress(), 
	        						Element.ALIGN_JUSTIFIED, prgFont, 0f, 0f);
	        
	        addCellToTable(table, 3, cert.getConfirmation(), Element.ALIGN_JUSTIFIED, prgFont, 0f, 0f, 18f);
	        addCellToTable(table, 3, "Перечень товаров: ", Element.ALIGN_LEFT, prgFont, 0f, 0f, 18f);

	        int rowIndexBeforeProduct = table.getLastCompletedRowIndex();
	        // printHeightRows(table, rowIndexBeforeProduct);
	        
	        for (FSProduct product : cert.getProducts() ) {
	        	addCellToTable(table, 3, product.getNumerator() + ". " + product.getTovar(), Element.ALIGN_LEFT, prgFont, 12f, 0f);
	        }
	        int rowIndexAfterProduct = table.getLastCompletedRowIndex() + 1;
	        addFooterFirstPage(table, cert, prgFont);

	        int nextProductIndex = 0;
	         
	        float finalH = table.getTotalHeight();
	        LOG.info(finalH);
	        
            if (finalH  > (maxHeightTable - 26f)) {
    	       float height = 0f;                  
	           for (int i=1; i <=rowIndexBeforeProduct; i++) {
	        	   height += table.getRowHeight(i);
	           }
	           height += table.getRowHeight(rowIndexAfterProduct);
	           height += table.getRowHeight(rowIndexAfterProduct+1);
	           height += table.getRowHeight(rowIndexAfterProduct+2);
	        
	           int i = rowIndexBeforeProduct + 1;
	           while (height < (maxHeightTable - 26f)) {
	        	   height += table.getRowHeight(i++);
	           }
	           
	           for (int j = rowIndexAfterProduct+2; j > rowIndexBeforeProduct; j--) {
	        	   table.deleteRow(j);
	           }
	           
   	           for (FSProduct product : cert.getProducts() ) {
		        	addCellToTable(table, 3, product.getNumerator() + ". " + product.getTovar(), Element.ALIGN_LEFT, prgFont, 12f, 0f);
		        	nextProductIndex++;
		        	if (table.getLastCompletedRowIndex() == (i-1)) { break;}
		       }
   	           
   	           addCellToTable(table, 3, "Смотри продолжение на ______ ", Element.ALIGN_LEFT, prgFont, 0f, 0f, 5f);
   	           addFooterFirstPage(table, cert, prgFont);
            }
            
	        document.add(table);
	        // first page finished
	        
	        if (nextProductIndex > 0) {
	        	
	            document.newPage();
	            pagecount++;
	            table = new PdfPTable(3);
		        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		        table.setTotalWidth(460f);
		        table.setLockedWidth(true);
	            
		        addBelCCIHeader(table, cert, chFont, 1,flagOriginal);
		        addBranchInfo(table, cert, prgFont);
		        addFSCertificateHeader(table, cert, bigFont, flagOriginal);
		        addFSNumber(table, cert, prgFont);
		        addCellToTable(table, 3, "Продолжение перечня товаров:", Element.ALIGN_LEFT, prgFont, 0f, 0f, 15f);
		        
		        rowIndexBeforeProduct = table.getLastCompletedRowIndex();
		        for (int i = nextProductIndex; i < cert.getProducts().size(); i++ ) {
		        	FSProduct product = cert.getProducts().get(i);
		        	addCellToTable(table, 3, product.getNumerator() + ". " + product.getTovar(), Element.ALIGN_LEFT, prgFont, 12f, 0f);
		        	LOG.info(product.getNumerator() + ". " + product.getTovar());
		        }
		        rowIndexAfterProduct = table.getLastCompletedRowIndex() + 1;
		        addFooterNextPage(table, cert, prgFont);
		        finalH = table.getTotalHeight();
		        LOG.info("Next page height:" + finalH);
		        
	            if (finalH > maxHeightTable) {
	    	       float height = 0f;                  
		           for (int i=1; i <=rowIndexBeforeProduct; i++) {
		        	   height += table.getRowHeight(i);
		           }
		           height += table.getRowHeight(rowIndexAfterProduct);
		        
		           int i = rowIndexBeforeProduct + 1;
		           while (height < maxHeightTable) {
		        	   height += table.getRowHeight(i++);
		           }
		           
		           for (int j = rowIndexAfterProduct; j > rowIndexBeforeProduct; j--) {
		        	   table.deleteRow(j);
		           }
		           
		           nextProductIndex = 0;
			       for (int j = nextProductIndex +1; j < cert.getProducts().size(); j++ ) {
			        	FSProduct product = cert.getProducts().get(j); 
			        	addCellToTable(table, 3, product.getNumerator() + ". " + product.getTovar(), Element.ALIGN_LEFT, prgFont, 12f, 0f);
			        	nextProductIndex++;
			        	if (table.getLastCompletedRowIndex() == (i-1)) { break;}
			       }
	   	           
	   	           addFooterNextPage(table, cert, prgFont);
	            }
		         
		        document.add(table);
	        }
	        return pagecount;
	}	
	
	
	private void printHeightRows(PdfPTable table, int jjj) {
		for (int jj = 0; jj < jjj; jj++) {
        	LOG.info("Row " + jj + ": "+ table.getRowHeight(jj));
        }
	}

	private void addBelCCIHeader(PdfPTable table, FSCertificate cert, Font font, int pageNumber, boolean flagOriginal) {
		if (flagOriginal) {
			addCellToTable(table, 3, 47.78f);
		} else {
			addCellToTable(table, 3, cert.getBlanks().get(pageNumber).getBlanknumber(), Element.ALIGN_RIGHT, font, 0f, 0f);
			addCellToTable(table, 3, "БЕЛОРУССКАЯ ТОРГОВО-ПРОМЫШЛЕННАЯ ПАЛАТА", Element.ALIGN_CENTER, font, 18f, 18f, 9f);
			addCellToTable(table, 3, "BELARUS CHAMBER OF COMMERCE AND INDUSTRY", Element.ALIGN_CENTER, font, 18f, 18f);
		}
	}

	private void addBranchInfo(PdfPTable table, FSCertificate cert, Font font) {
		if (cert.getBranch() != null) {
			String name = cert.getBranch().getName();
			name = name.replaceFirst("услуг", "услуг\n");  		
			addCellToTable(table, 3, name, Element.ALIGN_CENTER, font, 20f, 20f, 8f);
			addCellToTable(table, 3, cert.getBranch().getAddress(), Element.ALIGN_CENTER, font, 20f, 20f);
			addCellToTable(table, 3, "телефон: "+ cert.getBranch().getPhone() +"  факс: "+ cert.getBranch().getCell() 
        		+ "  e-mail: " + cert.getBranch().getEmail(), Element.ALIGN_CENTER, font, 30f, 30f);
		}
	}
	
	private void addFSCertificateHeader(PdfPTable table, FSCertificate cert, Font font, boolean flagOriginal) {
		if (flagOriginal) {
			addCellToTable(table, 3, 108.36f);
		} else {
			addCellToTable(table, 3, "СЕРТИФИКАТ", Element.ALIGN_CENTER, font, 72f, 72f, 20f);
			addCellToTable(table, 3, "СВОБОДНОЙ ПРОДАЖИ", Element.ALIGN_CENTER, font, 72f, 72f);
			addCellToTable(table, 3, "CERTIFICATE OF FREE SALE", Element.ALIGN_CENTER, font, 72f, 72f, 28f);
		}
    }
	
	private void addFSNumber(PdfPTable table, FSCertificate cert, Font font) {
        addCellToTable(table, 1, cert.getCertnumber(), Element.ALIGN_LEFT, font, 0f, 0f,14f);
        addCellToTable(table, 1, "", Element.ALIGN_CENTER, font, 0f, 0f, 14f);
        addCellToTable(table, 1, cert.getDatecert(), Element.ALIGN_RIGHT, font, 0f, 0f, 14f);
	}

	private void addFooterFirstPage(PdfPTable table, FSCertificate cert, Font font) {
        addCellToTable(table, 3, cert.getDeclaration(), Element.ALIGN_JUSTIFIED, font, 0f, 0f, 18f);
        addCellToTable(table, 3, "Срок действия с " + cert.getDateissue() + " по " + cert.getDateexpiry() + "  включительно.", 
        		          		Element.ALIGN_LEFT, font, 0f, 0f, 18f);
        addCellToTable(table, 1, cert.getSigner().getJob(), Element.ALIGN_LEFT, font, 0f, 0f, 30f);
        addCellToTable(table, 1, "", Element.ALIGN_CENTER, font, 0f, 0f, 30f);
        addCellToTable(table, 1, cert.getSigner().getName(), Element.ALIGN_RIGHT, font, 0f, 0f, 30f);
	}

	private void addFooterNextPage(PdfPTable table, FSCertificate cert, Font font) {
        addCellToTable(table, 1, cert.getSigner().getJob(), Element.ALIGN_LEFT, font, 0f, 0f, 30f);
        addCellToTable(table, 1, "", Element.ALIGN_CENTER, font, 0f, 0f, 30f);
        addCellToTable(table, 1, cert.getSigner().getName(), Element.ALIGN_RIGHT, font, 0f, 0f, 30f);
	}

	
	private void addCellToTable(PdfPTable table, int colspan,  float fixedHeight) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setFixedHeight(fixedHeight);
        table.addCell(cell);
     }
	
	private void addCellToTable(PdfPTable table, int colspan, String text, int alg, Font font, float left, float right, float spacingbefore) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.NO_BORDER);
        Paragraph pg = new Paragraph(text, font);
        pg.setAlignment(alg);
        pg.setIndentationLeft(left);
        pg.setIndentationRight(right);
        pg.setLeading(0f, 1.1f);
        cell.setLeading(0f, 1.1f);
        cell.setHorizontalAlignment(alg);
        cell.setPadding(0f);
        cell.setPaddingTop(spacingbefore);
        cell.setPaddingBottom(1f);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.addElement(pg);
        table.addCell(cell);
     }
    
    private void addCellToTable(PdfPTable table, int colspan, String text, int alg, Font font, float left, float right) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.NO_BORDER);
        Paragraph pg = new Paragraph(text, font);
        pg.setAlignment(alg);
        pg.setIndentationLeft(left);
        pg.setIndentationRight(right);
        pg.setLeading(0f, 1.1f);
        cell.setLeading(0f, 1.1f);
        cell.setHorizontalAlignment(alg);
        cell.setPadding(0f);
        cell.setPaddingTop(1f);
        cell.setPaddingBottom(1f);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.addElement(pg);
        table.addCell(cell);
     }

    
     private void rrrrr() throws DocumentException, FileNotFoundException {
    	    String TARGET = "temp.pdf";
    	    Document document = new Document(PageSize.A4);
    	    PdfWriter writer = 
    	        PdfWriter.getInstance(document, new FileOutputStream(TARGET));
    	    document.open();
    	    PdfPTable table = new PdfPTable(7);         
    	 
    	    for (int i = 0; i < 700; i++) {
    	        Phrase p = new Phrase("some text");
    	        PdfPCell cell = new PdfPCell();
    	        cell.addElement(p);
    	        table.addCell(cell);            
    	    }
    	 
    	    table.setTotalWidth(PageSize.A4.getWidth());
    	    table.setLockedWidth(true);
    	    PdfContentByte canvas = writer.getDirectContent();
    	    PdfTemplate template = canvas.createTemplate(
    	        table.getTotalWidth(), table.getTotalHeight());
    	    table.writeSelectedRows(0, -1, 0, table.getTotalHeight(), template);
    	    Image img = Image.getInstance(template);
    	    img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
    	    img.setAbsolutePosition(
    	        0, (PageSize.A4.getHeight() - table.getTotalHeight()) / 2);
    	    document.add(img);
    	    document.close();
     }
    
	
	//--------------------------------------------------------------------------------------------
	//  Utils
	//--------------------------------------------------------------------------------------------
	protected String getTextByMap(Object object, String map) {
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

