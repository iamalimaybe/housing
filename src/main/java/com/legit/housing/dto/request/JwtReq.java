package com.legit.housing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtReq {
    @NotNull(message = "Please provide username.")
    String username;
    @NotNull(message = "Please provide password.")
    String password;
}
