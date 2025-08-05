package uk.ac.cf.spring.Group13Project1.items.domain;

import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

public interface ItemService {

    Item getItemById(Long id);
    Item saveItem(Item id);
    void updateItem(Item item);

}
