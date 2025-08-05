package uk.ac.cf.spring.Group13Project1.items.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private JdbcTemplate jdbc;
    private RowMapper<Item> itemRowMapper;

    public ItemRepositoryImpl(JdbcTemplate aJdbc) {
        this.jdbc = aJdbc;
        setItemRowMapper();
    }

    private void setItemRowMapper() {
        itemRowMapper = (rs, i) -> new Item(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("due_date"),
                rs.getBoolean("is_complete"),
                rs.getString("owner_id"),
                rs.getLong("contacts_id")
        );
    }

    public Item getItemById(Long id) {
        String sql = "select * from items where id = ?";
        return jdbc.queryForObject(sql, itemRowMapper, id);
    }

    public List<Item> getItems() {
        String sql = "SELECT * FROM items";
        return jdbc.query(sql, itemRowMapper);
    }

    public List<Item> getItemsByEmployee(String employeeId) {
        String sql = "SELECT * FROM items WHERE owner_id = ?";
        return jdbc.query(sql, itemRowMapper, employeeId);
    }

    public Item saveItem(Item item) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("items")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", item.getTitle());
        parameters.put("description", item.getDescription());
        parameters.put("due_date", item.getDueDate());
        parameters.put("is_complete", item.getIsComplete());
        parameters.put("owner_id", item.getOwnerId());
        parameters.put("contacts_id", item.getContactsId());

        Number newItemId = jdbcInsert.executeAndReturnKey(parameters);
        item.setId(newItemId.longValue());
        return item;
    }
    public void updateItem(Item item) {
        String sql = "update items set title = ?, description = ?, due_date = ?, contacts_id = ?, is_complete = ?, owner_id = ? where id = ?";
        System.out.println("Executing SQL query: " + sql);
        try {
            jdbc.update(sql,
                    item.getTitle(),
                    item.getDescription(),
                    item.getDueDate(),
                    item.getContactsId(),
                    item.getIsComplete(),
                    item.getOwnerId(),
                    item.getId()
            );
        } catch (Exception e) {
            System.err.println("Error updating item with ID: " + item.getId());
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteItemById(Long id) {
        String sql = "DELETE FROM items WHERE id=?";
        jdbc.update(sql, id);
    }

    public void deleteItemsByOwner(String ownerId) {
        String sql = "DELETE FROM items WHERE owner_id=?";
        jdbc.update(sql, ownerId);
    }

}
