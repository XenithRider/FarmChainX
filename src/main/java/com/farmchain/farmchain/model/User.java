package com.farmchain.farmchain.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity   // Tells Hibernate to make a table out of this class
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String name ;

    @Column(nullable = false , unique = true)
    private String email ;

    @Column(nullable = false)
    private String password ;


    @ManyToMany(fetch = FetchType.EAGER)  // relationship User<->Role
    @JoinTable(
            name="user_roles",   // Name of the JOIN TABLE
            joinColumns = @JoinColumn(name="user_id"),  // FK from User Entity
            inverseJoinColumns = @JoinColumn(name="role_id")  //FK from Role Entity
    )
    private Set<Role> roles ;

    @OneToMany( mappedBy = "farmer" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Product> products = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
