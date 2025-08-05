package uk.ac.cf.spring.Group13Project1.newEmail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.Group13Project1.email.domain.models.EmailDetails;
import uk.ac.cf.spring.Group13Project1.email.domain.EmailService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class newEmailTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(sharedHttpSession())
                .build();
    }

    @Test
    @WithMockUser(roles = "admin")
    public void shouldRedirectToEmployeesAfterEmail() throws Exception {
        mvc
                .perform(get("/email/{itemId}/{emailType}", 1L, "create"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/employees"))
                .andDo(print());
    }

    @Test
    public void objectSendMailReturnsSuccess() throws Exception {

        EmailDetails emailDetails = new EmailDetails();

        emailDetails.setSubject("Email test");
        emailDetails.setRecipient("newstarterbipsync@gmail.com");
        emailDetails.setMsgBody("Email test");

        String emailResult = emailService.objectSendMail(emailDetails);

        assertEquals("Mail Sent Successfully", emailResult);
    }

    @Test
    public void emailIsSentAfterItemCreated() throws Exception {

        mvc.perform(post("/items/create")
                        .param("ownerId", "1")
                        .param("title", "Test item")
                        .param("description", "Test description")
                        .param("dueDate", "2020-12-12")
                        .param("contactsId", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/email/" + 8 + "/create"))
                .andDo(print());

    }

}
