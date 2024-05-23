package com.s1511.Ticketcine.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "seats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    private Long id;
    private String seatNumber;
    private LocalDateTime reservationTime;
    private boolean reserved;

//    private Cinema cinema;
//
//    private Movie movie;
//
//    private Hall hall;
//
//     private Screening screening;
//
//    @ManyToOne
//    @JoinColumn(name = "current_user_id")
//    private User currentUser;
//
//    @ManyToOne
//    @JoinColumn(name = "previous_user_id")
//    private User previousUser;


    private Availability availability;

    public enum Availability {
        AVAILABLE,
        OCCUPIED,
        RESERVED
    }
}