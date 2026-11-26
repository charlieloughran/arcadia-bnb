package com.makers.makersbnb.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter @Setter @NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate booked_from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate booked_until;


    @Column(name= "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name="space_id")
    private Space space;


    public Booking(LocalDate booked_from, LocalDate booked_until, String userId, Space space) {
        this.booked_from = booked_from;
        this.booked_until = booked_until;
        this.userId = userId;
        this.space = space;
    }
}
