package ma.hotel.hotelproject.DAO;

import ma.hotel.hotelproject.Beans.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReview extends JpaRepository<Review, Long> {
}
