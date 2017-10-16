package cci.web.controller.cert;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cci.model.cert.Certificate;
import cci.pdfbuilder.cert.CertificatePDFBuilder;
import cci.service.cert.CertService;

@Controller
@SessionAttributes({"lang"})
public class CertificateController {

	// public static Logger LOG=LogManager.getLogger(CertController.class);
	private static final Logger LOG = Logger.getLogger(CertificateController.class);

	@Autowired
	private CertService certService;

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
			
			Certificate rcert = certService.checkCertificate(cert);
			String msg;
			
			if (rcert != null) {

				String relativeWebPath = "/resources";
				String absoluteDiskPath = request.getSession().getServletContext().getRealPath(relativeWebPath);

				long start = System.currentTimeMillis();
				makepdffile(absoluteDiskPath, rcert);
				LOG.info("Certificate check pdf making: " + (System.currentTimeMillis() - start));
				
				if ("ru".equals(model.get("lang"))) {

					msg = "<p>Найден сертификат номер " + cert.getNomercert() + " на бланке с номером "
						+ cert.getNblanka() + ", выданный " + cert.getDatacert() + ".</p> "
						+ "<p>Воспроизведение бумажной версии сертификата <a href=\"javascript:openCertificate(\'"
						+ "resources/out/" + rcert.getCert_id() + ".pdf" + "')\">" + rcert.getNomercert()
						+ "</a><p style=\"width:100%\">"
						+ "<p>Результат воспроизведения может незначительно отличаться по форме и стилю отображения,"
						+ "но полностью воспроизводит содержание документа.</p>";
				} else {
					msg = "<p>There is a certificate with number " + cert.getNomercert() + " issued " + cert.getDatacert() + ".</p> "
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
		}
		return retpage;
	}
	
	
	// ---------------------------------------------------------------------------------------
	// Set page language 
	// ---------------------------------------------------------------------------------------
	@RequestMapping(value = "check.do", method = RequestMethod.GET)
	public String check(
			HttpServletRequest request,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "ncert", required = false) String ncert,
			@RequestParam(value = "nblanka", required = false) String nblanka,
			@RequestParam(value = "datecert", required = false) String datecert,
			Authentication aut,
			ModelMap model) {
		
		    String retpage = "check";
		    if (model.get("lang") == null) {
		    	model.addAttribute("lang", "ru");
		    } else if (lang != null) {
		       model.addAttribute("lang", lang );
		    }
		
	        Certificate cert = new Certificate();
	        cert.setNblanka(nblanka);
	        cert.setNomercert(ncert);
	        cert.setDatacert(datecert);
			model.addAttribute("cert", cert);
			
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

}
