package uk.ac.cf.spring.Group13Project1.viewEmployeesTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class viewEmployeesTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "admin")
    public void shouldShowCorrectChecklist() throws Exception {

        MvcResult result = mvc
                .perform(get("/employees", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("<h2> Open Checklists </h2>"));
        assertTrue(content.contains("<p>John Doe</p>"));
        assertTrue(content.contains("<span>2023-04-05</span>"));

    }

}
