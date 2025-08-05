package uk.ac.cf.spring.Group13Project1.contact.domain.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactForm {
    private Long id;
    @NotEmpty(message = "The name cannot be empty")
    private String name;
    @NotEmpty(message = "Please select a role")
    private String role;
    @NotEmpty(message = "The email cannot be empty")
    @Email
    private String email;

    private Long phone_number;

    public ContactForm() {
        this(0L,"","select_role","",0L);
    }
}
