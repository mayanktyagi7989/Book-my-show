package dev.aman.bookmyshow.Services;

import dev.aman.bookmyshow.Exceptions.ShowNotFoundException;
import dev.aman.bookmyshow.Exceptions.UserNotFoundException;
import dev.aman.bookmyshow.Models.*;
import dev.aman.bookmyshow.Repositories.ShowRepository;
import dev.aman.bookmyshow.Repositories.ShowSeatRepository;
import dev.aman.bookmyshow.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculatorService priceCalculatorService;

    public BookingService(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, PriceCalculatorService priceCalculatorService) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculatorService = priceCalculatorService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException, ShowNotFoundException {

        /*
        1. Get the user with the userId.
        2. Get the show with the showId.
        3. Get the list of show seats with the showSeatIds.
        4. Check if all the seats are available or not.
        5. if yes, proceed with the booking.
        6. if not, throw an exception.
        ---------TAKE A LOCK---------
        7. Check again if all the seats are available or not.
        8. Change the seat status to BLOCKED.
        ------RELEASE THE LOCK-------
        9. Create the booking and move to the payment page.
         */

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with email id " + userId + " doesn't exist.");
        }

        User user = optionalUser.get();

        Optional<Show> optionalShow = showRepository.findById(showId);

        if (optionalShow.isEmpty()) {
            throw new ShowNotFoundException("Show with id: " + showId + "doesn't exist.");
        }

        Show show = optionalShow.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new RuntimeException(("ShowSeat with id " + showSeat.getId() + " isn't available."));
            }
        }

        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            // change the status in database as well.
            showSeatRepository.save(showSeat);
        }

        // Create the booking and Move to the payment page.

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowSeats(showSeats);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(new Date());
        booking.setAmount(priceCalculatorService.calculatePrice(showSeats, show));

        // Save booking in the DB.

        return booking;
    }
}

/*
But eh point is implementing above functionalities in spring boot is difficult.
So what we will do is we will do the first approach.
We will take the complete thing in the transaction.
 */
