package uk.ac.cf.spring.Group13Project1.passwordReset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.Group13Project1.passwordReset.data.PasswordResetRepository;
import uk.ac.cf.spring.Group13Project1.passwordReset.domain.PasswordEmailService;
import uk.ac.cf.spring.Group13Project1.passwordReset.domain.PasswordResetService;
import uk.ac.cf.spring.Group13Project1.passwordReset.web.PasswordResetController;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PasswordResetController.class)
@AutoConfigureMockMvc(addFilters = false)
public class passwordResetTest {

    @Autowired
    private MockMvc mvc;
    @Mock
    private JdbcTemplate jdbcTemplate;
    @MockBean
    private PasswordResetService passwordResetService;
    @MockBean
    private PasswordEmailService passwordEmailService;
    @MockBean
    private PasswordResetRepository passwordResetRepository;
    @Mock
    private JavaMailSender javaMailSender;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void showResetPasswordForm_ShouldReturnResetPasswordView() throws Exception {
        mvc.perform(get("/reset-password"))
                .andExpect(view().name("/passwordReset/reset-password"));
    }

    @Test
    public void showResetPasswordConfirmForm_WithValidToken_ShouldReturnConfirmView() throws Exception {
        String validToken = "valid-token";
        when(passwordResetService.isResetTokenValid(validToken)).thenReturn(true);

        mvc.perform(get("/reset-password-confirm/{resetToken}", validToken))
                .andExpect(view().name("/passwordReset/reset-password-confirm"));
    }

    @Test
    public void showResetPasswordConfirmForm_WithInvalidToken_ShouldRedirectToResetPassword() throws Exception {
        String invalidToken = "invalid-token";
        when(passwordResetService.isResetTokenValid(invalidToken)).thenReturn(false);

        mvc.perform(get("/reset-password-confirm/{resetToken}", invalidToken))
                .andExpect(redirectedUrl("/reset-password?message=Invalid+or+expired+token.+Please+request+a+new+reset+link."));
    }

    @Test
    public void requestPasswordReset_WithInvalidEmail_ShouldReturnError() throws Exception {
        String invalidEmail = "";
        when(passwordResetService.requestPasswordReset(anyString())).thenReturn(false);

        mvc.perform(post("/reset-password").param("email", invalidEmail))
                .andExpect(model().attributeExists("error"))
                .andExpect(redirectedUrl("/login?error=Please+enter+your+email+address"));
    }

    @Test
    public void requestPasswordReset_WithEmailNotFound_ShouldReturnError() throws Exception {
        String emailNotFound = "notfound@example.com";
        when(passwordResetService.requestPasswordReset(emailNotFound)).thenReturn(false);

        mvc.perform(post("/reset-password").param("email", emailNotFound))
                .andExpect(model().attributeExists("message"))
                .andExpect(redirectedUrl("/login?message=No+user+found+with+the+provided+email+address."));
    }

    @Test
    public void requestPasswordReset_WithValidEmail_ShouldSendResetLink() throws Exception {
        String validEmail = "user@example.com";
        when(passwordResetService.requestPasswordReset(validEmail)).thenReturn(true);

        mvc.perform(post("/reset-password").param("email", validEmail))
                .andExpect(model().attributeExists("message"))
                .andExpect(redirectedUrl("/login?message=Password+reset+link+sent+to+your+email.+Please+check+your+inbox.+The+link+will+be+valid+for+5+minutes."));
    }

    @Test
    public void resetPassword_WithValidTokenAndNewPassword_ShouldResetSuccessfully() throws Exception {
        String validToken = "valid-token";
        String newPassword = "newPassword123";
        String username = "user123";

        when(passwordResetService.getUsernameByResetToken(validToken)).thenReturn(username);
        mvc.perform(post("/reset-password-confirm")
                        .param("resetToken", validToken)
                        .param("password", newPassword))
                .andExpect(view().name("redirect:/reset-password-confirm"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    public void resetPassword_WithEmptyNewPassword_ShouldReturnError() throws Exception {
        String validToken = "valid-token";
        String emptyPassword = "";

        mvc.perform(post("/reset-password-confirm")
                        .param("resetToken", validToken)
                        .param("password", emptyPassword))
                .andExpect(view().name("redirect:/reset-password-confirm"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("resetToken", validToken));
    }

    @Test
    public void resetPassword_WithInvalidToken_ShouldReturnError() throws Exception {
        String invalidToken = "invalid-token";
        String newPassword = "newPassword123";

        when(passwordResetService.getUsernameByResetToken(invalidToken)).thenReturn(null);
        mvc.perform(post("/reset-password-confirm")
                        .param("resetToken", invalidToken)
                        .param("password", newPassword))
                .andExpect(view().name("redirect:/reset-password-confirm"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    public void requestPasswordReset_WithEmailDoesNotExist_ShouldReturnFalse() {
        String email = "nonexistent@example.com";
        when(passwordResetRepository.checkIfEmailExists(email)).thenReturn(false);

        boolean result = passwordResetService.requestPasswordReset(email);

        verify(passwordResetRepository, never()).updateResetToken(eq(email), anyString());
        assertFalse(result);
    }
}
