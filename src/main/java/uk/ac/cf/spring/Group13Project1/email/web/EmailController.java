package uk.ac.cf.spring.Group13Project1.email.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.email.domain.models.EmailDetails;
import uk.ac.cf.spring.Group13Project1.email.domain.EmailService;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

@RestController
public class EmailController {

    @Autowired private EmailService emailService;


    @GetMapping("/email/{itemId}/{emailType}")
    public ModelAndView sendMail(@PathVariable Long itemId, @PathVariable String emailType) {

        Item item = emailService.getItemById(itemId);
        Contact contact = emailService.getContactById(item.getContactsId());
        Employee employee = emailService.getEmployeeById(Long.parseLong(item.getOwnerId()));

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(contact.getEmail());
        emailDetails.setSubject(item.getTitle());

        String messageBody;
        String redirectUrl = "redirect:/employees";

        switch(emailType) {
            case "create":
                messageBody = "You have been added as a contact on item:";
                break;
            case "poke":
                messageBody = "Reminder that item needs completing:";
                break;
            case "complete":
                messageBody = "Item has been marked complete:";
                break;
            case "incomplete":
                messageBody = "Item has been marked incomplete:";
                break;
            default:
                ModelAndView modelAndView = new ModelAndView(redirectUrl);
                return modelAndView;
        }

        messageBody += "\n     Item title: " + item.getTitle() +
                "\n     Due Date: " + item.getDueDate() +
                "\n     Employee name: " + employee.getName() +
                "\n     Employee email: " + employee.getEmail() +
                "\n     Employee start date" + employee.getStartDate() +
                "\n     Employee role: " + employee.getRole() +
                "\n     Item description: " + item.getDescription();

        emailDetails.setMsgBody(messageBody);
        emailService.objectSendMail(emailDetails);

        ModelAndView modelAndView = new ModelAndView(redirectUrl);
        return modelAndView;
    }


}