package uk.ac.cf.spring.Group13Project1.passwordReset.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.Group13Project1.passwordReset.data.PasswordResetRepository;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class PasswordResetService {
    @Autowired
    private PasswordResetRepository PasswordResetRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordEmailService passwordEmailService;
    private final ConcurrentMap<String, LocalDateTime> resetTokenMap = new ConcurrentHashMap<>();
    public boolean requestPasswordReset(String email) {
        if (PasswordResetRepository.checkIfEmailExists(email)) {
            String resetToken = generateUniqueResetToken();
            PasswordResetRepository.updateResetToken(email, resetToken);
            resetTokenMap.put(resetToken, LocalDateTime.now()); // Store timestamp
            sendPasswordResetEmail(email, resetToken);
            return true;
        }
        return false;
    }
    public boolean isResetTokenValid(String resetToken) {
        LocalDateTime timestamp = resetTokenMap.get(resetToken);
        if (timestamp != null) {
            LocalDateTime now = LocalDateTime.now();
            // Check if 5 minutes have passed since the token was generated
            if (now.minusMinutes(5).isBefore(timestamp)) {
                return true;
            } else {
                resetTokenMap.remove(resetToken); // Remove expired token from map
            }
        }
        return false;
    }
    public String getUsernameByResetToken(String resetToken) {
        return PasswordResetRepository.getUsernameByResetToken(resetToken);
    }
    public void updatePassword(String username, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword); // Use password encoder
        PasswordResetRepository.updatePasswordAndResetToken(username, encodedPassword);
    }
    private String generateUniqueResetToken() {
        return UUID.randomUUID().toString();
    }
    public void sendPasswordResetEmail(String email, String resetToken) {
        passwordEmailService.sendPasswordResetEmail(email, resetToken);
    }
}
