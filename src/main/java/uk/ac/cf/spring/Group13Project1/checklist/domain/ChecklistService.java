package uk.ac.cf.spring.Group13Project1.checklist.domain;

import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;

import java.util.List;

public interface ChecklistService {

    List<Item> getItemsByEmployee(String employeeId);
    void updateItem(Item item);
    Contact getContactById(Long id);
    Employee getEmployeeById(Long id);
    Item getItemById(Long id);
    List<Template> getTemplates();

}
