package uk.ac.cf.spring.Group13Project1.contact.data;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;

import java.util.List;

@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private JdbcTemplate jdbc;

    private RowMapper<Contact> contactRowMapper;

    public ContactRepositoryImpl(JdbcTemplate aJdbc) {
        this.jdbc = aJdbc;
        setContactRowMapper();
    }

    private void setContactRowMapper() {
        contactRowMapper = (rs, i) -> new Contact(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("role"),
                rs.getString("email"),
                rs.getLong("phone_number")
        );
    }

    public List<Contact> getContacts() {
        String sql = "select * from contacts";
        return jdbc.query(sql, contactRowMapper);
    }

    public Contact getContactByID(Long id) {
        String sql = "select * from contacts where id = ?";
        return jdbc.queryForObject(sql, contactRowMapper, id);
    }

    public void update(Contact contact){
        String sql = "update contacts set name = ?, role = ?, email = ?, phone_number = ? where id = ?";
        try {
            jdbc.update(sql,
                    contact.getName(),
                    contact.getRole(),
                    contact.getEmail(),
                    contact.getPhone_number(),
                    contact.getId()
            );
        } catch (Exception e) {
            System.err.println("Error updating employee with ID: " + contact.getId());
            e.printStackTrace();
            throw e;
        }
    }

    public void saveContact(Contact contact) {
        String contactsInsertSql =
                "insert into contacts " +
                        "(name, role, email, phone_number)" +
                        " values (?,?,?,?)";
        jdbc.update(contactsInsertSql,
                contact.getName(),
                contact.getRole(),
                contact.getEmail(),
                contact.getPhone_number()
        );
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM contacts WHERE email = ?";
        try {
            int count = jdbc.queryForObject(sql, Integer.class, email);
            return count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean existsByPhoneNumber(String phone_number) {
        String sql = "SELECT COUNT(*) FROM contacts WHERE phone_number = ?";
        try {
            int count = jdbc.queryForObject(sql, Integer.class, phone_number);
            return count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
