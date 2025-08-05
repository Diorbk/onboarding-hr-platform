package uk.ac.cf.spring.Group13Project1.registration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ViewRegistrationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void shouldGetTwoUsers() throws Exception {

        MvcResult result = mvc
                .perform(get("/userList"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("<td>ben</td>"));
        assertTrue(content.contains("<td>charlotte</td>"));
    }


    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testShowEditEmployeeForm() throws Exception {

        mvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/registration"));
    }

//    @Test
//    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
//    void registerUserTest() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.post("/register")
//                        .param("username", "test")
//                        .param("password", "test")
//                        .with(csrf()))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(view().name("redirect:/userList"))
//                .andDo(print());
//    }


}
