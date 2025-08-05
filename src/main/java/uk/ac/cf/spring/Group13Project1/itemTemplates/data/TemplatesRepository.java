package uk.ac.cf.spring.Group13Project1.itemTemplates.data;

import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;

import java.util.List;

public interface TemplatesRepository {

    Template saveTemplate(Template template);
    Template getTemplateById(String templateId);
    Template updateTemplate(Template template);
    List<Template> getTemplates();
    void deleteTemplateById(Long templateId);

}
