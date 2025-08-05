package uk.ac.cf.spring.Group13Project1.passwordReset.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordResetRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean checkIfEmailExists(String email) {
        String checkEmailQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
        int userCount = jdbcTemplate.queryForObject(checkEmailQuery, Integer.class, email);
        return userCount > 0;
    }

    public void updateResetToken(String email, String resetToken) {
        String updateTokenQuery = "UPDATE users SET reset_token = ? WHERE email = ?";
        jdbcTemplate.update(updateTokenQuery, resetToken, email);
    }

    public String getUsernameByResetToken(String resetToken) {
        String getUserQuery = "SELECT username FROM users WHERE reset_token = ?";
        return jdbcTemplate.queryForObject(getUserQuery, String.class, resetToken);
    }

    public void updatePasswordAndResetToken(String username, String encodedPassword) {
        String updatePasswordQuery = "UPDATE users SET password = ?, reset_token = null WHERE username = ?";
        jdbcTemplate.update(updatePasswordQuery, encodedPassword, username);
    }
}
