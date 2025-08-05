package uk.ac.cf.spring.Group13Project1.employees.data;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private JdbcTemplate jdbc;
    private RowMapper<Employee> employeeRowMapper;

    public EmployeeRepositoryImpl(JdbcTemplate aJdbc) {
        this.jdbc = aJdbc;
        setEmployeeRowMapper();
    }

    private void setEmployeeRowMapper() {
        employeeRowMapper = (rs, i) -> new Employee(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("role"),
                rs.getString("email"),
                rs.getDate("start_date")

        );
    }

    public Employee getEmployeeById(Long id) {
        String sql = "select * from employees where id = ?";
        return jdbc.queryForObject(sql, employeeRowMapper, id);
    }

    public List<Employee> getEmployees() {
        String sql = "SELECT * FROM employees";
        return jdbc.query(sql, employeeRowMapper);
    }

    public void updateEmployee(Employee employee){
        String sql = "update employees set name = ?, role = ?, email = ?, start_date = ? where id = ?";
        try {
            jdbc.update(sql,
                    employee.getName(),
                    employee.getRole(),
                    employee.getEmail(),
                    employee.getStartDate(),
                    employee.getId()
            );
        } catch (Exception e) {
            System.err.println("Error updating employee with ID: " + employee.getId());
            e.printStackTrace();
            throw e;
        }
    }

    public Employee saveEmployee(Employee employee) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("employees")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", employee.getName());
        parameters.put("role", employee.getRole());
        parameters.put("email", employee.getEmail());
        parameters.put("start_date", employee.getStartDate());

        Number newEmployeeId = jdbcInsert.executeAndReturnKey(parameters);
        employee.setId(newEmployeeId.longValue());
        return employee;
    }

    public void deleteEmployeeById(Long id){
        String sql = "DELETE FROM employees WHERE id=?";
        jdbc.update(sql, id);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM employees WHERE email = ?";
        try {
            int count = jdbc.queryForObject(sql, Integer.class, email);
            return count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
