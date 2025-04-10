package com.example.Fstock.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;
    private String permissionName;
    private String permissionDescription;
    @ManyToMany(mappedBy = "permissions")
    private Set<Roles> roles;

}
