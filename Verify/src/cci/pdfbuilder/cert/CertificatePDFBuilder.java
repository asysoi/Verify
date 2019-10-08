package cci.pdfbuilder.cert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import cci.config.cert.PDFConfigReader;
import cci.config.cert.PDFPageConfig;
import cci.config.cert.XMLConfigReader;
import cci.model.cert.Certificate;
import cci.model.cert.Product;
import cci.model.owncert.OwnCertificate;
import cci.pdfbuilder.PDFBuilder;
import cci.pdfbuilder.PDFBuilderFactory;
import cci.repository.cert.CertificateDAO;
import cci.repository.cert.JDBCCertificateDAO;
import cci.web.controller.cert.exception.CertificateGetException;
import cci.web.controller.cert.exception.NotFoundCertificateException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class CertificatePDFBuilder {
	private static final Logger LOG = Logger.getLogger(CertificatePDFBuilder.class);	
	public static final String FONT_DIR = "resources/fonts/";
	static NamedParameterJdbcTemplate template; 
	private Document document;
	private PdfWriter writer;
	private PDFConfigReader xreader;
	private PDFPageConfig pconfig;
	private String fontpath;
	
	public static void main(String[] arg) {
		CertificatePDFBuilder builder = new CertificatePDFBuilder();
        DataSource datasource = getDataSource();
        template = new NamedParameterJdbcTemplate(datasource);
        
        //List<Integer> ids = template.query("SELECT cert_id FROM c_cert", new IDMapper<Integer>());
        //System.out.println("Список IDs получен : " + ids.size() );
        
        
        
//		long start = System.currentTimeMillis();
//        int pagesize = 100;
//        int pagecount = 940818/pagesize;
//        
//        for (int page = pagecount; page > 0; page--) {
//        	List<Certificate> certs = findViewNextPage(page, pagesize); 
//        
//        	for (Certificate certificate : certs) {
//        		Certificate cert = getCertificate(Integer.valueOf(certificate.getCert_id()+""));
        
                Certificate cert = getCertificate(Integer.valueOf(1342498));
        		if (cert != null) {
        			try {
        				builder.createPdf("c:\\tmp\\cert\\certificate_" + cert.getCert_id() +".pdf", cert, "C:\\Java\\git\\Verify\\Verify\\WebContent\\resources\\config\\pages.xml", "c:\\Windows\\Fonts\\");
        				// 	System.out.println(" сертификат воспроизведен ");            		
        			} catch(Exception ex) {
        				System.out.println(" ID = " + cert.getCert_id() + " : ошибка формирования сертификата -  " + ex.getMessage());
        			}
        		}  else {
        			System.out.println(" ID = " + " : сертификат не найден или не загружен");	
        		}
//        	}
//    		System.out.println(page + ". " + (System.currentTimeMillis() - start)/1000);
//    		start = System.currentTimeMillis();
//        }
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
			// System.out.println("Form name: " + cert.getForms());
			pagename = cert.getForms().trim();
		} else {
			// System.out.println("Form name: NULL");
			pagename = PDFBuilderFactory.PAGE_CT1;
		}
		
		cert.setCurrentlist(0);  // start from main certification list
		
		while (cert.getIterator().hasNext()) {
			// System.out.println("Номер листа: " + cert.getCurrentlist());
		    pconfig = xreader.getPDFPageConfig(pagename);
		    PDFBuilder pmaker = PDFBuilderFactory.getPADFBuilder(pagename);
		    pmaker.createPDFPage(writer, cert, pconfig);
		    pagename = pconfig.getNextPage();
		    cert.setCurrentlist(cert.getCurrentlist() + 1);
		    document.newPage();
		}
	}
	
	// --------------------------------------------------------------------------------
	//  Database access functions
	// --------------------------------------------------------------------------------
	public static DataSource getDataSource() {
	    DriverManagerDataSource datasource = new DriverManagerDataSource();
	    datasource.setDriverClassName("oracle.jdbc.OracleDriver");
	    datasource.setUrl("jdbc:oracle:thin:@//192.168.0.10:1521/orclpdb");
	    datasource.setUsername("beltpp");
	    datasource.setPassword("123456");
	    return datasource;
	}
	
	// ---------------------------------------------------------------
	// вернуть очередную страницу списка сертификатов XXX
	// ---------------------------------------------------------------
	public static List<Certificate> findViewNextPage(int page, int pagesize) {
			String sql;
	        Map<String, Object> params = new HashMap<String, Object>();
	        
        	sql = "select * "  
					+ " from cert_view where cert_id in "
					+ " (select  a.cert_id "
					+ " from (SELECT cert_id FROM (select cert_id from cert_view "
					+ ") where rownum <= :highposition "    
					+ ") a left join (SELECT cert_id FROM (select cert_id from cert_view "
					+ ") where rownum <= :lowposition "   
					+ ") b on a.cert_id = b.cert_id where b.cert_id is null)";
	       		params.put("highposition", Integer.valueOf(page * pagesize));
	    		params.put("lowposition", Integer.valueOf((page - 1) * pagesize));
			
		    return template.query(sql,	params, 
					new BeanPropertyRowMapper<Certificate>(Certificate.class));
	}
	
	public static Certificate getCertificate(Integer id) {
		Certificate rcert = null;
		try {
			String sql = "select * from CERT_VIEW WHERE cert_id=?" ;
			rcert = template.getJdbcOperations().queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			if (rcert != null) {
				sql = "select * from C_PRODUCT WHERE cert_id = ? ORDER BY product_id";
				rcert.setProducts(template.getJdbcOperations().query(sql, new Object[] { id },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (EmptyResultDataAccessException ex) {
			System.out.println("Certificate find error: " + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Certificate loading error: " + ex.getMessage());
		}

		return rcert;
	}
	
	
	
}
