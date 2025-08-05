package uk.ac.cf.spring.Group13Project1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private RecaptchaService recaptchaService;

    @GetMapping("/login")
    ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/security/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam("g-recaptcha-response") String gRecaptchaResponse,
                               Model model) {
        boolean recaptchaVerified = recaptchaService.verifyRecaptcha(gRecaptchaResponse);

        if (!recaptchaVerified) {
            model.addAttribute("error", "Captcha verification failed. Please try again.");

            return "/security/login";
        }

        return "redirect:/contacts";
    }

}