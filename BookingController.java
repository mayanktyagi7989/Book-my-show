package dev.aman.bookmyshow.Controllers;

import dev.aman.bookmyshow.DTOs.BookMovieRequestDTO;
import dev.aman.bookmyshow.DTOs.BookMovieResponseDTO;
import dev.aman.bookmyshow.DTOs.ResponseStatus;
import dev.aman.bookmyshow.Models.Booking;
import dev.aman.bookmyshow.Services.BookingService;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDTO bookMovie(BookMovieRequestDTO requestDTO) {

        BookMovieResponseDTO responseDTO = new BookMovieResponseDTO();

        try {
            Booking booking = bookingService.bookMovie(
                    requestDTO.getUserId(),
                    requestDTO.getShowId(),
                    requestDTO.getShowSeatIds()
            );
            responseDTO.setBooking(booking);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e) {
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDTO;
    }
}
