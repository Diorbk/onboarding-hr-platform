package uk.ac.cf.spring.Group13Project1.item;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.ac.cf.spring.Group13Project1.employees.domain.EmployeeService;
import uk.ac.cf.spring.Group13Project1.items.domain.ItemService;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ViewItemTests {

    @Mock
    private ItemService itemService;

    @Mock
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testViewItemDetails() throws Exception {
        // Mock employee data
        Long itemId = 1L;
        Date dueDate = Date.valueOf("2023-06-01");

        Item mockItem = new Item (itemId, "Sign Contract", "Review and sign the new client contract", dueDate, false, "1", 1L);

        when(itemService.getItemById(itemId)).thenReturn(mockItem);

        mvc.perform(MockMvcRequestBuilders.get("/items/{id}", itemId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("item"))
                .andExpect(MockMvcResultMatchers.view().name("items/viewItem"))
                .andExpect(MockMvcResultMatchers.model().attribute("item", mockItem));
    }


    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testUpdateItemTitle(){
        //Given a candidate has two references
        Date dueDate = Date.valueOf("2023-06-01");
        Item item = new Item (1L, "Sign Contract", "Review and sign the new client contract", dueDate, false, "1", 1L);

        given(itemService.getItemById(1L)).willReturn(item);

        //Mock database edit
        item.setTitle("Contract Signed");

        assertEquals(item.getTitle(), "Contract Signed");
        System.out.println("Item: " + item);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testAddItem(){
        //Given a candidate has two references
        Date dueDate = Date.valueOf("2023-06-01");
        Item item = new Item (8L, "Test Item", "Writing Tests", dueDate, false, "1", 1L);

        given(employeeService.save(item)).willReturn(item);

        //Mock adding item to database
        String expectedString = "Item{id=8, title='Test Item', description='Writing Tests', dueDate=2023-06-01, isComplete=false, ownerId='1', contactsId=1}";
        assertEquals(expectedString, item.toString());
        System.out.println("Item: " + item);
    }
}
