package uk.ac.cf.spring.Group13Project1.checklist.web;

import com.structurizr.annotation.UsedByPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.checklist.domain.ChecklistService;
import uk.ac.cf.spring.Group13Project1.email.domain.EmailService;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;
import uk.ac.cf.spring.Group13Project1.items.domain.models.ItemForm;

import java.util.List;

@RestController
//@UsedByPerson(name = "Heather", description = "Admin with checklist",technology = "http(s)")
public class ChecklistController {

    @Autowired EmailService emailService;
    private final ChecklistService checklistService;

    public ChecklistController(ChecklistService checklistService) { this.checklistService = checklistService; }

    @GetMapping("/employees/{id}/checklist")
    public ModelAndView checklist(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("checklist/viewChecklist");
        List<Item> checklistItems = checklistService.getItemsByEmployee(id.toString());
        Employee employee = checklistService.getEmployeeById(id);
        ItemForm itemForm = new ItemForm();
        itemForm.setItemList(checklistItems);
        List<Template> templates = checklistService.getTemplates();
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("itemForm", itemForm);
        modelAndView.addObject("templates", templates);
        return modelAndView;
    }

    @PostMapping("/items/check")
    public ModelAndView updateItem(@ModelAttribute("itemForm") ItemForm itemForm) {
        List<Item> itemList = itemForm.getItemList();
        ModelAndView modelAndView = new ModelAndView("redirect:/employees/" + itemList.get(0).getOwnerId() + "/checklist");
        for (Item item : itemList) {
            checklistService.updateItem(item);
        }
        return modelAndView;
    }

}
