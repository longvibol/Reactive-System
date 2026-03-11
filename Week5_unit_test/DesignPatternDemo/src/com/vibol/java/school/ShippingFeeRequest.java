package com.vibol.java.school;

import java.math.BigDecimal;

public record ShippingFeeRequest(
		String countryCode,
		BigDecimal weightKg,
		BigDecimal orderAmount		
) {}
