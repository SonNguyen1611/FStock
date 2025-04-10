package com.example.Fstock.dto.response;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private String address;
    private List<RolesReponse> roles;

}
