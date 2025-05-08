package com.example.Fstock.dto.request;
import com.example.Fstock.entity.Roles;
import com.example.Fstock.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreationUser {
    @NotBlank(message = "Fist name must not be blank")
    private String firstName;
    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @NotBlank(message = "User name must not be blank")
    @Size(min = 8, max = 30, message = "User name must be at least 8 character")
    private String userName;

    @NotBlank(message = "Email must not be blank")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=!.()+ =]).{8,}$",
            message = "Password must have at least 8 characters, one uppercase letter, and one special character")
    private String password;

    @NotBlank(message = "Phone must not be blank")
    @Pattern(regexp = "^(0[0-9]{9,11})$", message = "Invalid phone number format")
    private String phone;

    private MultipartFile imgUrlDisplay;
    private Gender gender;

    private List<Roles> roles;

}
