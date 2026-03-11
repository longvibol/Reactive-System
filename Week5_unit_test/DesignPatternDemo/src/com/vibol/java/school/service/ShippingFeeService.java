package com.vibol.java.school.service;

import java.util.Map;

import com.vibol.java.school.ShippingFeeRequest;
import com.vibol.java.school.ShippingFeeStrategy;

public final class ShippingFeeService {

    private final Map<String, ShippingFeeStrategy> strategies;
    private final ShippingFeeStrategy defaultStrategy;

    //Constractor to create object 
    public ShippingFeeService(
    		
            Map<String, ShippingFeeStrategy> strategies,
            
            ShippingFeeStrategy defaultStrategy) {
    	
        this.strategies = strategies;
        this.defaultStrategy = defaultStrategy;
        
    }
    // Method Calculate 
    public long calculate(ShippingFeeRequest request) {
    	
    	ShippingFeeStrategy strategy = strategies.getOrDefault(request.countryCode(), 
    			defaultStrategy);    	
    	
    	return strategy.calculate(request);
    	
    }
}