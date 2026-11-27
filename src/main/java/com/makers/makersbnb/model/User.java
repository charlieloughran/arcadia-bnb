package com.makers.makersbnb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String zitadelId;

    private String email;


    public User(String zitadelId, String email){
        this.zitadelId = zitadelId;
        this.email = email;
    }
    // Getters and setters
}