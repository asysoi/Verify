package cci.purchase.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cci.cert.model.Customer;
import cci.purchase.model.Purchase;
import cci.purchase.web.controller.PurchaseView;

@Component
public class PurchaseValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return Purchase.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price",
				"required.price", "Field name is required.");
		
		PurchaseView purchase = (PurchaseView) target;
		

		//if(purchase.getProduct() > 0){
		//	errors.rejectValue("product", "required.product");
		//}
		
	}

}
