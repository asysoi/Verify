package cci.web.controller.cert;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cci.model.cert.Certificate;
import cci.service.cert.CertificateRestFulService;

@Controller
@RestController
public class CertificateRestFulController {

	private static final Logger LOG=Logger.getLogger(CertificateRestFulController.class);
	
	@Autowired
	private CertificateRestFulService service;

	/* -----------------------------------------
	 * Get list of numbers's certificates by filter
	 * ----------------------------------------- */
	@RequestMapping(value = "rcerts.do", method = RequestMethod.GET, headers = "Accept=application/txt")
	@ResponseStatus (HttpStatus.OK)
	public ResponseEntity<String> getCertificates(
			@RequestParam(value = "nomercert", required = false) String number,
			@RequestParam(value = "nblanka", required = false) String blanknumber,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to ) {
		String certificates = null;
		Filter filter = new Filter(number, blanknumber, from, to);
		
		try {
		   certificates = service.getCertificates(filter);
		
		   if (certificates == null ) {
			  throw (new NotFoundCertificateException("Не найдено сертификатов, удовлетворяющих условиям поиска: " + filter.toString()));
		   }
		} catch (Exception ex) {
			throw (new NotFoundCertificateException("Не найдено сертификатов, удовлетворяющих условиям поиска: " + filter.toString()));
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Operation", "Get List Certificate Numbers");
		
		return new ResponseEntity<String>(certificates, responseHeaders, HttpStatus.OK);
	}

	/* -----------------------------
	 * Add new certificate from XML body
	 * ----------------------------- */
	@RequestMapping(value = "rcert.do", method = RequestMethod.POST, headers = "Accept=application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public Certificate addXMLCertificate(@RequestBody Certificate certificate) {
		try {
			service.addCertificate(certificate);
		} catch (Exception ex) {
			throw(new AddCertificateException(ex.toString()));
		}
		return certificate;
	}
	
	
	/* -----------------------------
	 * Get certificate by number & blanlnumber
	 * ----------------------------- */
	@RequestMapping(value = "rcert.do", method = RequestMethod.GET, headers = "Accept=application/xml")
	@ResponseStatus(HttpStatus.OK)
	public Certificate getCertificateByNumber(
			@RequestParam(value = "nomercert", required = true) String number,
			@RequestParam(value = "nblanka", required = true) String blanknumber)  {
		try {
		    return service.getCertificateByNumber(number, blanknumber);
		} catch (Exception ex) {
			throw(new NotFoundCertificateException("Серитификат номер " + number + ", выданный на бланке " +  blanknumber + " не найден :  " + ex.toString()));			
		}
	}

	/* -----------------------------
	 * Update certificate
	 * ----------------------------- */
	@RequestMapping(value = "rcert.do", method = RequestMethod.PUT, headers = "Accept=application/xml")
	@ResponseStatus(HttpStatus.OK)
	public Certificate updateCertificate(@RequestBody Certificate cert) {
		 Certificate rcert = null;
		 try {
   		     rcert = service.updateCertificate(cert);
		 } catch (Exception ex) {
			 throw(new CertificateUpdateErorrException("Серитификат номер " + cert.getNomercert() + ", выданный на бланке " +  cert.getNblanka() + "  :  " + ex.toString()));
		 }
		 
		 if (rcert == null) {
			 throw(new NotFoundCertificateException("Серитификат номер " + cert.getNomercert() + ", выданный на бланке " +  cert.getNblanka() + "  не найден и не может быть изменен."));		    	 
		 }
		 return rcert;
	}
	
	/* -----------------------------
	 * Delete certificate
	 * ----------------------------- */
	@RequestMapping(value = "rcert.do", method = RequestMethod.DELETE, headers = "Accept=application/txt")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<String> deleteCertificate(
			@RequestParam(value = "nomercert", required = false) String number,
			@RequestParam(value = "nblanka", required = false) String blanknumber) {
		try {
			service.deleteCertificate(number, blanknumber);
		} catch(Exception ex) {
			throw(new CertificateDeleteException("Серитификат номер " + number + ", выданный на бланке " +  blanknumber + "  не может быть удален: " + ex.toString()));	
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<String>("Certificate " + number + " deleted.", responseHeaders, HttpStatus.OK);
	}
	
	/* -----------------------------
	 * Exception handling 
	 * ----------------------------- */
	@ExceptionHandler(Exception.class)
	@ResponseBody
    public ResponseEntity<String> handleRESTException(Exception ex) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Error-Code", "12345");
		responseHeaders.set("Content-Type", "application/json;charset=utf-8");
		return new ResponseEntity<String>(ex.toString(), responseHeaders,  HttpStatus.BAD_REQUEST);
    }

}
