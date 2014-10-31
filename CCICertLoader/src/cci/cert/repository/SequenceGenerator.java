package cci.cert.repository;

import org.apache.log4j.Logger;

public class SequenceGenerator {
	
	private static final Logger LOG = Logger.getLogger(SequenceGenerator.class);
	private static long value = 1;
	private static long maxvalue = 1;
	private static int poolsize = 100;
	    
    public static long getNextValue(String seq_name, CertificateDAO dao) throws Exception{
    	if (value == maxvalue) {
   		   value = dao.getNextValuePool(seq_name, poolsize);
   		   maxvalue = value + poolsize;
   		   LOG.info("Got next value pool: from " + value + " to " + maxvalue);
    	}
    	
        return value++;    	
    }
}
