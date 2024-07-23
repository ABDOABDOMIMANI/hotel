package ma.hotel.hotelproject.Services;
import ma.hotel.hotelproject.Beans.User;
import ma.hotel.hotelproject.DAO.UserDao;
import ma.hotel.hotelproject.Mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    ////////////////////////////////// REGISTER USER///////////////////////////////////

    public String register(User user) {
        User existingUser = userDao.findByEmail(user.getEmail());
        String hashedPassword = passwordEncoder.encode(user.getMotDePasse());

        if (existingUser != null) {
            return "User already exists with this email";
        }

        // Vérifiez et définissez les valeurs par défaut pour les attributs non obligatoires
        if (user.getNom() == null || user.getNom().isEmpty()) {
            user.setNom(user.getNom());
        }
            user.setMotDePasse(hashedPassword); // Vous devriez hacher le mot de passe avant de l'enregistrer

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole(user.getRole());
        }
        if (user.getDateInscription() == null) {
            user.setDateInscription(new Date());
        }
        if (user.getAdresse() == null) {
            user.setAdresse(user.getAdresse());
        }
        if (user.getTelephone() == null) {
            user.setTelephone(user.getTelephone());
        }

        // Save the user
        userDao.save(user);

        return "User registered successfully";
    }
    ///////////////////////////// REGISTER USER///////////////////////////

    ///////////////////////////// LOGIN USER///////////////////////////
    public User signIn(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getMotDePasse())) {
            return user;
        }
        return null;
    }
    /////////////////////////////////////// LOGIN USER///////////////////////////////////////

    ///////////////////////////////////// FORGET PASSWORD /////////////////////////////////////
    @Transactional
    public User forgotPassword(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            // Generate a token with 8 numbers
            Random random = new Random();
            String token = String.format("%08d", random.nextInt(100000000));
            user.setToken(token);
            userDao.save(user);

            // Store the token in the session

            // Send an email to the user with the token
            try {
                mailService.sendMail(email, "Password Reset", "Your reset token is: " + token);
            } catch (MessagingException e) {
                System.out.println("Error sending email: " + e.getMessage());
            }

            return user;
        } else {
            return null;
        }
    }
    public User resetPassword(String token) {

        User user = userDao.findByToken(token);

        if(user != null) {

            return user;
        }

        return null;
    }
    public User changePassword(String token, String newPassword) {
        User user = userDao.findByToken(token);
        if (user != null) {
            user.setMotDePasse(passwordEncoder.encode(newPassword));
            user.setToken(null);
            userDao.save(user);
            return user;
        }
        return null;
    }
    ///////////////////////////////////////// FORGET PASSWORD ///////////////////////////////////

    ///////////////////////////// UPDATE USER///////////////////////////

    public User updateUser(User updatedUser) {
        User existingUser = userDao.findByEmail(updatedUser.getEmail());
        if (existingUser != null) {
            if (updatedUser.getNom() != null) {
                existingUser.setNom(updatedUser.getNom());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getMotDePasse() != null) {
                existingUser.setMotDePasse(passwordEncoder.encode(updatedUser.getMotDePasse()));
            }
            if (updatedUser.getRole() != null) {
                existingUser.setRole(updatedUser.getRole());
            }
            if (updatedUser.getDateInscription() != null) {
                existingUser.setDateInscription(updatedUser.getDateInscription());
            }
            if (updatedUser.getAdresse() != null) {
                existingUser.setAdresse(updatedUser.getAdresse());
            }
            if (updatedUser.getTelephone() != null) {
                existingUser.setTelephone(updatedUser.getTelephone());
            }
            if (updatedUser.getProfile() != null) {
                existingUser.setProfile(updatedUser.getProfile());
            }
            if (updatedUser.getToken() != null) {
                existingUser.setToken(updatedUser.getToken());
            }

            userDao.save(existingUser);
            return existingUser;
        }
        return null;
    }


    ///////////////////////////// UPDATE USER///////////////////////////////

    ///////////////////////////// DELETE USER///////////////////////////////
    public boolean deleteUser(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            userDao.delete(user);
            return true;
        }
        return false;
    }

    ///////////////////////////// DELETE USER///////////////////////////////

    ///////////////////////////// UPLOAD PROFILE //////////////////////////////
    public User uploadImage(String email, MultipartFile profile) throws IOException {
        String baseUrl = ".\\Images\\Profile\\";
        String filename = StringUtils.cleanPath(profile.getOriginalFilename());

        Path storageDirectory = Paths.get("D:\\Hotel\\phegon-hotel-booking-and-management\\HotelProject\\src\\main\\java\\ma\\hotel\\hotelproject\\Images\\Profile");
        if (!Files.exists(storageDirectory)) {
            Files.createDirectories(storageDirectory);
        }

        Path destinationPath = storageDirectory.resolve(Path.of(filename));
        profile.transferTo(destinationPath);

        User user = userDao.findByEmail(email);
        if (user != null) {
            user.setProfile(baseUrl + filename);  // Save the URL instead of the path
            userDao.save(user);
        }
        return user;
    }
    ///////////////////////////// UPLOAD PROFILE //////////////////////////////
    ///////////////////////////// FIND ALL USERS  //////////////////////////////

    public List<User> findAll(){
        return userDao.findAll();
    }

    ///////////////////////////// FIND ALL USERS //////////////////////////////




}
