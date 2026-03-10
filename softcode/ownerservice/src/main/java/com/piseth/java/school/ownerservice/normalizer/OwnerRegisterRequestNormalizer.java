package com.piseth.java.school.ownerservice.normalizer;
import org.springframework.stereotype.Component;

import com.piseth.java.school.ownerservice.dto.OwnerRegisterRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OwnerRegisterRequestNormalizer {
	
	// it ask like deliget

    private final EmailNormalizer emailNormalizer;
    private final PhoneNormalizer phoneNormalizer;

    public OwnerRegisterRequest normalize(OwnerRegisterRequest request) {

    	// create new object prevend side effected if we take the object then we do normalize
    	
    	OwnerRegisterRequest newRequest = new OwnerRegisterRequest();
    	
    	newRequest.setEmail(emailNormalizer.normalize(request.getEmail()));
    	newRequest.setPhone(phoneNormalizer.normalize(request.getPhone()));

        return newRequest;
    }
    
    //@TODO don't mutate parameter (create new object)
}