package com.pisethjavaschool.userservice.user.domain;

import com.pisethjavaschool.userservice.user.domain.enumeration.UserType;

public record PinResetTokenData(
        String countryCode,
        String phoneNumber,
        UserType userType
) {
}