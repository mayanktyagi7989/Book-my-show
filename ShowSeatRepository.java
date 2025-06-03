package dev.aman.bookmyshow.Repositories;

import dev.aman.bookmyshow.Models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Override
    List<ShowSeat> findAllById(Iterable<Long> longs);
    // For some reason List is not working Iterable is working.

    // If the input parameter has an id attribute only - it will update the data
    // else it will create the data.
    // Thats how save method works.
    @Override
    ShowSeat save(ShowSeat showSeat);
}


