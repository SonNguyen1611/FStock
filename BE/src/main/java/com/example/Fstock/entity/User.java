package com.example.Fstock.entity;

import com.example.Fstock.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column( nullable = false)
    private String firstName;
    @Column( nullable = false)
    private String lastName;
    @Column( nullable = false)
    private String userName;
    @Column( nullable = false)
    private String email;
    @Column( nullable = false)
    private String password;
    @Column( nullable = false)
    private String phone;

    @Column( nullable = true)
    private String imgUrlDisplay;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Order> orders;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user" )
    private List<Cart> carts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<User_Roles> userRoles;

}
