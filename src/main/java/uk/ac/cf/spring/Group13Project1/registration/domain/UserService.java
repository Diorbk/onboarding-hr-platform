package uk.ac.cf.spring.Group13Project1.registration.domain;

import uk.ac.cf.spring.Group13Project1.registration.domain.models.User;

import java.util.List;

public interface UserService {

    User registerUser(User user) throws Exception;
    User getUserByUsername(String username);
    List<User> getEnabledUsers();
    List<User> getUsers();
    void disableUser(Long userId);
    void enableUser(Long userId);
    void deleteUser(Long userId);

}
