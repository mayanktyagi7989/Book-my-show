package dev.aman.bookmyshow.DTOs;

import dev.aman.bookmyshow.Models.Booking;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDTO {
    private Booking booking;
    private ResponseStatus responseStatus;
}
