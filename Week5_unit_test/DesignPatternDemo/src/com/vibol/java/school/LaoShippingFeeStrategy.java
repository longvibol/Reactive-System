package com.vibol.java.school;

import java.math.BigDecimal;

import com.vibol.java.school.validation.ShippingFeeRequestValidator;

public class LaoShippingFeeStrategy implements ShippingFeeStrategy{

	@Override
	public Long calculate(ShippingFeeRequest request) {
		
		// Validation 
		ShippingFeeRequestValidator.validate(request);
		
		// Example rules: base 2000 riel per 1kg	
		BigDecimal base = BigDecimal.valueOf(2000);
		

		BigDecimal total = request.weightKg().multiply(base);
		
		return total.longValueExact();
		// longValueExact() converts to long and throws an error if the value is not a whole number or is too large
	
	}

}
