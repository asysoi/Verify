package cci.cert.pdfbuilder;

public class PDFBuilderFactory {
	  public static final String PAGE_CT1 = "CT-1";
	  public static final String PAGE_CT1b = "CT-1b";
	  
      public static PDFBuilder getPADFBuilder(String pagename) {
    	  PDFBuilder builder = null; 
    	  
    	  if (pagename.equals(PAGE_CT1)) {
    		  builder = new CT1PDFBuilder();
    	  } else if (pagename.equals(PAGE_CT1b)) {
    		  builder = new CT1bPDFBuilder();    		  
    	  }    		
    	      	  
    	  return builder;
      }
}
