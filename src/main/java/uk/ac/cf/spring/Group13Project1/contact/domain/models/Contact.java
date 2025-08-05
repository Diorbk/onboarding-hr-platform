package uk.ac.cf.spring.Group13Project1.contact.domain.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contact {
    private Long id;
    private String name;
    private String role;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private Long phone_number;

    public boolean isNew() {
        return this.id == 0;
    }

    public Contact(){
    }

    public String getEmail() {
        return email;
    }

    public Long getPhoneNumber() {
        return phone_number;
    }
}
