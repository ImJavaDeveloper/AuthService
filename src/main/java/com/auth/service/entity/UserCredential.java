package com.auth.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users",uniqueConstraints ={@UniqueConstraint(columnNames = "username")} )
@Data
public class UserCredential {

    @Id
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="username"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles=new HashSet<>();

}
