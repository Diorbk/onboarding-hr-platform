package uk.ac.cf.spring.Group13Project1.email.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.Group13Project1.contact.data.ContactRepository;
import uk.ac.cf.spring.Group13Project1.email.domain.models.EmailDetails;
import uk.ac.cf.spring.Group13Project1.employees.data.EmployeeRepository;
import uk.ac.cf.spring.Group13Project1.items.data.ItemRepository;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired private JavaMailSender javaMailSender;
    @Autowired private ItemRepository itemRepository;
    @Autowired private ContactRepository contactRepository;
    @Autowired private EmployeeRepository employeeRepository;

    @Value("${spring.mail.username}") private String sender;

    public String objectSendMail(EmailDetails details)
    {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public EmailDetails bindEmailDetails(String recipient, String subject, String body) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(recipient);
        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(body);
        return emailDetails;
    }

    public Item getItemById(Long id) { return itemRepository.getItemById(id); }
    public Contact getContactById(Long id) { return contactRepository.getContactByID(id); }
    public Employee getEmployeeById(Long id) { return employeeRepository.getEmployeeById(id); }

}