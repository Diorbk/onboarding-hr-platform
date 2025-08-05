package uk.ac.cf.spring.Group13Project1.itemTemplates.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.Group13Project1.items.data.ItemRepository;
import uk.ac.cf.spring.Group13Project1.itemTemplates.data.TemplatesRepository;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;

import java.util.List;

@Service
public class TemplatesServiceImpl implements TemplatesService {

    @Autowired
    TemplatesRepository templatesRepository;

    @Autowired
    ItemRepository itemRepository;

    public Template saveTemplate(Template template) { return templatesRepository.saveTemplate(template); }
    public Template getTemplateById(String templateId) { return templatesRepository.getTemplateById(templateId); }
    public Template updateTemplate(Template template) { return templatesRepository.updateTemplate(template); }
    public List<Template> getTemplates() { return templatesRepository.getTemplates(); };
    public void deleteTemplateById(Long templateId) { templatesRepository.deleteTemplateById(templateId); }

    public List<Item> getItemsByOwner(String ownerId) { return itemRepository.getItemsByEmployee(ownerId); }
    public Item saveItem(Item item) { return itemRepository.saveItem(item); }
    public void deleteItemById(Long itemId) { itemRepository.deleteItemById(itemId); }
    public void deleteItemsByOwner(String ownerId) {itemRepository.deleteItemsByOwner(ownerId);}

}
