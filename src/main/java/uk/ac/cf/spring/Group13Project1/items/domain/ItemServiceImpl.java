package uk.ac.cf.spring.Group13Project1.items.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.Group13Project1.items.data.ItemRepository;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Item getItemById(Long id) { return itemRepository.getItemById(id); }

    public void updateItem(Item item) { itemRepository.updateItem(item); }

    public Item saveItem(Item item) { return itemRepository.saveItem(item); }

}
