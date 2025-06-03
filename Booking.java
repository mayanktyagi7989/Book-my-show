package dev.aman.bookmyshow.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// Ticket class
@Getter
@Setter
@Entity
public class Booking extends BaseModel {

    @ManyToMany
    private List<ShowSeat> showSeats;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.ORDINAL)    // Store it in the form of enum
    private BookingStatus bookingStatus;

    private int amount;  // 20050 => 200.50

    @OneToMany
    private List<Payment> payments;
}



/*

   1            M
Booking ---- ShowSeat  ==> M:M
   M             1
   // Consider one showseat belongs to booking1 but it got cancelled
   // then another booking was done on the same showseat booking2

   1        1
Booking -- User ==> M:1
   M         1

      1           M
   Booking --- Payment ==> 1:M
      1            1
 */