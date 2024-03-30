package com.legit.housing.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtRes {
    String accessToken;
    Long expiresIn;
}
