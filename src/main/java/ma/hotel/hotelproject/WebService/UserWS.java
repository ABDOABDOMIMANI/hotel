package ma.hotel.hotelproject.WebService;

import ma.hotel.hotelproject.Beans.User;
import ma.hotel.hotelproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Hotel/User")
public class UserWS {

     @Autowired
     private UserService userService;
     ///////////////////////////// REGISTER USER///////////////////////////
     @PostMapping("/register/")
        public String findUserById(@RequestBody User user) {
            return userService.register( user);
        }
    ///////////////////////////// REGISTER USER///////////////////////////

    ///////////////////////////// LOGIN USER///////////////////////////
    @GetMapping("/login/email/{email}/password/{password}")
    public User signIn(@PathVariable String email, @PathVariable String password) {
        return userService.signIn(email, password);
    }
    ///////////////////////////// LOGIN USER///////////////////////////
    ///////////////////////////// FORGET  PASSWORD///////////////////////////

    @PostMapping ("/forgetPassword/email/{email}")
    public User forgetPassword(@PathVariable String email) {
        return userService.forgotPassword(email);
    }

    @PostMapping("/resetPassword/token/{token}")

    public User resetPassword(@PathVariable String token) {
        return userService.resetPassword(token);
    }

    @PostMapping("/changePassword/token/{token}/password/{password}")
    @Transactional
    public User changePassword(@PathVariable String token,@PathVariable String password) {
        return userService.changePassword(token,password);
    }
    ///////////////////////////// FORGET  PASSWORD///////////////////////////
    ///////////////////////////// UPDATE USER///////////////////////////
    @PostMapping("/updateUser")
    public User updateUser(@RequestBody User updatedUser) {
        return userService.updateUser(updatedUser);
    }

    ///////////////////////////// UPDATE USER///////////////////////////

    ///////////////////////////// DELETE USER///////////////////////////
    @DeleteMapping("/deleteUser/{email}")
    public String deleteUser(@PathVariable String email) {
         userService.deleteUser(email);
        return "User deleted successfully";
    }
    ///////////////////////////// DELETE USER///////////////////////////

    //////////////////////////// UPLOAD IMAGE///////////////////////////
    @PostMapping(value = "/uploadImage", consumes = {"multipart/form-data"})
    public User uploadImage(@RequestParam("email") String email,
                            @RequestParam("profile") MultipartFile profile) throws IOException {
        return userService.uploadImage(email, profile);
    }

    //////////////////////////// UPLOAD IMAGE///////////////////////////

    //////////////////////////// FIND ALL USERS///////////////////////////
    @GetMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }
    //////////////////////////// FIND ALL USERS///////////////////////////




}
