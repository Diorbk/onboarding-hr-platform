package uk.ac.cf.spring.Group13Project1.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.cf.spring.Group13Project1.employees.domain.EmployeeService;
import uk.ac.cf.spring.Group13Project1.employees.domain.models.Employee;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
public class ViewEmployeeTests {

    @Autowired
    private MockMvc mvc;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }



    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testGetAddEmployeePage() throws Exception {

        MvcResult result = mvc
                .perform(get("/employees/addEmployee"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void updateEmployeeTest() throws Exception {
        // Mock employee data
        Long employeeId = 1L;
        Date startDate = Date.valueOf("2023-01-01");

        Employee updatedEmployee = new Employee(employeeId, "John Doe", "Manager", "john.doe@example.com", startDate);

        updatedEmployee.setName("Updated Name");

        // Perform the request and assert the response
        mvc.perform(MockMvcRequestBuilders.post("/employees/update")
                .flashAttr("updatedEmployee", updatedEmployee)
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(view().name("redirect:/employees"))
                .andExpect(model().attributeExists("message"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void addEmployeeTest() throws Exception {
        Date startDate = Date.valueOf("2023-01-01");

        mvc.perform(MockMvcRequestBuilders.post("/employees/added")
                        .param("name", "test")
                        .param("role", "test")
                        .param("email", "test@test.com")
                        .param("startDate", String.valueOf(startDate))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(view().name("redirect:/employees"))
                .andExpect(model().attributeExists("message"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testUpdateEmployeeName(){
        //Given a candidate has two references
        Date startDate = Date.valueOf("2023-01-01");
        Employee mockEmployee = new Employee(1L, "John Doe", "Manager", "john.doe@example.com", startDate);

        given(employeeService.getEmployeeById(1L)).willReturn(mockEmployee);

        //Mock database edit
        mockEmployee.setName("Peter Griffin");

        assertEquals(mockEmployee.getName(), "Peter Griffin");
        System.out.println("Employee: " + mockEmployee);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testAddEmployee() throws Exception{
        //Given a candidate has two references
        Date startDate = Date.valueOf("2023-01-01");
        Employee mockEmployee = new Employee(8L, "Peter Griffin", "Developer", "peter.griffin@test.com", startDate);

        given(employeeService.saveEmployee(mockEmployee)).willReturn(mockEmployee);

        //Mock adding item to database
        String expectedString = "Employee{id=8, name='Peter Griffin', role='Developer', email=peter.griffin@test.com', startDate=2023-01-01}";
        assertEquals(expectedString, mockEmployee.toString());
        System.out.println("Employee: " + mockEmployee);
    }


}

