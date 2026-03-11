package com.vibol.java.school.validation;

import java.math.BigDecimal;

import com.vibol.java.school.ShippingFeeRequest;

public final class ShippingFeeRequestValidator {

	// That constructor is there to stop anyone from creating an object of the validator class.
	// we no need to create object when we instantiation to called and static void we can called it easily 
    private ShippingFeeRequestValidator() {
        // prevent instantiation
    }

    public static void validate(ShippingFeeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request is required");
        }

        if (request.weightKg() == null) {
            throw new IllegalArgumentException("Weight is required");
        }

        if (request.weightKg().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }

        if (request.orderAmount() == null) {
            throw new IllegalArgumentException("Order amount is required");
        }

        if (request.orderAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Order amount must not be negative");
        }
    }
}