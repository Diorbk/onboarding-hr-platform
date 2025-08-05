package uk.ac.cf.spring.Group13Project1.contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.Group13Project1.contact.data.ContactRepository;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.ContactForm;
import uk.ac.cf.spring.Group13Project1.contact.web.ContactController;
import uk.ac.cf.spring.Group13Project1.contact.domain.ContactService;
import uk.ac.cf.spring.Group13Project1.contact.domain.models.Contact;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
public class getContacts {
    @Autowired
    private MockMvc mvc;
    @Mock
    private JdbcTemplate jdbcTemplate;
    @MockBean
    private ContactService contactService;
    @MockBean
    private ContactRepository contactRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Captor
    private ArgumentCaptor<Contact> contactCaptor;
    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    //testing endpoints
    @Test
    public void testShowContactList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contacts"))

                .andExpect(status().isOk())
                .andExpect(view().name("/contact/contactList"));
    }

    @Test
    public void testGetContacts() throws Exception {

        Contact contact1 = new Contact(1L, "John Doe", "Developer", "john.doe@example.com", 1234567890L);
        Contact contact2 = new Contact(2L, "Jane Smith", "Manager", "jane.smith@example.com", 9876543210L);

        List<Contact> contacts = Arrays.asList(contact1,contact2);
        given(contactService.getContacts()).willReturn(contacts);

        mvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/contact/contactList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("contacts"));
    }

    @Test
    public void testGetContactById() throws Exception {
        Long contactId = 1L;
        Contact mockContact = new Contact(contactId, "John Doe", "Developer", "john.doe@example.com", 1234567890L);
        given(contactService.getContactByID(contactId)).willReturn(mockContact);

        mvc.perform(MockMvcRequestBuilders.get("/contacts/{id}", contactId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/contact/contactDetails"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("contact"))
                .andExpect(MockMvcResultMatchers.model().attribute("contact", mockContact));
    }

    @Test
    public void testAddContact() throws Exception {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = csrfTokenRepository.generateToken(new MockHttpServletRequest());

        mvc.perform(MockMvcRequestBuilders.get("/contacts/create")
                        .with(csrf())
                        .sessionAttr(CsrfToken.class.getName(), csrfToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/contact/addContactForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("contact"))
                .andExpect(MockMvcResultMatchers.model().attribute("contact", instanceOf(ContactForm.class)));
    }

    @Test
    public void testEditContact() throws Exception {
        Long contactId = 1L;
        Contact mockContact = new Contact(contactId, "John Doe", "Developer", "john.doe@example.com", 1234567890L);
        given(contactService.getContactByID(contactId)).willReturn(mockContact);

        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = csrfTokenRepository.generateToken(new MockHttpServletRequest());

        mvc.perform(MockMvcRequestBuilders.get("/contacts/{id}/edit", contactId)
                        .with(csrf())
                        .sessionAttr(CsrfToken.class.getName(), csrfToken))
                .andExpect(status().isOk())
                .andExpect(view().name("/contact/editContact"))
                .andExpect(model().attributeExists("contact"))
                .andExpect(model().attribute("contact", instanceOf(Contact.class)));
    }

    //testing update contact
    @Test
    public void testUpdateContactName(){
        Contact mockContact = new Contact(5L, "John Doe", "Manager", "john.doe@example.com",000L);
        given(contactService.getContactByID(5L)).willReturn(mockContact);

        mockContact.setName("Adam Smith");
        assertEquals(mockContact.getName(), "Adam Smith");
        System.out.println("Contact: " + contactService);
    }

    @Test
    public void testUpdateContactEmail() {
        Contact mockContact = new Contact(5L, "John Doe", "Manager", "john.doe@example.com", 000L);
        given(contactService.getContactByID(5L)).willReturn(mockContact);

        String newEmail = "adam.smith@example.com";
        mockContact.setEmail(newEmail);
        assertEquals(newEmail, mockContact.getEmail());
        System.out.println("Updated Contact: " + mockContact);
    }

    @Test
    public void testUpdateContactPhoneNumber() {
        Long contactId = 5L;
        Contact mockContact = new Contact(contactId, "John Doe", "Manager", "john.doe@example.com", 1234567890L);
        given(contactService.getContactByID(contactId)).willReturn(mockContact);

        Long newPhoneNumber = 9876543210L;
        mockContact.setPhone_number(newPhoneNumber);
        assertEquals(mockContact.getPhoneNumber(), newPhoneNumber);

        System.out.println("Updated Contact: " + mockContact);
    }

    @Test
    public void testUpdateContactRole() {
        Long contactId = 5L;
        Contact mockContact = new Contact(contactId, "John Doe", "Manager", "john.doe@example.com", 000L);
        given(contactService.getContactByID(contactId)).willReturn(mockContact);

        String newRole = "Director";
        mockContact.setRole(newRole);
        contactService.updateContact(mockContact);

        assertEquals(mockContact.getRole(), newRole);
        verify(contactService).updateContact(mockContact);
        System.out.println("Updated Contact: " + mockContact);
    }

    //tests for adding contact
    @Test
    public void testAddContactSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .with(csrf())
                        .param("name", "John Doe")
                        .param("role", "Manager")
                        .param("email", "john.doe@example.com")
                        .param("phone_number", "1234567890"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/contacts"));

        verify(contactService).save(contactCaptor.capture());

        Contact capturedContact = contactCaptor.getValue();
        assertEquals("John Doe", capturedContact.getName());
        assertEquals("Manager", capturedContact.getRole());
        assertEquals("john.doe@example.com", capturedContact.getEmail());
        assertEquals(Long.valueOf(1234567890), capturedContact.getPhone_number());
    }

    @Test
    public void testAddContactInvalidEmail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .with(csrf())
                        .param("name", "John Doe")
                        .param("role", "Manager")
                        .param("email", "invalidemail") // Invalid email format
                        .param("phone_number", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contact/addContactForm"));
    }

    @Test
    public void testAddContactMissingRequiredField() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .with(csrf())
                        .param("name", "") // Missing or empty name field
                        .param("role", "Manager")
                        .param("email", "john.doe@example.com")
                        .param("phone_number", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contact/addContactForm"));

    }
}
