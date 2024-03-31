package com.legit.housing.dto.request;

import com.legit.housing.annotation.EnumNamePattern;
import com.legit.housing.entity.main.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @NotNull(message = "First name is required")
    @NotBlank(message = "First name cannot be empty")
    private String firstname;
    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name cannot be empty")
    private String lastname;
    @Email(message = "Please provide a valid email address")
    @NotNull(message = "Email address is required")
    private String email;
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Password must contain both numbers and alphabets")
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Role is required")
    @EnumNamePattern(regexp = "ADMIN|MANAGER|TENANT|LANDLORD|AGENT")
    private Role role;
    @NotNull(message = "User active status is required")
    private Boolean active;
}
