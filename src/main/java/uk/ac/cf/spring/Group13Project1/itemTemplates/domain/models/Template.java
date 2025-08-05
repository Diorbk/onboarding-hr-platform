package uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Template {

    Long templateId;
    String templateName;

    public Template() {}

}
