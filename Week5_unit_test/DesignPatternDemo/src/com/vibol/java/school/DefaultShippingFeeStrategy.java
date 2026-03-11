package com.vibol.java.school;

import java.math.BigDecimal;

import com.vibol.java.school.validation.ShippingFeeRequestValidator;

public class DefaultShippingFeeStrategy implements ShippingFeeStrategy{

	@Override
	public Long calculate(ShippingFeeRequest request) {
		
		// Validation 
		ShippingFeeRequestValidator.validate(request);
		
		// Example rules: 5% of order amount, minimum 3000
		long fee = request.orderAmount()
				.multiply(BigDecimal.valueOf(0.05))
				.longValue();
		
		return Math.max(fee, 3000L);
	}

}
