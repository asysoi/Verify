package cci.web.controller.fscert;

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
import cci.model.cert.fscert.FSCertificate;
import cci.service.cert.CertService;
import cci.service.fscert.FSCertificateRestFulService;
import cci.web.controller.cert.AddCertificateException;
import cci.web.controller.cert.CertificateDeleteException;
import cci.web.controller.cert.CertificateUpdateErorrException;
import cci.web.controller.cert.NotFoundCertificateException;

@Controller
@RestController
public class FSCertificateRestFulController {

	private static final Logger LOG=Logger.getLogger(FSCertificateRestFulController.class);
	
	@Autowired
	private FSCertificateRestFulService fsservice;
	
	@Autowired
	private CertService certService;
	
	/* -----------------------------------------
	 * Get list of header's certificates by filter
	 * ----------------------------------------- */
	@RequestMapping(value = "fscerts.do", method = RequestMethod.GET, headers = "Accept=application/csv")
	@ResponseStatus (HttpStatus.OK)
	public ResponseEntity<String> getCertificates(
			@RequestParam(value = "nomercert", required = false) String number,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to,
			Authentication aut) {
		
		String certificates = null;
		
		FSFilter filter = new FSFilter(number, from, to);
	    filter.setOtd_id(getOtd_idByRole(aut));
	
		try {
		   certificates = fsservice.getCertificates(filter);
		
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
	@RequestMapping(value = "fscert.do", method = RequestMethod.POST, headers = "Accept=application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public FSCertificate addXMLCertificate(@RequestBody FSCertificate certificate,
			Authentication aut) {
		
		String otd_id = getOtd_idByRole(aut);
		
		if (otd_id != null) {
			//certificate.setOtd_id(Integer.parseInt(otd_id));
			try {
				fsservice.addCertificate(certificate);
			} catch (Exception ex) {
				throw(new AddCertificateException("Ошибка добавления сертификата: " + ex.toString()));
			}
	   	} else {
	   		throw(new AddCertificateException("Добавлять сертификат может только авторизированный представитель отделения ."));
	   	}
		
		return certificate;
	}
	
	
	/* -----------------------------
	 * Get certificate by number & blanknumber
	 * ----------------------------- */
	@RequestMapping(value = "fscert.do", method = RequestMethod.GET, headers = "Accept=application/xml")
	@ResponseStatus(HttpStatus.OK)
	public FSCertificate getCertificateByNumber(
			@RequestParam(value = "nomercert", required = true) String number,
			Authentication aut)  {
		
		String otd_id = getOtd_idByRole(aut);
		try {
			    
			    FSCertificate rcert = fsservice.getCertificateByNumber(number);
			    
			    if (otd_id != null) {
			    	throw(new NotFoundCertificateException("Нет доступа к серитификату номер " + number));
			    }
				return rcert; 
			} catch (Exception ex) {
				throw(new NotFoundCertificateException("Серитификат номер " + number + " не найден :  " + ex.toString()));			
		}
		
	}

	/* -----------------------------
	 * Update certificate
	 * ----------------------------- */
	@RequestMapping(value = "fscert.do", method = RequestMethod.PUT, headers = "Accept=application/xml")
	@ResponseStatus(HttpStatus.OK)
	public FSCertificate updateCertificate(@RequestBody FSCertificate cert, Authentication aut) {
		 FSCertificate rcert = null;
		 try {
			 String otd_id = getOtd_idByRole(aut);
			 if (otd_id == null) {
				 throw(new CertificateUpdateErorrException("Изменить сертификат может только авторизированный представитель отделения."));
			 }
   		     rcert = fsservice.updateCertificate(cert, otd_id);
		 } catch (NotFoundCertificateException ex) {
			 throw(new CertificateUpdateErorrException("Cертификат номер " + cert.getCertnumber() + " не найден в базе. Обновление невозможно. Добавьте сертификат в базу."));
		 } catch (Exception ex) {
			 throw(new CertificateUpdateErorrException("Ошибка обновления сертификата номер " + cert.getCertnumber() + ", выданного на бланке "  + "  :  " + ex.toString()));
		 }
		 
		 if (rcert == null) {
			 throw(new NotFoundCertificateException("Сертификат номер " + cert.getCertnumber() + ", выданный на бланке " + "  не найден и не может быть изменен."));		    	 
		 }
		 return rcert;
	}
	
	/* -----------------------------
	 * Delete certificate
	 * ----------------------------- */
	@RequestMapping(value = "fscert.do", method = RequestMethod.DELETE, headers = "Accept=application/txt")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<String> deleteCertificate(
			@RequestParam(value = "nomercert", required = false) String number,
			@RequestParam(value = "nblanka", required = false) String blanknumber,
			Authentication aut) {
		try {
			String otd_id = getOtd_idByRole(aut);
			 if (otd_id == null) {
				 throw(new CertificateDeleteException("Удалить сертификат может только авторизированный представитель отделения."));
			}
			fsservice.deleteCertificate(number, blanknumber, otd_id);
		} catch(Exception ex) {
			throw(new CertificateDeleteException("Серитификат номер " + number + ", выданный на бланке " +  blanknumber + "  не может быть удален: " + ex.toString()));	
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("CertificateResponseHeader", "Message");
		return new ResponseEntity<String>("Certificate " + number + " deleted.", responseHeaders, HttpStatus.OK);
	}
	
	/* -----------------------------
	 * Exception handling 
	 * ----------------------------- */
	@ExceptionHandler(Exception.class)
	@ResponseBody
    public ResponseEntity<String> handleRESTException(Exception ex) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Error Name", ex.getClass().getName());
		responseHeaders.set("Content-Type", "application/json;charset=utf-8");
		return new ResponseEntity<String>(ex.toString(), responseHeaders,  HttpStatus.BAD_REQUEST);
    }

}



	
	
