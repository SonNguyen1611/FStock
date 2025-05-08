package com.example.Fstock.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeInfoRequest {
    private String email;

    @NotBlank(message = "User name must not be blank")
    @Size(min = 8, max = 30, message = "User name must be at least 8 character")
    private String userName;
    @NotBlank(message = "Phone must not be blank")
    @Pattern(regexp = "^(0[0-9]{9,11})$", message = "Invalid phone number format")
    private String phone;

}
