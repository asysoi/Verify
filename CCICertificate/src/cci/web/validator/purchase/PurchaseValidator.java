package cci.web.validator.purchase;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cci.model.cert.Customer;
import cci.model.purchase.Purchase;
import cci.web.controller.purchase.ViewPurchase;

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
		
		ViewPurchase purchase = (ViewPurchase) target;
		

		//if(purchase.getProduct() > 0){
		//	errors.rejectValue("product", "required.product");
		//}
		
	}

}
