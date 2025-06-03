package dev.aman.bookmyshow.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel {
    private String name;

    @OneToMany
    private List<Seat> seats;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection               // Because it is a collection element
    private List<Feature> features;
}


/*
  1        M
Screen -- Seat  ==> 1:M
  1         1
 */