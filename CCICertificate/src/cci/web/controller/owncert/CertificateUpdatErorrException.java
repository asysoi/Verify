package cci.web.controller.owncert;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.BAD_REQUEST)
public class CertificateUpdatErorrException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	public  CertificateUpdatErorrException(String err) {
		   super(err);
       }

}

