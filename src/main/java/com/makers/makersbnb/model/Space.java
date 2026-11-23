package com.makers.makersbnb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "SPACES")
public class Space {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String description;
    private Integer price;
    private String rules;


    public Space(String name, String description, Integer price, String rules){
        this.name = name;
        this.description = description;
        this.price = price;
        this.rules = rules;
    }


    }
