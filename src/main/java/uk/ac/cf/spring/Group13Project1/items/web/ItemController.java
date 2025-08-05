package uk.ac.cf.spring.Group13Project1.items.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.contact.domain.ContactService;
import uk.ac.cf.spring.Group13Project1.employees.domain.EmployeeService;
import uk.ac.cf.spring.Group13Project1.items.domain.ItemService;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

@RestController
public class ItemController {

    private final ItemService itemService;
    private final EmployeeService employeeService;
    private final ContactService contactService;

    @Autowired
    public ItemController(ItemService itemService, EmployeeService employeeService, ContactService contactService) {
        this.itemService = itemService;
        this.employeeService = employeeService;
        this.contactService = contactService;
    }

    // -- VIEW ITEM --
    @GetMapping("/items/{id}")
    public ModelAndView viewItem(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("items/viewItem");
        Item item = itemService.getItemById(id);
        modelAndView.addObject("item", item);
        modelAndView.addObject("contactService", contactService);
        return modelAndView;
    }

    // -- UPDATE --
    @GetMapping("/items/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        ModelAndView modelAndView = new ModelAndView("/items/editItem");
        modelAndView.addObject("item", item);
        modelAndView.addObject("contacts", contactService.getContacts());
        return modelAndView;
    }

    @PostMapping("/items/update")
    public ModelAndView updateItem(@ModelAttribute @Valid Item item) {
        itemService.updateItem(item);
        String redirectUrl;
        if (item.getOwnerId().contains("tmp")) {
            redirectUrl = "redirect:/templates/create/" + item.getOwnerId().substring(3);
        } else {
            redirectUrl = "redirect:/employees/" + item.getOwnerId() + "/checklist";
        }
        ModelAndView modelAndView = new ModelAndView(redirectUrl);
        return modelAndView;
    }

    // -- CREATE --
    @GetMapping("/items/create/{ownerId}")
    public ModelAndView createItem(@PathVariable String ownerId) {
        ModelAndView modelAndView = new ModelAndView("/items/createItem");
        modelAndView.addObject("item", new Item(0L, "", "", null, false, ownerId, null));
        modelAndView.addObject("employee", ownerId);
        modelAndView.addObject("contacts", contactService.getContacts());
        return modelAndView;
    }

    @PostMapping("/items/create")
    public ModelAndView createItem(@ModelAttribute("item") Item item) {
        System.out.println("Received item: " + item);

        Item savedItem = itemService.saveItem(item);
        String redirectUrl;
        if (item.getOwnerId().contains("tmp")) {
            redirectUrl = "redirect:/templates/create/" + item.getOwnerId().substring(3);
        } else {
            redirectUrl = "redirect:/email/" + savedItem.getId() + "/create";
        }
        ModelAndView modelAndView = new ModelAndView(redirectUrl);
        return modelAndView;
    }

}
