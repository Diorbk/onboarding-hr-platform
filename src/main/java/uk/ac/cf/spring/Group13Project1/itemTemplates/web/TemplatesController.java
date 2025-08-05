package uk.ac.cf.spring.Group13Project1.itemTemplates.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.contact.domain.ContactService;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.TemplatesService;
import uk.ac.cf.spring.Group13Project1.items.domain.ItemService;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;

import java.util.List;

@Controller
public class TemplatesController {

    private final ItemService itemService;
    private final TemplatesService templatesService;
    private final ContactService contactService;

    @Autowired
    public TemplatesController(ItemService itemService, TemplatesService templatesService, ContactService contactService) {
        this.itemService = itemService;
        this.templatesService = templatesService;
        this.contactService = contactService;
    }

    @GetMapping("/templates")
    public ModelAndView viewTemplates() {
        ModelAndView modelAndView = new ModelAndView("itemTemplates/viewTemplates");
        List<Template> templates = templatesService.getTemplates();
        System.out.println(templates.toString());
        modelAndView.addObject("templates", templates);
        return modelAndView;
    }

    @GetMapping("/templates/create")
    public ModelAndView createTemplate() {
        Template newTemplate = new Template();
        newTemplate.setTemplateName("Template name");
        newTemplate = templatesService.saveTemplate(newTemplate);
        String redirectUrl = "redirect:/templates/create/" + newTemplate.getTemplateId();
        ModelAndView modelAndView = new ModelAndView(redirectUrl);
        return modelAndView;
    }

    @GetMapping({"/templates/create/{templateId}", "/templates/{templateId}/edit"})
    public ModelAndView createTemplateForm(@PathVariable String templateId) {
        ModelAndView modelAndView = new ModelAndView("itemTemplates/createTemplate");
        Template template = templatesService.getTemplateById(templateId);
        List<Item> allItems = templatesService.getItemsByOwner("tmp" + templateId);
        modelAndView.addObject("items", allItems);
        modelAndView.addObject("template", template);
        return modelAndView;
    }


    @GetMapping("/templates/{templateId}/{templateName}/addItem")
    public ModelAndView addTemplateItem(@PathVariable Long templateId, @PathVariable String templateName) {
        ModelAndView modelAndView = new ModelAndView("/items/createItem");
        Template template = new Template();
        template.setTemplateId(templateId);
        template.setTemplateName(templateName);
        templatesService.updateTemplate(template);
        String fullTemplateId = "tmp" + templateId;
        java.sql.Date dueDate = new java.sql.Date(0L);
        modelAndView.addObject("item", new Item(0L, "", "", dueDate, false, fullTemplateId, null));
        modelAndView.addObject("contacts", contactService.getContacts());

        return modelAndView;
    }

    @PostMapping("templates/create")
    public ModelAndView saveTemplate(@ModelAttribute("template") Template template) {
        Template newTemplate = new Template();
        newTemplate.setTemplateId(template.getTemplateId());
        newTemplate.setTemplateName(template.getTemplateName());
        template = templatesService.updateTemplate(newTemplate);
        ModelAndView modelAndView = new ModelAndView("redirect:/templates");
        return modelAndView;
    }

    @GetMapping("templates/{templateId}/apply/{userId}")
    public ModelAndView applyTemplate(@PathVariable Long templateId, @PathVariable Long userId) {
        List<Item> allItems = templatesService.getItemsByOwner("tmp" + templateId);
        for (Item item : allItems) {
            Item newItem = new Item();
            newItem.setTitle(item.getTitle());
            newItem.setDescription(item.getDescription());
            newItem.setDueDate(item.getDueDate());
            newItem.setIsComplete(item.getIsComplete());
            newItem.setOwnerId(userId.toString());
            newItem.setContactsId(item.getContactsId());
            templatesService.saveItem(newItem);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/employees/" + userId + "/checklist");
        return modelAndView;
    }

    @GetMapping("templates/{templateId}/delete")
    public ModelAndView deleteTemplate(@PathVariable Long templateId) {
        templatesService.deleteTemplateById(templateId);
        templatesService.deleteItemsByOwner("tmp" + templateId);
        ModelAndView modelAndView = new ModelAndView("redirect:/templates");
        return modelAndView;
    }

    @GetMapping("/templates/{templateId}/item/{itemId}/delete")
    public ModelAndView deleteTemplateItem(@PathVariable Long templateId, @PathVariable Long itemId) {
        templatesService.deleteItemById(itemId);
        ModelAndView modelAndView = new ModelAndView("redirect:/templates/create/" + templateId);
        return modelAndView;
    }

}
