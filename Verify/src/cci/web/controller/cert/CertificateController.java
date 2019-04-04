package cci.web.controller.cert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cci.model.cert.Certificate;
import cci.model.owncert.OwnCertificate;
import cci.pdfbuilder.PDFUtils;
import cci.pdfbuilder.cert.CertificatePDFBuilder;
import cci.service.cert.CertificateService;
import cci.web.controller.cert.exception.NotFoundCertificateException;
import cci.web.controller.owncert.ViewOwnCertificateJSPHelper;

@Controller
@SessionAttributes({"lang","type"})
public class CertificateController {

	// public static Logger LOG=LogManager.getLogger(CertController.class);
	private static final Logger LOG = Logger.getLogger(CertificateController.class);
	private final String relativeWebPath = "/resources/in";

	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private PDFUtils pdfutils;

	// ---------------------------------------------------------------------------------------
	// Check certificate
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "check.do", method = RequestMethod.POST)
	public String check(@ModelAttribute("cert") Certificate cert, BindingResult result, SessionStatus status,
						ModelMap model, HttpServletRequest request) {
		String retpage = "check";

		if (cert.getNomercert() != null && cert.getNblanka() != null && cert.getDatacert() != null) {
			cert.setNomercert(cert.getNomercert().trim());
			cert.setNblanka(cert.getNblanka().trim());
			cert.setDatacert(cert.getDatacert().trim());
			
			LOG.info("type = " + model.get("type"));
			String msg; 
			if ("ct1".equals(model.get("type"))) {

				Certificate rcert = certificateService.checkCertificate(cert);

				if (rcert != null) {

					String relativeWebPath = "/resources";
					String absoluteDiskPath = request.getSession().getServletContext().getRealPath(relativeWebPath);

					makepdffile(absoluteDiskPath, rcert);

					if ("ru".equals(model.get("lang"))) {
						msg = "<p>Найден сертификат номер " + cert.getNomercert() + " на бланке с номером "
								+ cert.getNblanka() + ", выданный " + cert.getDatacert() + ".</p> "
								+ "<p>Воспроизведение бумажной версии сертификата <a href=\"javascript:openCertificate(\'"
								+ "resources/out/" + rcert.getCert_id() + ".pdf" + "')\">" + rcert.getNomercert()
								+ "</a><p style=\"width:100%\">"
								+ "<p>Результат воспроизведения может незначительно отличаться по форме и стилю отображения,"
								+ "но полностью воспроизводит содержание документа.</p>";
					} else {
						msg = "<p>There is a certificate with number " + cert.getNomercert() + " issued "
								+ cert.getDatacert() + ".</p> "
								+ "<p>Electronic copy of the original certificate <a href=\"javascript:openCertificate(\'"
								+ "resources/out/" + rcert.getCert_id() + ".pdf" + "')\">" + rcert.getNomercert()
								+ "</a><p style=\"width:100%\">"
								+ "<p>The electronic copy of the certificate may have minor differences "
								+ "in form but is identical in content.</p>";
					}
					model.addAttribute("msg", msg);
					retpage = "fragments/message";
				} else {
					model.addAttribute("cert", cert);

					if ("eng".equals(model.get("lang"))) {
						msg = "Certificate number " + cert.getNomercert() + " with the number of the form "
								+ cert.getNblanka() + " issued " + cert.getDatacert() + " not found";
					} else {
						msg = "Сертификат номер " + cert.getNomercert() + " на бланке номер " + cert.getNblanka()
								+ ", выданный " + cert.getDatacert() + ", не найден";
					}

					model.addAttribute("msg", msg);
					retpage = "fragments/message";
				}
			} else if ("own".equals(model.get("type"))) {
				OwnCertificate rcert = certificateService.checkOwnCertificate(cert);
				
				if (rcert != null) {
					
					if ("ru".equals(model.get("lang"))) {
						msg = "<p>Найден сертификат собственного производства " + cert.getNomercert() + " на бланке с номером "
								+ cert.getNblanka() + ", выданный " + cert.getDatacert() + ".</p> "
								+ "<p>Воспроизведение бумажной версии сертификата <a href=\"javascript:openCertificate(\'owncert.do?ncert="
								+ rcert.getNumber() + "&nblanka=" + rcert.getBlanknumber() + "&datecert=" + rcert.getDatecert() 
								+ "')\">" + rcert.getNumber()
								+ "</a><p style=\"width:100%\">"
								+ "<p>Результат воспроизведения может незначительно отличаться по форме и стилю отображения,"
								+ "но полностью воспроизводит содержание документа.</p>";
					} else {
						msg = "<p>There is a certificate ща щцт зкщвгсешщт with number " + cert.getNomercert() + " issued "
								+ cert.getDatacert() + ".</p> "
								+ "<p>Electronic copy of the original certificate <a href=\"javascript:openCertificate(\'owncert.do?ncert="
								+ rcert.getNumber() + "&nblanka=" + rcert.getBlanknumber() + "&datecert=" + rcert.getDatecert() 
								+ "')\">" + rcert.getNumber()
								+ "</a><p style=\"width:100%\">"
								+ "<p>The electronic copy of the certificate may have minor differences "
								+ "in form but is identical in content.</p>";
					}
					model.addAttribute("msg", msg);
					retpage = "fragments/message";
				} else {
					model.addAttribute("cert", cert);

					if ("eng".equals(model.get("lang"))) {
						msg = "Certificate of own production number " + cert.getNomercert() + " with the number of the form "
								+ cert.getNblanka() + " issued " + cert.getDatacert() + " is not found";
					} else {
						msg = "Сертификат собственного производства номер " + cert.getNomercert() + " на бланке номер " + cert.getNblanka()
								+ ", выданный " + cert.getDatacert() + ", не найден";
					}

					model.addAttribute("msg", msg);
					retpage = "fragments/message";
				}
				
			}
		}
		return retpage;
	}
	
	
	// ---------------------------------------------------------------------------------------
	// Set page language 
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "check.do", method = RequestMethod.GET)
	public String check(
			HttpServletRequest request,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "ncert", required = false) String ncert,
			@RequestParam(value = "nblanka", required = false) String nblanka,
			@RequestParam(value = "datecert", required = false) String datecert,
			Authentication aut,
			ModelMap model) {
		
		    String retpage = "check";
		    if (lang == null && model.get("lang") == null) {
		    	model.addAttribute("lang", "ru");
		    } else if (lang != null) {
		       model.addAttribute("lang", lang);
		    }
		    
		    if (type == null && model.get("type") == null) {
		    	model.addAttribute("type", "ct1");
		    } else if (type != null) {
		       model.addAttribute("type", type);
		    }
		
	        Certificate cert = new Certificate();
	        cert.setNblanka(nblanka);
	        cert.setNomercert(ncert);
	        cert.setDatacert(datecert);
			model.addAttribute("cert", cert);
			LOG.info("type = " + model.get("type"));
  		    return retpage;
	}

	// ---------------------------------------------------------------------------------------
	// PDF builder util
	// ---------------------------------------------------------------------------------------
	private void makepdffile(String absoluteDiskPath, Certificate cert) {
		CertificatePDFBuilder builder = new CertificatePDFBuilder();
		String fout = absoluteDiskPath + "/out/" + cert.getCert_id() + ".pdf";
		String fconf = absoluteDiskPath + "/config/pages.xml";
		String fpath = absoluteDiskPath + "/fonts/";

		// CountryConverter.setCountrymap(certService.getCountriesList("RU"));

		try {
			builder.createPdf(fout, cert, fconf, fpath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	/* -------------------------------------------------------------------------
	 *  View Own Certificate as HTML or PDF page
	 * -------------------------------------------------------------------------*/
	@RequestMapping(value = "owncert.do", method = RequestMethod.GET)
	public void getOwnCertificate(
			@RequestParam(value = "ncert", required = true) String ncert,
			@RequestParam(value = "nblanka", required = true) String nblanka,
			@RequestParam(value = "datecert", required = true) String datecert,
			HttpServletRequest request, 
			HttpServletResponse response, 
			ModelMap model) throws IOException, ServletException {
        
		String pathToTempFile = "/resources/out/own";
		String pathToJSP = "/WEB-INF/jsp/";
		
		OwnCertificate owncert = null;
		
		try {
	        Certificate filter= new Certificate();
	        filter.setNblanka(nblanka);
	        filter.setNomercert(ncert);
	        filter.setDatacert(datecert);
			owncert = certificateService.getOwnCertificate(filter);
		} catch (EmptyResultDataAccessException emex) {
			model.addAttribute("error", "Сертификат не найден или отсутстуют права доступа к нему.");
			request.setAttribute("error", "Сертификат не найден или отсутстуют права доступа к нему.");
			request.getRequestDispatcher(pathToJSP+"400.jsp").forward(request, response);
		} catch (Exception ex) {
			model.addAttribute("error", ex.getClass().getName() + " - " + ex.getLocalizedMessage());
			request.setAttribute("error", ex.getClass().getName() + " - " + ex.getLocalizedMessage());
			request.getRequestDispatcher(pathToJSP+"400.jsp").forward(request, response);
		} 
		
		if (owncert != null && owncert.getFilename() != null && !owncert.getFilename().isEmpty()) {
			try {
				
				String templateDiskPath = request.getSession().getServletContext().getRealPath(relativeWebPath);
				String pdfFilePath = request.getSession().getServletContext().getInitParameter("upload.location");
				String tempname = owncert.getBlanknumber() + ".pdf";

				String pagefirst = null;
				String pagenext = null;

				if ("с/п".equals(owncert.getType())) {
					pagefirst = templateDiskPath + System.getProperty("file.separator") + "ownproductfirst.pdf";
					pagenext = templateDiskPath + System.getProperty("file.separator") + "ownproductnext.pdf";
				} else if ("р/у".equals(owncert.getType())) {
					pagefirst = templateDiskPath + System.getProperty("file.separator") + "ownservicefirst.pdf";
					pagenext = templateDiskPath + System.getProperty("file.separator") + "ownservicenext.pdf";
				} else if ("б/у".equals(owncert.getType())) {
					pagefirst = templateDiskPath + System.getProperty("file.separator") + "ownbankfirst.pdf";
					pagenext = templateDiskPath + System.getProperty("file.separator") + "ownbanknext.pdf";
				} else {
					throw new NotFoundCertificateException("Для данного типа сертификата не определены формы бланков.");
				}

				String pdffile = pdfFilePath + System.getProperty("file.separator") + owncert.getFilename();
				List<String> numbers = certificateService.splitOwnCertNumbers(owncert.getBlanknumber(),
						owncert.getAdditionalblanks());
				ByteArrayOutputStream output = pdfutils.mergePdf(pdffile, pagefirst, pagenext, numbers);
				
				if (output != null) {
					response.setContentType("application/pdf");
					response.setHeader("Accept-Ranges", "bytes");
					response.setHeader("Content-Disposition", "inline; filename=" + tempname);
					response.setContentLength(output.size());
					response.getOutputStream().write(output.toByteArray());
					response.getOutputStream().flush();
					response.getOutputStream().close();
					response.flushBuffer();
				    output.close();
				    
				} else {
					LOG.info("Ошибка формирования файла с изображением выданного сертификата.");
					model.addAttribute("error", "Ошибка формирования файла с изображением выданного сертификата.");
					request.setAttribute("error", "Ошибка формирования файла с изображением выданного сертификата.");
					request.getRequestDispatcher(pathToJSP+"400.jsp").forward(request, response);
				}
			} catch (Exception ex) {
				LOG.info(ex.getMessage());
				model.addAttribute("error", ex.getMessage());
				model.addAttribute("owncert", owncert);
				request.setAttribute("error", ex.getMessage());
				ViewOwnCertificateJSPHelper viewcert = new ViewOwnCertificateJSPHelper(owncert); 
				request.setAttribute("viewcert", viewcert);
				request.getRequestDispatcher(pathToJSP+"own/ownview.jsp").forward(request, response);
			}
		} else if (owncert != null) {
			LOG.info("Owncert has no PDF! Go to text.");
			model.addAttribute("owncert", owncert);
			ViewOwnCertificateJSPHelper viewcert = new ViewOwnCertificateJSPHelper(owncert); 
			request.setAttribute("viewcert", viewcert);
			request.getRequestDispatcher(pathToJSP+"own/ownview.jsp").forward(request, response);
		}
	}
	
	/* -----------------------------
	 * Get certificate by number & blanknumber
	 * ----------------------------- */
	@RequestMapping(value = "rcert.do", method = RequestMethod.GET, headers = "Accept=application/xml")
	@ResponseStatus(HttpStatus.OK)
	public Certificate getCertificateByNumber(
			@RequestParam(value = "ncert", required = true) String number,
			@RequestParam(value = "nblanka", required = true) String blanknumber,
			@RequestParam(value = "date", required = true) String date,
			Authentication aut)  {
		
		try {
			    
			    Certificate rcert = certificateService.getCertificate(number, blanknumber, date);
				return rcert; 
			} catch (Exception ex) {
				throw(new NotFoundCertificateException("Сертификат " + number + ", номер бланка " +  blanknumber + " не найден :  " + ex.toString()));			
		}
		
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
