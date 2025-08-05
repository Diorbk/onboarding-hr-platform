package uk.ac.cf.spring.Group13Project1.employees.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.cf.spring.Group13Project1.employees.data.EmployeeRepository;
import uk.ac.cf.spring.Group13Project1.items.data.ItemRepository;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    public void updateEmployee(Employee updatedEmployee) {
        employeeRepository.updateEmployee(updatedEmployee);
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        try {
            if (employeeRepository.existsByEmail(employee.getEmail())) {
                throw new Exception("An employee with this email already exists.");
            }
            System.out.println("Saving employee: " + employee);
            return employeeRepository.saveEmployee(employee);
        } catch (Exception e) {
            // Handle the exception here, e.g., log it or wrap it in a runtime exception
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.getItems();
    }

    @Override
    public Item save(Item item){
        return itemRepository.saveItem(item);
    }

    @Override
    public List<Item> getItemByEmployee(String employeeId) {
        return itemRepository.getItemsByEmployee(employeeId);
    }

    @Override
    public Boolean isChecklistComplete(Employee employee) {
        List<Item> employeeItems = this.getItemByEmployee((employee.getId()).toString());
        if (employeeItems.isEmpty()) {
            System.out.println("List Empty");
            return false;
        }
        for (Item item : employeeItems) {
            if (!item.getIsComplete()) {
                System.out.println("Isn't complete");
                return false;
            }
        }
        return true;
    }


}
