package cci.web.controller.cert.exception;

public class CertificateDeleteException extends RuntimeException {
	   private static final long serialVersionUID = 1L;

		public  CertificateDeleteException(String err) {
			   super(err);
	    }
}
