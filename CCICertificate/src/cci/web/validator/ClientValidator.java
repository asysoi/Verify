package cci.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cci.model.Client;



@Component
public class ClientValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return Client.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price",
				"required.price", "Field name is required.");
		
		Client client = (Client) target;
		
	}

}