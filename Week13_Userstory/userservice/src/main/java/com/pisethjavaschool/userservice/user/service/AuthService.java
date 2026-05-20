package com.pisethjavaschool.userservice.user.service;

import com.pisethjavaschool.userservice.user.dto.ForgotPinConfirmOtpRequest;
import com.pisethjavaschool.userservice.user.dto.ForgotPinConfirmOtpResponse;
import com.pisethjavaschool.userservice.user.dto.ForgotPinRequest;
import com.pisethjavaschool.userservice.user.dto.LoginRequest;
import com.pisethjavaschool.userservice.user.dto.LoginResponse;
import com.pisethjavaschool.userservice.user.dto.ResetPinRequest;
import com.pisethjavaschool.userservice.user.dto.UserAccountResponse;

import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<LoginResponse> login(LoginRequest request);

    Mono<Void> requestForgotPinOtp(ForgotPinRequest request);

    Mono<ForgotPinConfirmOtpResponse> confirmForgotPinOtp(ForgotPinConfirmOtpRequest request);

    Mono<UserAccountResponse> resetPin(ResetPinRequest request);
}