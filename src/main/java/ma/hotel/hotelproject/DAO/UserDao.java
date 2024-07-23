package ma.hotel.hotelproject.DAO;

import ma.hotel.hotelproject.Beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User  , Long> {
    User findByEmail(String email);
    User findByToken(String token);


    List<User> findAll();
}
