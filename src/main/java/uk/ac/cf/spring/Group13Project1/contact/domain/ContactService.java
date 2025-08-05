package uk.ac.cf.spring.Group13Project1.contact.domain;

import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getContacts();
    Contact getContactByID(Long id);
    void save(Contact Contact);
    void updateContact(Contact contact);
}
