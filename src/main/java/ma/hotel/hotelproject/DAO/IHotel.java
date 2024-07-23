package ma.hotel.hotelproject.DAO;

import ma.hotel.hotelproject.Beans.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotel extends JpaRepository<Hotel, Long> {
}
