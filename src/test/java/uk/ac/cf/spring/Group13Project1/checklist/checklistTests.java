package uk.ac.cf.spring.Group13Project1.checklist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class checklistTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "admin")
    public void titleCardShouldPullCorrectInfo() throws Exception {

        MvcResult result = mvc
                .perform(get("/employees/{id}/checklist", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("<h2>John Doe</h2>"));
        assertTrue(content.contains("<span>Manager</span>"));
        assertTrue(content.contains("<span>2023-01-01</span>"));
        assertTrue(content.contains("<a type=\"button\" class=\"btn btn-warning float-end\" href=\"../../employees/1/update\">Edit</a>"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void itemsCheckShouldRedirectToCorrectChecklist() throws Exception {
        mvc.perform(post("/items/check")
                        .param("itemList[0].id", "3")
                        .param("itemList[0].title", "Photo")
                        .param("itemList[0].description", "Get photo for ID card")
                        .param("itemList[0].dueDate", "2023-06-15")
                        .param("itemList[0].ownerId", "2")
                        .param("itemList[0].contactsId", "1")
                        .param("itemList[0].contactsId", "1")
                        .param("itemList[0].isComplete", "true")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/employees/" + 2 + "/checklist"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void checkListShouldDisplayCorrectInfo() throws Exception {

        MvcResult result = mvc
                .perform(get("/employees/{id}/checklist", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("<p>Sign Contract</p>"));
        assertTrue(content.contains("<p>Training Session</p>"));
        assertTrue(content.contains("<span>2023-06-15</span>"));
    }

}
