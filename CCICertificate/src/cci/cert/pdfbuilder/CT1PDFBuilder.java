package cci.cert.pdfbuilder;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;

import cci.cert.config.CTCell;
import cci.cert.config.TableConfig;
import cci.cert.model.Certificate;
import cci.cert.model.Product;
import cci.cert.model.ProductIterator;
import cci.cert.service.CountryConverter;

public class CT1PDFBuilder extends PDFBuilder {

	protected void fillInRow(List<CTCell> row, Product product) {
		row.get(0).setText(product.getNumerator() == null ? "" : product.getNumerator());
		row.get(1).setText(product.getVidup() == null ? "" : product.getVidup());
		row.get(2).setText(product.getTovar() == null ? "" : product.getTovar());
		row.get(3).setText(product.getKriter() == null ? "" : product.getKriter());
		row.get(4).setText(product.getVes() == null ? "" : product.getVes());
		row.get(5).setText(product.getSchet() == null ? "" : product.getSchet());
	}
}