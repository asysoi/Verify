package cci.web.controller.cert;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.BAD_REQUEST)
public class CertificateUpdateErorrException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	public  CertificateUpdateErorrException(String err) {
		   super(err);
       }

}

