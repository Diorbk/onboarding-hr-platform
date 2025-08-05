package uk.ac.cf.spring.Group13Project1.employees.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.employees.domain.EmployeeService;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeesController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // -- VIEW EMPLOYEES --
    @GetMapping("/employees")
    public ModelAndView landing() {
        ModelAndView modelAndView = new ModelAndView("employees/viewEmployees");
        List<Employee> allEmployees = employeeService.getEmployees();
        List<Employee> completeEmployees = new ArrayList<>();
        List<Employee> openEmployees = new ArrayList<>();
        for (int i = 0; i < allEmployees.size(); i++) {
            Boolean isComplete = employeeService.isChecklistComplete(allEmployees.get(i));
            if (isComplete) {
                completeEmployees.add(allEmployees.get(i));
            } else {
                openEmployees.add(allEmployees.get(i));
            }
        }
        modelAndView.addObject("openEmployees", openEmployees);
        modelAndView.addObject("completeEmployees", completeEmployees);
        return modelAndView;
    }

    // -- ADD --

    @GetMapping("employees/addEmployee")
        public ModelAndView addEmployee(){
        ModelAndView modelAndView = new ModelAndView("/employees/addEmployee");
        modelAndView.addObject("employee", new Employee());

        return modelAndView;
    }

    @PostMapping("/employees/added")
    public ModelAndView addEmployee(@ModelAttribute ("employee") Employee employee) throws Exception {
        System.out.println("Received employee: " + employee);
        employeeService.saveEmployee(employee);
        ModelAndView modelAndView = new ModelAndView("redirect:/employees");
        modelAndView.addObject("message", "Successfully created");
        return modelAndView;
    }

    // -- EDIT --
    @GetMapping("/employees/{id}/update")
    public ModelAndView showEditEmployeeForm(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        ModelAndView modelAndView = new ModelAndView("/employees/editEmployee");
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    @PostMapping("/employees/update")
    public ModelAndView updateEmployee(@ModelAttribute @Valid Employee updatedEmployee) {
        employeeService.updateEmployee(updatedEmployee);
        ModelAndView modelAndView = new ModelAndView("redirect:/employees");
        modelAndView.addObject("message", "Successfully updated");
        return modelAndView;
    }



}
