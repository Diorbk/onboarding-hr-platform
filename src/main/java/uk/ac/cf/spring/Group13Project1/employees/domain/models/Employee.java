package uk.ac.cf.spring.Group13Project1.employees.domain.models;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Date;


@Data
public class Employee {

    private Long id;
    private String name;
    private String role;
    @Column(unique = true)
    private String email;
    private Date startDate;

    public Employee(){
    }

    public Employee(Long id, String name, String role, String email, Date startDate) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email=" + email + '\'' +
                ", startDate=" + startDate +
                '}';
    }

}
