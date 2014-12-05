package cci.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SequenceGenerator {
	
	public static Logger LOG = LogManager.getLogger(SequenceGenerator.class);
	private static long value = 1;
	private static long maxvalue = 1;
	private static int poolsize = 100;
	    
    public static long getNextValue(String seq_name, SequenceDAO dao) throws Exception{
    	if (value == maxvalue) {
   		   value = dao.getNextValuePool(seq_name, poolsize);
   		   maxvalue = value + poolsize;
   		   LOG.info("Got next value pool: from " + value + " to " + maxvalue);
    	}
    	
        return value++;    	
    }
}
