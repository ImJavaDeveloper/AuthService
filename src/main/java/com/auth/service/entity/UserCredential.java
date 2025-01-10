package com.auth.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name ="userCred" )
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCredential {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="username"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Roles> userRoles=new HashSet<>();

}
