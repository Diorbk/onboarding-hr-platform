package uk.ac.cf.spring.Group13Project1.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final RecaptchaService recaptchaService;

    public CustomAuthenticationFilter(RecaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String recaptchaResponse = request.getParameter("g-recaptcha-response");
        if (!recaptchaService.verifyRecaptcha(recaptchaResponse)) {
            throw new BadCredentialsException("reCAPTCHA validation failed");
        }

        return super.attemptAuthentication(request, response);
    }
}
