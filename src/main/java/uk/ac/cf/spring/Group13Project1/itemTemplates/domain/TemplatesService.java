package uk.ac.cf.spring.Group13Project1.itemTemplates.domain;

import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;

import java.util.List;

public interface TemplatesService {

    Template saveTemplate(Template template);
    Template getTemplateById(String templateId);
    Template updateTemplate(Template template);
    List<Template> getTemplates();
    void deleteTemplateById(Long templateId);

    List<Item> getItemsByOwner(String ownerId);
    Item saveItem(Item item);
    void deleteItemById(Long itemId);
    void deleteItemsByOwner(String ownerId);

}
