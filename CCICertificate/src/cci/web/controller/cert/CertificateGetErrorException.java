package cci.web.controller.cert;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.BAD_REQUEST)
public class CertificateGetErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public  CertificateGetErrorException(String err) {
		   super(err);
    }
}
