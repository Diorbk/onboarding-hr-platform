package uk.ac.cf.spring.Group13Project1.employees.data;

import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;

import java.util.List;

public interface EmployeeRepository {

    Employee getEmployeeById(Long id);
    List<Employee> getEmployees();
    void updateEmployee(Employee employee);
    Employee saveEmployee(Employee employee);
    void deleteEmployeeById(Long id);

    boolean existsByEmail(String email);

}
