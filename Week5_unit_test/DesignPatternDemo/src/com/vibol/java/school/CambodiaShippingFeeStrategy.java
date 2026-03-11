package com.vibol.java.school;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.vibol.java.school.validation.ShippingFeeRequestValidator;

public class CambodiaShippingFeeStrategy implements ShippingFeeStrategy{

	@Override
	public Long calculate(ShippingFeeRequest request) {
		
		// Validation 
		ShippingFeeRequestValidator.validate(request);
		
		// Example rules: base 2000 riel + 1500 riel per 0.5kg		
		BigDecimal base = BigDecimal.valueOf(2000);
		BigDecimal step = BigDecimal.valueOf(1500);
		
		// we want to half kg 
		BigDecimal halfKgUnits = request.weightKg().divide(BigDecimal.valueOf(0.5), 0, RoundingMode.CEILING);
		
		/*
		 * Examples

			- 0.3 / 0.5 = 0.6 → rounds up to 1
			
			- 0.5 / 0.5 = 1.0 → stays 1
			
			- 0.6 / 0.5 = 1.2 → rounds up to 2
			
			- 1.1 / 0.5 = 2.2 → rounds up to 3
		 */
		
		return base.add(step.multiply(halfKgUnits)).longValueExact();
		
		/*
		 Example calculations:

			If weight = 0.3 kg
			
			0.3 / 0.5 = 0.6 → round up = 1
			
			Fee = 2000 + (1500 × 1) = 3500
			
			If weight = 0.5 kg
			
			0.5 / 0.5 = 1
			
			Fee = 2000 + (1500 × 1) = 3500
			
			If weight = 0.6 kg
			
			0.6 / 0.5 = 1.2 → round up = 2
			
			Fee = 2000 + (1500 × 2) = 5000
			
			If weight = 1.2 kg
			
			1.2 / 0.5 = 2.4 → round up = 3
			
			Fee = 2000 + (1500 × 3) = 6500
		 
		 */
	}

}
