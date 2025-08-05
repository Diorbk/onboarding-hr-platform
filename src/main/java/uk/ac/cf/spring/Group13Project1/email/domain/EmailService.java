package uk.ac.cf.spring.Group13Project1.email.domain;

import uk.ac.cf.spring.Group13Project1.email.domain.models.EmailDetails;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

public interface EmailService {

    String objectSendMail(EmailDetails details);
    EmailDetails bindEmailDetails(String recipient, String subject, String body);

    Item getItemById(Long id);
    Contact getContactById(Long id);
    Employee getEmployeeById(Long id);

}