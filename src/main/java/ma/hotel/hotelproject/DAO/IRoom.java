package ma.hotel.hotelproject.DAO;

import ma.hotel.hotelproject.Beans.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoom extends JpaRepository<Room, Long> {
}
