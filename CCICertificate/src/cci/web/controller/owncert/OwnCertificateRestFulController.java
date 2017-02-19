package cci.web.controller.owncert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cci.model.owncert.Company;
import cci.model.owncert.OwnCertificate;
import cci.model.owncert.OwnCertificateHeaders;
import cci.model.owncert.OwnCertificates;
import cci.service.owncert.OwnCertificateService;
import cci.web.controller.cert.exception.AddCertificateException;
import cci.web.controller.cert.exception.NotFoundCertificateException;

@RestController
public class OwnCertificateRestFulController {

	@Autowired
	private cci.service.owncert.OwnCertificateService service;

	/* -----------------------------------------
	 * Get list of all certificates by filter
	 * ----------------------------------------- */
	@RequestMapping(value="rowncerts.do",  method = RequestMethod.GET, headers = "Accept=application/json,application/xml")
	@ResponseStatus (HttpStatus.OK)
	public cci.model.owncert.OwnCertificates getCertificates(
			@RequestParam(value = "number", required = false) String number,
			@RequestParam(value = "blanknumber", required = false) String blanknumber,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to ) {
		OwnCertificates certificates;
		OwnFilter filter = new OwnFilter(number, blanknumber, from, to);
		certificates = service.getOwnCertificates(filter);
		
		if (certificates.getOwncertificates().size() == 0 ) {
			throw (new NotFoundCertificateException("Не найдено сертификатов, удовлетворяющих условиям поиска: " + filter.toString()));
		}
		return certificates;
	}
	
	/* -----------------------------------------
	 * Get list of all headr's certificates by filter 
	 * ----------------------------------------- */
	@RequestMapping(value = "rowncertheaders.do", method = RequestMethod.GET, headers = "Accept=application/json,application/xml")
	@ResponseStatus (HttpStatus.OK)
	public OwnCertificateHeaders getCertificateHeaders(
			@RequestParam(value = "number", required = false) String number,
			@RequestParam(value = "blanknumber", required = false) String blanknumber,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to ) {
		OwnCertificateHeaders certificates;
		OwnFilter filter = new OwnFilter(number, blanknumber, from, to);
		certificates = service.getOwnCertificateHeaders(filter);
		
		if (certificates.getOwncertificateheaders().size() == 0 ) {
			throw (new NotFoundCertificateException("Не найдено сертификатов, удовлетворяющих условиям поиска: " + filter.toString()));
		}
		return certificates;
	}
	
	/* -------------------------------------------------
	 * Add new certificate 
	 * ------------------------------------------------- */
	@RequestMapping(value = "rowncert.do", method = RequestMethod.POST, headers = "Accept=application/xml,application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public OwnCertificate addCertificate(@RequestBody OwnCertificate certificate) {
		try {
			service.addOwnSertificate(certificate);
		} catch (Exception ex) {
			throw(new AddCertificateException(ex.toString()));
		}
		return certificate;
	}
	
		
	/* ---------------------------------------------------
	 * Get certificate by NUmber
	 * ------------------------------------------------- */
	@RequestMapping(value = "rowncert.do", method = RequestMethod.GET, 
			headers = "Accept=application/json,application/xml")
	@ResponseStatus(HttpStatus.OK)
	public OwnCertificate getOwnCertificateByNumber(
			@RequestParam(value = "number", required = true) String number)  {
		try {
		    return service.getOwnCertificateByNumber(number);
		} catch (Exception ex) {
			throw(new NotFoundCertificateException("Серитификат с номером  = " + number 
									+ " не найден:  " + ex.toString()));			
		}
	}

	/* -----------------------------
	 * Update certificate
	 * ----------------------------- */
	@RequestMapping(value = "rowncert.do", method = RequestMethod.PUT, headers = "Accept=application/json,application/xml")
	@ResponseStatus(HttpStatus.OK)
	public OwnCertificate updateCountry(@RequestBody OwnCertificate certificate) throws Exception {
		return service.updateOwnCertificate(certificate);
	}
	
	/* -----------------------------
	 * Delete certificate
	 * ----------------------------- */
	@RequestMapping(value = "rowncert.do/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json,application/xml")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<String> deleteCountry(@PathVariable("id") int id) throws Exception {
		System.out.println("id="+id);
		service.deleteOwnCertificate(id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<String>("Certificate " + id + " deleted.", responseHeaders, HttpStatus.OK);
	}
	
	/* -----------------------------
	 * Exception handling 
	 * ----------------------------- */
	@ExceptionHandler(Exception.class)
	@ResponseBody
    public ResponseEntity<String> handleIOException(Exception ex) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Error-Code", "12345");
		responseHeaders.set("Content-Type", "application/json;charset=utf-8");
		return new ResponseEntity<String>(ex.toString(), responseHeaders,  HttpStatus.BAD_REQUEST);
    }

}
