package uk.ac.cf.spring.Group13Project1.passwordReset.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.passwordReset.domain.PasswordResetService;

@Controller
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/reset-password")
    public ModelAndView showResetPasswordForm() {
        ModelAndView modelAndView = new ModelAndView("/passwordReset/reset-password");
        return modelAndView;
    }

    @PostMapping("/reset-password")
    public ModelAndView requestPasswordReset(@RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView("redirect:/login");

        if (email == null || email.isEmpty()) {
            modelAndView.addObject("error", "Please enter your email address");
            return modelAndView;
        }

        if (passwordResetService.requestPasswordReset(email)) {
            modelAndView.addObject("message", "Password reset link sent to your email. Please check your inbox. The link will be valid for 5 minutes.");
        } else {
            modelAndView.addObject("message", "No user found with the provided email address.");
        }
        return modelAndView;
    }


    @GetMapping("/reset-password-confirm/{resetToken}")
    public ModelAndView showResetPasswordConfirmForm(@PathVariable String resetToken) {
        if (passwordResetService.isResetTokenValid(resetToken)) {
            ModelAndView modelAndView = new ModelAndView("/passwordReset/reset-password-confirm");
            modelAndView.addObject("resetToken", resetToken);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/reset-password");
            modelAndView.addObject("message", "Invalid or expired token. Please request a new reset link.");
            return modelAndView;
        }
    }

    @PostMapping("/reset-password-confirm")
    public ModelAndView resetPassword(@RequestParam("resetToken") String resetToken,
                                @RequestParam("password") String newPassword) {

        ModelAndView modelAndView = new ModelAndView("redirect:/reset-password-confirm");

        if (newPassword.isEmpty()) {
            modelAndView.addObject("message", "Please enter new password");
            modelAndView.addObject("resetToken", resetToken);
            return modelAndView;
        }

        String username = passwordResetService.getUsernameByResetToken(resetToken);

        if (username != null) {
            passwordResetService.updatePassword(username, newPassword);
            modelAndView.addObject("message", "Password reset successfully. You can now login with your new password.");
        } else {
            modelAndView.addObject("message", "Invalid or expired token. Please request a new reset link.");
        }

        return modelAndView;
    }
}
