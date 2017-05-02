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
import cci.model.ClientLocale;
import cci.model.EmployeeLocale;
import cci.model.fscert.FSCertificate;
import cci.model.fscert.FSProduct;
import cci.pdfbuilder.PDFBuilder;
import cci.pdfbuilder.PDFBuilderFactory;
import cci.service.fscert.FSCertificateService;

public class FSPDFBuilder extends PDFBuilder {
	public static Logger LOG=Logger.getLogger(FSPDFBuilder.class);
	
	public static final String FONT_DIR = "resources/fonts/";
	private Document document;
	private PdfWriter writer;
	private PDFConfigReader xreader;
	private PDFPageConfig pconfig;
	private String fontpath;
	private float tableWidth = 460f;
	private float maxHeightTable;
	private float documentMarginTop = 54f;
	private float documentMarginBottom = 54f;
	private float documentMarginLeft = 72f;
	private float documentMarginRight = 72f;
	private float rowsListCountHeight = 26f;
	
	public void createPdf(String outfilename, Object cert,
		String configFileName, String fpath, String countryname, FSCertificate parent, FSCertificateService service, boolean flagOriginal) throws IOException, DocumentException {
		fontpath = fpath;
		// step 1
		document = new Document(PageSize.A4, documentMarginLeft, documentMarginRight, documentMarginTop, documentMarginBottom);
		maxHeightTable = document.getPageSize().getHeight() - documentMarginTop - documentMarginBottom;
		// step 2
		writer = PdfWriter.getInstance(document, new FileOutputStream(outfilename));
		// step 3
		document.open();
		// step 4
		xreader = XMLConfigReader.getInstance(configFileName, fontpath);
		createContent(cert, countryname, parent, service, flagOriginal);
		// step 5
		document.close();
		// step 6
		writer.close();
	}
	
	public int genereatePdf(String outfilename, Object cert,
			String configFileName, String fpath, String countryname, FSCertificate parent, FSCertificateService service, boolean flagOriginal) throws IOException, DocumentException {
			fontpath = fpath;
			int pages = 1;
			// step 1
			document = new Document(PageSize.A4, documentMarginLeft, documentMarginRight, documentMarginTop, documentMarginBottom);
			maxHeightTable = document.getPageSize().getHeight() - documentMarginTop - documentMarginBottom;
			// step 2
			writer = PdfWriter.getInstance(document, new FileOutputStream(outfilename));
			// step 3
			document.open();
			// step 4
			xreader = XMLConfigReader.getInstance(configFileName, fontpath);
			pages = createContent(cert, countryname, parent, service, flagOriginal);
			// step 5
			document.close();
			// step 6
			writer.close();
			return pages;
		}

	
	private int createContent(Object cert, String countryname, FSCertificate parent, FSCertificateService service, boolean flagOriginal) throws DocumentException, IOException {
		int pages;
		String pagename  = PDFBuilderFactory.PAGE_FS;; 
	    pconfig = xreader.getPDFPageConfig(pagename);
	    pages = createPDFPage(writer, cert, pconfig, countryname, parent, service, flagOriginal);
	    pagename = pconfig.getNextPage();
	    return pages;
	}
	
	public int createPDFPage(PdfWriter writer, Object certificate,
					PDFPageConfig pconfig, String countryname, FSCertificate parent, FSCertificateService service, boolean flagOriginal) throws DocumentException, IOException {
		
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
	        // table.getDefaultCell().setBorder(PdfPCell.BOX);
	        table.setTotalWidth(tableWidth);
	        table.setLockedWidth(true);
	        table.setSpacingAfter(0f);
	        table.setSpacingBefore(0f);
	        
	        addBelCCIHeader(table, cert, chFont, 0, flagOriginal);
	        addBranchInfo(table, cert, prgFont);
	        addFSCertificateHeader(table, cert, bigFont, flagOriginal);
	        addFSNumber(table, cert, prgFont);
	        
	        if (cert.getParentnumber() != null && parent != null) {
				 String template = service.getTemplate("dublicate", cert.getLanguage());
  		         template = template.replaceAll("\\[datecert\\]", parent.getDatecert());
  		         template = template.replaceAll("\\[certnumber\\]", parent.getCertnumber());
 	        	 addCellToTable(table, 3, template, Element.ALIGN_LEFT, prgFont, 0f, 0f, 14f);
	        }
	        
	        addCellToTable(table, 3, service.getTemplate("submission", cert.getLanguage()) + countryname, Element.ALIGN_LEFT, prgFont, 0f, 0f, 15f);
	        addCellToTable(table, 3, service.getTemplate("exporter", cert.getLanguage()), Element.ALIGN_LEFT, prgFont, 0f, 0f, 15f);
	        
	        String name = (cert.getExporter() != null && cert.getExporter().getName() != null ? cert.getExporter().getName() : "");
	        String address = (cert.getExporter() != null && cert.getExporter().getAddress() != null ? cert.getExporter().getAddress() : "");
	        		
	        if (!"RU".equals(cert.getLanguage()) && cert.getExporter() != null) {
	           ClientLocale locale = cert.getExporter().getLocale(cert.getLanguage());
	           
	           name = (locale != null && locale.getName() != null ? locale.getName() : "");
	           address = (locale != null && locale.getAddress() != null ? locale.getAddress() : "");
	        } 
	        
	        addCellToTable(table, 3,  name + ", " 
	                                  + address , 
	        						  Element.ALIGN_JUSTIFIED, prgFont, 0f, 0f);
	        
	        addCellToTable(table, 3, cert.getConfirmation(), Element.ALIGN_JUSTIFIED, prgFont, 0f, 0f, 18f);
	        addCellToTable(table, 3, service.getTemplate("listofproducts", cert.getLanguage()), Element.ALIGN_LEFT, prgFont, 0f, 0f, 18f);
            
	        // ------------------START PRODUCT RENDERING ---------------------------
	        int rowIndexBeforeProduct = table.getLastCompletedRowIndex();
	        LOG.debug("rowIndexBeforeProduct = " + rowIndexBeforeProduct);
	        
	        for (FSProduct product : cert.getProducts() ) {
	        	addCellToTable(table, 3, product.getNumerator() + ". " + product.getTovar(), Element.ALIGN_LEFT, prgFont, 12f, 0f);
	        }
	        int rowIndexAfterProduct = table.getLastCompletedRowIndex() + 1;
	        addFooterFirstPage(table, cert, service, prgFont);
	        LOG.debug("rowIndexAfterProduct = " + rowIndexAfterProduct);

	        int nextProductIndex = 1;
	         
	        float finalH = table.getTotalHeight();
	        LOG.debug(finalH);
	        
            if (finalH  > maxHeightTable) {
    	       float height = 0f;                  
	           for (int i=1; i <=rowIndexBeforeProduct; i++) {
	        	   height += table.getRowHeight(i);
	           }
	           height += table.getRowHeight(rowIndexAfterProduct);
	           height += table.getRowHeight(rowIndexAfterProduct+1);
	           height += table.getRowHeight(rowIndexAfterProduct+2);
	           height += rowsListCountHeight;
	        
	           int lastIndex = rowIndexBeforeProduct;
	           while (height < maxHeightTable) {     
	        	   height += table.getRowHeight(++lastIndex);
	           }
	           if (height > maxHeightTable) lastIndex--; 
	           
	           for (int j = rowIndexAfterProduct+2; j > rowIndexBeforeProduct; j--) {
	        	   table.deleteRow(j);
	           }
	           
   	           for (FSProduct product : cert.getProducts() ) {
		        	addCellToTable(table, 3, product.getNumerator() + ". " + product.getTovar(), Element.ALIGN_LEFT, prgFont, 12f, 0f);
		        	nextProductIndex++;
		        	if (table.getLastCompletedRowIndex() == lastIndex ) { break;}
		       }
   	           
			   String template = service.getTemplate("listscount", cert.getLanguage());
  		       template = template.replaceAll("\\[listscount\\]", "" + (cert.getListscount() != null ? cert.getListscount() - 1 : ""));
  		          	           
   	           addCellToTable(table, 3, template, Element.ALIGN_LEFT, prgFont, 0f, 0f, 5f);
   	           addFooterFirstPage(table, cert, service, prgFont);
            } 
	        LOG.debug("Document Height: " + document.getPageSize().getHeight()); 
	        LOG.debug("  Table Height: " + table.getTotalHeight());
            LOG.debug("  Max Table Height: " + maxHeightTable);
            
	        document.add(table);
	        
	        // ------------------- next page rendering ----------------------------
	        while (nextProductIndex > 1) {
	        	
	            document.newPage();
	            pagecount++;
	            table = new PdfPTable(3);
		        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	            //table.getDefaultCell().setBorder(PdfPCell.BOX);
		        table.setTotalWidth(tableWidth);
		        table.setLockedWidth(true);
		        table.setSpacingAfter(0f);
		        table.setSpacingBefore(0f);
	            
		        addBelCCIHeader(table, cert, chFont, pagecount -1 ,flagOriginal);
		        addBranchInfo(table, cert, prgFont);
		        addFSCertificateHeader(table, cert, bigFont, flagOriginal);
		        addFSNumber(table, cert, prgFont);
		        addCellToTable(table, 3, service.getTemplate("annex", cert.getLanguage()) , Element.ALIGN_LEFT, prgFont, 0f, 0f, 15f);
		        
		        rowIndexBeforeProduct = table.getLastCompletedRowIndex();
		        LOG.debug("rowIndexBeforeProduct = " + rowIndexBeforeProduct);
		        
		        for (int i = nextProductIndex-1; i < cert.getProducts().size(); i++ ) {
		        	FSProduct product = cert.getProducts().get(i);
		        	addCellToTable(table, 3, product.getNumerator() + ". " + product.getTovar(), Element.ALIGN_LEFT, prgFont, 12f, 0f);
		        }
		        addFooterNextPage(table, cert, prgFont);
		        rowIndexAfterProduct = table.getLastCompletedRowIndex();
		        
		        finalH = table.getTotalHeight();
		        LOG.debug("Next page height:" + finalH);
		        
		        if (finalH > maxHeightTable) {
	    	       float height = 0f;                  
		           for (int i=0; i <=rowIndexBeforeProduct; i++) {
		        	   height += table.getRowHeight(i);
		           }
		           height += table.getRowHeight(rowIndexAfterProduct);
		        
		           int lastIndex = rowIndexBeforeProduct;
		           while (height < maxHeightTable) {
		        	   height += table.getRowHeight(++lastIndex);
		           }
		           if (height > maxHeightTable) lastIndex--;
		           
		           for (int j = rowIndexAfterProduct; j > rowIndexBeforeProduct; j--) {
		        	   table.deleteRow(j);
		           }
		           
		           for (int j = nextProductIndex -1; j < cert.getProducts().size(); j++ ) {
			        	FSProduct product = cert.getProducts().get(j); 
			        	addCellToTable(table, 3, product.getNumerator() + ". " + product.getTovar(), Element.ALIGN_LEFT, prgFont, 12f, 0f);
			        	nextProductIndex++;
			        	if (table.getLastCompletedRowIndex() == lastIndex) { break;}
			       }
	   	           
	   	           addFooterNextPage(table, cert, prgFont);
	            } else {
	            	nextProductIndex = 0;
	            }
		        LOG.debug("================================>  Document Height: " + document.getPageSize().getHeight()); 
		        LOG.debug(" Table Height: " + table.getTotalHeight());
		        LOG.debug(" Max Table Height: " + maxHeightTable);
		        document.add(table);
	        }
	        
	        return pagecount;
	}	
	
	private String notNull(String str) {
		return str == null ? "" : str;		
	}
	
	private void printHeightRows(PdfPTable table) {
		for (int jj = 0; jj < table.getRows().size() ; jj++) {
        	LOG.info("Row " + jj + ": "+ table.getRowHeight(jj));
        }
	}

	private void addBelCCIHeader(PdfPTable table, FSCertificate cert, Font font, int pageNumber, boolean flagOriginal) {
		if (flagOriginal) {
			//addCellToTable(table, 3, 47.78f);
			addCellToTable(table, 3, " ", Element.ALIGN_RIGHT, font, 0f, 0f);
			addCellToTable(table, 3, " ", Element.ALIGN_CENTER, font, 18f, 18f, 9f);
			addCellToTable(table, 3, " ", Element.ALIGN_CENTER, font, 18f, 18f);
			
		} else {
			if  (cert.getBlanks() != null && cert.getBlanks().size() > 0 && pageNumber < cert.getBlanks().size()) {
			  addCellToTable(table, 3, cert.getBlanks().get(pageNumber).getBlanknumber(), Element.ALIGN_RIGHT, font, 0f, 0f);
			} else {
			  addCellToTable(table, 3, " ", Element.ALIGN_RIGHT, font, 0f, 0f);	
			}
			addCellToTable(table, 3, "БЕЛОРУССКАЯ ТОРГОВО-ПРОМЫШЛЕННАЯ ПАЛАТА", Element.ALIGN_CENTER, font, 18f, 18f, 9f);
			addCellToTable(table, 3, "BELARUS CHAMBER OF COMMERCE AND INDUSTRY", Element.ALIGN_CENTER, font, 18f, 18f);
		}
	}

	private void addBranchInfo(PdfPTable table, FSCertificate cert, Font font) {
		if (cert.getBranch() != null) {
			String name="";
			
			if ("RU".equals(cert.getLanguage())) {
				name = cert.getBranch().getName();
				name = name.replaceFirst("услуг", "услуг\n");   	
			} else {
				ClientLocale locale = cert.getBranch().getLocale("EN");
				name = locale.getName();
				name = name.replaceFirst("services", "services\n");
			}
			  		
			addCellToTable(table, 3, name, Element.ALIGN_CENTER, font, 20f, 20f, 8f);
			if ("RU".equals(cert.getLanguage())) { 
			    addCellToTable(table, 3, cert.getBranch().getAddress(), Element.ALIGN_CENTER, font, 20f, 20f);
			} else {
				ClientLocale locale = cert.getBranch().getLocale("EN");
				addCellToTable(table, 3, locale.getAddress(), Element.ALIGN_CENTER, font, 20f, 20f);
			}
			
			addCellToTable(table, 3, ("RU".equals(cert.getLanguage()) ? "телефон: " : "phone: ") 
					                  + notNull(cert.getBranch().getPhone())   
					                  + ("RU".equals(cert.getLanguage()) ? " факс: " : " fax: ") + notNull(cert.getBranch().getCell()) 
        		                      + "  e-mail: " + notNull(cert.getBranch().getEmail()), Element.ALIGN_CENTER, font, 30f, 30f);
		}
	}
	
	private void addFSCertificateHeader(PdfPTable table, FSCertificate cert, Font font, boolean flagOriginal) {
		if (flagOriginal) {
			//addCellToTable(table, 3, 108.36f);
			addCellToTable(table, 3, " ", Element.ALIGN_CENTER, font, 72f, 72f, 20f);
			addCellToTable(table, 3, " ", Element.ALIGN_CENTER, font, 72f, 72f);
			addCellToTable(table, 3, " ", Element.ALIGN_CENTER, font, 72f, 72f, 28f);
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

	private void addFooterFirstPage(PdfPTable table, FSCertificate cert, FSCertificateService service, Font font) {
        addCellToTable(table, 3, cert.getDeclaration(), Element.ALIGN_JUSTIFIED, font, 0f, 0f, 18f);
        
		String template = service.getTemplate("valid", cert.getLanguage());
		if (cert.getDateissue() != null) {
			template = template.replaceAll("\\[datestart\\]", cert.getDateissue());
		}
		if (cert.getDateexpiry() != null) {
			template = template.replaceAll("\\[dateexpiry\\]", cert.getDateexpiry());
		}
	    
        addCellToTable(table, 3, template, 
        		          		Element.ALIGN_LEFT, font, 0f, 0f, 18f);
        
        String name = (cert.getSigner() != null && cert.getSigner().getName() != null ? cert.getSigner().getName() : "");
        String job = (cert.getSigner() != null && cert.getSigner().getJob() != null ? toFirstUppercase(cert.getSigner().getJob()) : "");
        		
        if (!"RU".equals(cert.getLanguage())) {
        	EmployeeLocale locale = cert.getSigner().getLocale(cert.getLanguage());
        	job = (locale!=null && locale.getJob() != null) ? toFirstUppercase(locale.getJob()) : "";
        	name = (locale!=null && locale.getName() != null) ? locale.getName() : "";
        }
        
        addCellToTable(table, 1, job, Element.ALIGN_LEFT, font, 0f, 0f, 30f);
        addCellToTable(table, 1, "", Element.ALIGN_CENTER, font, 0f, 0f, 30f);
        addCellToTable(table, 1, name, Element.ALIGN_RIGHT, font, 0f, 0f, 30f);
	}

	private String toFirstUppercase(String job) {
		return job.substring(0, 1).toUpperCase() + job.substring(1); 
	}

	private void addFooterNextPage(PdfPTable table, FSCertificate cert, Font font) {
		String name = (cert.getSigner() != null && cert.getSigner().getName() != null ? cert.getSigner().getName() : "");
        String job = (cert.getSigner() != null && cert.getSigner().getJob() != null ? toFirstUppercase(cert.getSigner().getJob()) : "");
	        		
        if (!"RU".equals(cert.getLanguage())) {
        	EmployeeLocale locale = cert.getSigner().getLocale(cert.getLanguage());
        	job = (locale!=null && locale.getJob() != null) ? toFirstUppercase(locale.getJob()) : "";
        	name = (locale!=null && locale.getName() != null) ? locale.getName() : "";
        }
	        
        addCellToTable(table, 1, job, Element.ALIGN_LEFT, font, 0f, 0f, 30f);
        addCellToTable(table, 1, "", Element.ALIGN_CENTER, font, 0f, 0f, 30f);
        addCellToTable(table, 1, name, Element.ALIGN_RIGHT, font, 0f, 0f, 30f);
	}

	
	private void addCellToTable(PdfPTable table, int colspan,  float fixedHeight) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.NO_BORDER);
        //cell.setBorder(PdfPCell.BOX);
        cell.setFixedHeight(fixedHeight);
        table.addCell(cell);
     }
	
	private void addCellToTable(PdfPTable table, int colspan, String text, int alg, Font font, float left, float right, float spacingbefore) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.NO_BORDER);
        //cell.setBorder(PdfPCell.BOX);
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
        //cell.setBorder(PdfPCell.BOX);
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

