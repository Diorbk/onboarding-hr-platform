package uk.ac.cf.spring.Group13Project1.registration.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.registration.domain.models.User;
import uk.ac.cf.spring.Group13Project1.registration.domain.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        System.out.println("Directing to Registration form");
        ModelAndView modelAndView =new ModelAndView("security/registration");

        modelAndView.addObject("user", new User(0L, "defaultName", "defaultPassword","defaulEmail", true, 0L, null));
        return modelAndView; // Return the registration form view
    }

    @PostMapping("/register")
    public String processRegistration(User user) throws Exception {

        System.out.println("User: " + user.toString());

        try {
            userService.registerUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(userService.getUsers().toString());

        return "redirect:/userList";
    }

    @GetMapping("/userList")
    public ModelAndView showUserList(){
        ModelAndView modelAndView = new ModelAndView("security/userList");
        List<User> enabledUsers = userService.getUsers();

        modelAndView.addObject("users", enabledUsers);

        return modelAndView;
    }

    @PostMapping("/disableUser")
    public String disableUser(@RequestParam("userId") Long userId) {
        System.out.println("Disabling user with ID: " + userId);

        userService.disableUser(userId);

        return "redirect:/userList";
    }

    @PostMapping("/enableUser")
    public String enableUser(@RequestParam("userId") Long userId) {
        System.out.println("Enabling user with ID: " + userId);

        userService.enableUser(userId);

        return "redirect:/userList";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId) {
        System.out.println("Deleting user with ID: " + userId);

        userService.deleteUser(userId);

        return "redirect:/userList";
    }

}

