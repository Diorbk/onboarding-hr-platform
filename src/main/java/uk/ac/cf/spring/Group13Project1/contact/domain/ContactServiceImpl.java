package uk.ac.cf.spring.Group13Project1.contact.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.cf.spring.Group13Project1.contact.data.ContactRepository;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    public List<Contact> getContacts(){return contactRepository.getContacts();}
    public Contact getContactByID(Long id){return contactRepository.getContactByID(id);}
    @Transactional
    public void save(Contact contact) {
        try {
            String phone_number = Long.toString(contact.getPhoneNumber());
            if (contactRepository.existsByEmail(contact.getEmail())) {
                throw new Exception("A contact with this email already exists.");
            } else if (contactRepository.existsByPhoneNumber(phone_number)){
                throw new Exception("A contact with this phone number already exists.");
            }
            contactRepository.saveContact(contact);
        } catch (Exception e) {
            // Handle the exception here, e.g., log it or wrap it in a runtime exception
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void updateContact(Contact contact){
        contactRepository.update(contact);
    }

}
