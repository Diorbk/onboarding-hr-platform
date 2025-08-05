package uk.ac.cf.spring.Group13Project1.templates;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.ac.cf.spring.Group13Project1.itemTemplates.data.TemplatesRepository;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class templatesTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TemplatesRepository templatesRepository;

    @Test
    @WithMockUser(roles = "admin")
    public void templateSaveShouldStoreIdAndName() throws Exception {

        Template template = new Template();
        template.setTemplateName("testTemplateSave");
        Template templateResult = templatesRepository.saveTemplate(template);

        assertNotNull(templateResult.getTemplateId());
        assertEquals("testTemplateSave", templateResult.getTemplateName());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void templateUpdateShouldCorrectlyUpdateTemplate() throws Exception {

        Template template = new Template();
        template.setTemplateName("testTemplateUpdateEdit");
        Template templateResult = templatesRepository.saveTemplate(template);

        templateResult.setTemplateName("testTemplateUpdateEdit");
        Template updatedTemplate = templatesRepository.updateTemplate(templateResult);

        assertNotNull(updatedTemplate.getTemplateId());
        assertEquals("testTemplateUpdateEdit", updatedTemplate.getTemplateName());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void shouldBeAblePostToCreateTemplate() throws Exception {
        mvc.perform(post("/templates/create")
                        .param("templateName", "testTemplate")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/templates"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void viewTemplatesShouldGetCorecctInformation() throws Exception {

        MvcResult result = mvc
                .perform(get("/templates", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("<p>Software Engineer</p>"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void titleCardShouldPullCorrectInfo() throws Exception {

        MvcResult result = mvc
                .perform(get("/templates/1/edit", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("value=\"Software Engineer\""));
        assertTrue(content.contains("<p>Setup workstation</p>"));
        assertTrue(content.contains("<p>Create git account</p>"));
    }

}
