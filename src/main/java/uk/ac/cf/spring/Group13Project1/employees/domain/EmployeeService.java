package uk.ac.cf.spring.Group13Project1.employees.domain;

import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

import java.util.List;

public interface EmployeeService {

    List<Employee> getEmployees();
    Employee getEmployeeById(Long id);
    void updateEmployee(Employee employee);
    Item save(Item item);
    List<Item> getItems();
    List<Item> getItemByEmployee(String employeeId);
    Employee saveEmployee(Employee employee) throws Exception;

    Boolean isChecklistComplete(Employee employee);

}
