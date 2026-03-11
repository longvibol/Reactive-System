package com.vibol.java.school;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.vibol.java.school.service.ShippingFeeService;

public class Demo {
	public static void main(String[] args) {		
		Map<String, ShippingFeeStrategy> map = getStrategy();		
		ShippingFeeService service = new ShippingFeeService(
				map, 
				new DefaultShippingFeeStrategy()
		);		
		/*
		 In our ShippingFeeService need two parameter:
		 	1- Map<String, ShippingFeeStrategy> strategies;
    		2- ShippingFeeStrategy defaultStrategy;
		 */		
		String countryCode = "KH";
		BigDecimal weightKg = new BigDecimal("2.1");
		BigDecimal orderAmount = new BigDecimal("10000");
		
		ShippingFeeRequest request = new ShippingFeeRequest(countryCode, weightKg, orderAmount);	
		
		long fee = service.calculate(request);
		
		System.out.println("Fee = " + fee + " Country : " + countryCode);
	}
	
	//our function create
	private static Map<String, ShippingFeeStrategy>getStrategy(){
		
		//Create new object instance
		Map<String, ShippingFeeStrategy> map = new HashMap<>();
		
		map.put("KH", new CambodiaShippingFeeStrategy());
		map.put("LA", new LaoShippingFeeStrategy());
		
		return map;
	}

}
