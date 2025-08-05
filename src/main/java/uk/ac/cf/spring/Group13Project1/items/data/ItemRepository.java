package uk.ac.cf.spring.Group13Project1.items.data;

import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

import java.util.List;

public interface ItemRepository {

    Item getItemById(Long id);
    List<Item> getItems();

    List<Item> getItemsByEmployee(String employeeId);

    Item saveItem(Item item);
    void updateItem(Item item);
    void deleteItemById(Long id);
    void deleteItemsByOwner(String ownerId);

}
