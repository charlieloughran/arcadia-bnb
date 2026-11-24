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
@Table(name = "SPACES")
public class Space {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String description;
    private Integer price;
    private String rules;
    private LocalDate availability_start;
    private LocalDate availability_end;


    public Space(String name, String description, Integer price, String rules, LocalDate availability_start, LocalDate availability_end){
        this.name = name;
        this.description = description;
        this.price = price;
        this.rules = rules;
        this.availability_start = availability_start;
        this.availability_end = availability_end;
    }


    }
