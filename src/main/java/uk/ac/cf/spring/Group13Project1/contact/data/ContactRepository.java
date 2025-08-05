package uk.ac.cf.spring.Group13Project1.contact.data;

import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;

import java.util.List;

public interface ContactRepository {

    List<Contact> getContacts();
    Contact getContactByID(Long id);
    void saveContact(Contact contact);
    void update(Contact contact);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone_number);
}
