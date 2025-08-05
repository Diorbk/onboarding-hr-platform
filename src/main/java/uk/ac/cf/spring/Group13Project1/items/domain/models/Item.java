package uk.ac.cf.spring.Group13Project1.items.domain.models;

import lombok.Data;

import java.sql.Date;

@Data
public class Item {

    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private Boolean isComplete;
    private String ownerId;
    private Long contactsId;

    public Item() {}

    public Item(Long id, String title, String description, Date dueDate, Boolean isComplete, String ownerId, Long contactsId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
        this.ownerId = ownerId;
        this.contactsId = contactsId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", isComplete=" + isComplete +
                ", ownerId='" + ownerId + '\'' +
                ", contactsId=" + contactsId +
                '}';
    }
}
