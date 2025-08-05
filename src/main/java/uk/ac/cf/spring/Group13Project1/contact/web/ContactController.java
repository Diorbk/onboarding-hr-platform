package uk.ac.cf.spring.Group13Project1.contact.web;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.contact.domain.ContactService;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.ContactForm;

import java.util.List;

@RestController
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService aContactService) {
        this.contactService = aContactService;
    }

    @GetMapping("/contacts")
    public ModelAndView getContacts() {
        ModelAndView modelAndView = new ModelAndView("/contact/contactList");
        List<Contact> Contacts = contactService.getContacts();
        modelAndView.addObject("contacts", Contacts);
        return modelAndView;
    }

    @GetMapping("/contacts/{id}")
    public ModelAndView getContacts(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/contact/contactDetails");
        Contact Contact = contactService.getContactByID(id);
        modelAndView.addObject("contact", Contact);
        return modelAndView;
    }

    @GetMapping("/contacts/create")
    public ModelAndView addContact() {
        ModelAndView modelAndView = new ModelAndView("/contact/addContactForm");
        ContactForm emptyContactForm = new ContactForm();
        modelAndView.addObject("contact", emptyContactForm);
        return modelAndView;
    }

    @GetMapping("/contacts/{id}/edit")
    public ModelAndView editContact(@PathVariable Long id) {
        Contact contact = contactService.getContactByID(id);
        ModelAndView modelAndView = new ModelAndView("/contact/editContact");
        modelAndView.addObject("contact", contact);
        return modelAndView;
    }

    @PostMapping("/contacts/update")
    public ModelAndView updateEmployee(@ModelAttribute @Valid Contact updatedContact) {
        contactService.updateContact(updatedContact);
        ModelAndView modelAndView = new ModelAndView("redirect:/contacts");
        modelAndView.addObject("message", "Successfully updated");
        return modelAndView;
    }

    @PostMapping("/contacts")
    public ModelAndView addContact(@PathVariable(value = "id", required = false) Long id,
                                   @Valid @ModelAttribute("contact") ContactForm Contact,
                                   BindingResult bindingResult,
                                   Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/contact/addContactForm", model.asMap());
            return modelAndView;
        } else {
            Contact newContact = new Contact(Contact.getId(), Contact.getName(),Contact.getRole(),Contact.getEmail(),Contact.getPhone_number());
            contactService.save(newContact);
            ModelAndView modelAndView = new ModelAndView("redirect:/contacts");
            return modelAndView;
        }
    }



}
