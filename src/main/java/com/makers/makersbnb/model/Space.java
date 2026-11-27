package com.makers.makersbnb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "spaces")
public class Space {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String description;
    private Integer price;
    private String rules;
    private LocalDate availabilityStart;
    private LocalDate availabilityEnd;
    private Long userId;


    public Space(String name, String description, Integer price, String rules, LocalDate availabilityStart, LocalDate availabilityEnd, Long userId){
        this.name = name;
        this.description = description;
        this.price = price;
        this.rules = rules;
        this.availabilityStart = availabilityStart;
        this.availabilityEnd = availabilityEnd;
        this.userId = userId;
    }


    }
