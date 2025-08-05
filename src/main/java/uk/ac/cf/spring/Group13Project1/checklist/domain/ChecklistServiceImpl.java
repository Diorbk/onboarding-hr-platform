package uk.ac.cf.spring.Group13Project1.checklist.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.Group13Project1.contact.data.ContactRepository;
import uk.ac.cf.spring.Group13Project1.employees.data.EmployeeRepository;
import uk.ac.cf.spring.Group13Project1.items.data.ItemRepository;
import uk.ac.cf.spring.Group13Project1.itemTemplates.data.TemplatesRepository;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;

import java.util.List;

@Service
public class ChecklistServiceImpl implements ChecklistService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TemplatesRepository templatesRepository;

    @Autowired
    ContactRepository contactRepository;

    @Override
    public List<Item> getItemsByEmployee(String employeeId) {
        return itemRepository.getItemsByEmployee(employeeId);
    }

    @Override
    public Employee getEmployeeById(Long id) { return employeeRepository.getEmployeeById(id); }

    @Override
    public void updateItem(Item updatedItem) {
        itemRepository.updateItem(updatedItem);
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.getItemById(id);
    }

    @Override
    public List<Template> getTemplates() { return templatesRepository.getTemplates(); }

    @Override
    public Contact getContactById(Long id) { return contactRepository.getContactByID(id); }


}
