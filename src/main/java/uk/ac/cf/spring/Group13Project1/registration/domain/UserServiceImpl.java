package uk.ac.cf.spring.Group13Project1.registration.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.cf.spring.Group13Project1.registration.domain.models.User;
import uk.ac.cf.spring.Group13Project1.registration.data.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new Exception("this user already exists.");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            // Handle the exception here, e.g., log it or wrap it in a runtime exception
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getEnabledUsers(){
        return userRepository.findEnabledUsers();
    }

    @Override
    public List<User> getUsers(){
        return userRepository.findUsers();
    }

    @Override
    public void disableUser(Long userId){
        userRepository.disableUser(userId);
    }

    @Override
    public void enableUser(Long userId){
        userRepository.enableUser(userId);
    }

    @Override
    public void deleteUser(Long userId){
        userRepository.deleteUser(userId);
    }

}
