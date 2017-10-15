package cci.web.controller.cert;


import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cci.model.cert.Certificate;
import cci.pdfbuilder.cert.CertificatePDFBuilder;
import cci.service.cert.CertService;

@Controller
@SessionAttributes({ "certfilter", "vmanager" })
public class CertificateController {
	
	//public static Logger LOG=LogManager.getLogger(CertController.class);
	private static final Logger LOG=Logger.getLogger(CertificateController.class);
	
	@Autowired
	private CertService certService;

	// ---------------------------------------------------------------------------------------
		//  Check certificate 
		// ---------------------------------------------------------------------------------------
		@RequestMapping(value = "check.do", method = RequestMethod.GET)
		public String check(ModelMap model) {
			String retpage = "check";
			Certificate cert = new Certificate();
			model.addAttribute("cert", cert);
			return retpage;
		}

		
		// ---------------------------------------------------------------------------------------
		//  Check certificate 
		// ---------------------------------------------------------------------------------------
		@RequestMapping(value = "check.do", method = RequestMethod.POST)
		public String check(@ModelAttribute("cert") Certificate cert,
				BindingResult result, SessionStatus status, ModelMap model, HttpServletRequest request) {
			String retpage = "check";

			if (cert.getNomercert() != null && cert.getNblanka() != null
					&& cert.getDatacert() != null) {
				Certificate rcert = certService.checkCertificate(cert);

				if (rcert != null) {
					
					String relativeWebPath = "/resources";
					String  absoluteDiskPath= request.getSession().getServletContext().getRealPath(relativeWebPath);
					
					long start = System.currentTimeMillis();
					makepdffile(absoluteDiskPath, rcert);
					LOG.info("Certificate check pdf making: " + (System.currentTimeMillis() - start));
					
					String msg = "<p>Найден сертификат номер " + cert.getNomercert()
							+ " на бланке с номером " + cert.getNblanka() + ", выданный "
							+ cert.getDatacert()
							+ ".</p> "+
					        "<p>Воспроизведение бумажной версии сертификата <a href=\"javascript:openCertificate(\'" +
							"resources/out/" + rcert.getCert_id() + ".pdf" +
							"')\">" + rcert.getNomercert() + "</a><p style=\"width:100%\">" + 
				            "<p>Результат воспроизведения может незначительно отличаться по форме и стилю отображения," +
				            "но полностью воспроизводит содержание документа.</p>";
					
					model.addAttribute("msg", msg);
					retpage = "fragments/message";
				} else {
					model.addAttribute("cert", cert);
					String msg = "Сертификат номер " + cert.getNomercert()
							+ " на бланке номер " + cert.getNblanka() + ", выданный "
							+ cert.getDatacert()
							+ ", не найден";
					model.addAttribute("msg", msg);
					retpage = "fragments/message";
				}
			}
			return retpage;
		}

	
	
	// ---------------------------------------------------------------------------------------
	//   PDF builder util
	// ---------------------------------------------------------------------------------------
	private void makepdffile(String absoluteDiskPath, Certificate cert) {
		CertificatePDFBuilder builder = new CertificatePDFBuilder();
		String fout = absoluteDiskPath + "/out/" + cert.getCert_id() + ".pdf";
		String fconf = absoluteDiskPath + "/config/pages.xml";
		String fpath = absoluteDiskPath + "/fonts/";
		
		// CountryConverter.setCountrymap(certService.getCountriesList("RU"));
		
		try {
		   builder.createPdf(fout, cert, fconf, fpath);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	
	


}
