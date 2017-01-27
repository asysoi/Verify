package cci.web.controller.cert;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import cci.service.FieldType;
import cci.service.cert.CertService;
import cci.service.cert.CertificateRestFulService;

@Controller
@RestController
public class CertificateRestFulController {

	private static final Logger LOG=Logger.getLogger(CertificateRestFulController.class);
	
	@Autowired
	private CertificateRestFulService service;
	
	private CertService certService;


	/* -----------------------------------------
	 * Get list of numbers's certificates by filter
	 * ----------------------------------------- */
	@RequestMapping(value = "rcerts.do", method = RequestMethod.GET, headers = "Accept=application/csv")
	@ResponseStatus (HttpStatus.OK)
	public ResponseEntity<String> getCertificates(
			@RequestParam(value = "nomercert", required = false) String number,
			@RequestParam(value = "nblanka", required = false) String blanknumber,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to,
			Authentication aut) {
		
		String certificates = null;
		
		Filter filter = new Filter(number, blanknumber, from, to);
	    filter.setOtd_id(getOtd_idByRole(aut));
	
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
	 * Find OTD_ID by Role
	 * ----------------------------- */
	private String getOtd_idByRole(Authentication aut) {
		String ret = null;
		if (aut != null) {
			Iterator iterator = aut.getAuthorities().iterator();
		
			while (iterator.hasNext()) {
				String roleName = ((GrantedAuthority) iterator.next()).getAuthority();
				if  (certService.getACL().containsKey(roleName)) {      
			      ret = certService.getACL().get(roleName);
				}
			}
		} else {
			LOG.info("Authentification object is not presented.");			
		}
		return ret;
	}

	/* -----------------------------
	 * Add new certificate from XML body
	 * ----------------------------- */
	@RequestMapping(value = "rcert.do", method = RequestMethod.POST, headers = "Accept=application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public Certificate addXMLCertificate(@RequestBody Certificate certificate,
			Authentication aut) {
		
		String otd_id = getOtd_idByRole(aut);
		
		if (otd_id != null) {
			certificate.setOtd_id(Integer.parseInt(otd_id));
			try {
				service.addCertificate(certificate);
			} catch (Exception ex) {
				throw(new AddCertificateException("Ошибка добавления сертификата: " + ex.toString()));
			}
	   	} else {
	   		throw(new AddCertificateException("Добавлять сертификат может только авторизированный представитель отделения"));
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
			 throw(new CertificateUpdateErorrException("Ошибка обновления сертификата номер " + cert.getNomercert() + ", выданного на бланке " +  cert.getNblanka() + "  :  " + ex.toString()));
		 }
		 
		 if (rcert == null) {
			 throw(new NotFoundCertificateException("Сертификат номер " + cert.getNomercert() + ", выданный на бланке " +  cert.getNblanka() + "  не найден и не может быть изменен."));		    	 
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
