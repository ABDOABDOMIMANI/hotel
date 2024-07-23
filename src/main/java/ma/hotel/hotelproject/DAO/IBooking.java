package ma.hotel.hotelproject.DAO;

import ma.hotel.hotelproject.Beans.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBooking extends JpaRepository<Booking, Integer>{

}
