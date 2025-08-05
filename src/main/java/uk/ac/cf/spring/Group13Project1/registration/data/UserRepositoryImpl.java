package uk.ac.cf.spring.Group13Project1.registration.data;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.Group13Project1.registration.domain.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbc;
    private RowMapper<User> userItemMapper;
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
        setUserItemMapper();
    }

    private void setUserItemMapper() {

        userItemMapper = (rs, i) -> new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getBoolean("enabled"),
                rs.getLong("role_id"),
                rs.getLong("assigned_id")
        );
    }

    @Override
    public User save(User user) {

        System.out.println("We getting here.");

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("email", user.getEmail());
        parameters.put("enabled", user.isEnabled());
        parameters.put("role_id", user.getRoleId());
        parameters.put("assigned_id", user.getAssignedId());

        Number newUserId = jdbcInsert.executeAndReturnKey(parameters);
        user.setId(newUserId.longValue());
        return user;
    }

    public User findUserByUsername(String username){
        String sql = "select * from users where username = ?";
        return jdbc.queryForObject(sql, userItemMapper, username);
    }

    public User findUserByEmail(String email){
        String sql = "select * from users where email = ?";
        return jdbc.queryForObject(sql, userItemMapper, email);
    }

    public List<User> findUsers(){
        String sql = "select * from users where role_id != 1";
        return jdbc.query(sql, userItemMapper);
    }

    public List<User> findEnabledUsers(){
        String sql = "select * from users where enabled = true";
        return jdbc.query(sql, userItemMapper);
    }

    public void disableUser(Long userId){
        String sql = "update users set enabled = false where id = ?";
        jdbc.update(sql, userId);
    }

    public void enableUser(Long userId){
        String sql = "update users set enabled = true where id = ?";
        jdbc.update(sql, userId);
    }

    public void deleteUser(Long userId){
        String sql = "delete from users where id = ?";
        jdbc.update(sql, userId);
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try {
            int count = jdbc.queryForObject(sql, Integer.class, username);
            return count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
