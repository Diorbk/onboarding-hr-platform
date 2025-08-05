package uk.ac.cf.spring.Group13Project1.registration.data;

import uk.ac.cf.spring.Group13Project1.registration.domain.models.User;

import java.util.List;

public interface UserRepository {

    User save(User user);
    User findUserByUsername(String username);
    List<User> findEnabledUsers();
    User findUserByEmail(String email);
    List<User> findUsers();
    void disableUser(Long userId);
    void enableUser(Long userId);
    void deleteUser(Long userId);
    boolean existsByUsername(String username);
}