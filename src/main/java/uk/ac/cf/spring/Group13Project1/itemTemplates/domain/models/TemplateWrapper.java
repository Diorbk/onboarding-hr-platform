package uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models;

import lombok.Getter;
import lombok.Setter;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;
import uk.ac.cf.spring.Group13Project1.items.domain.models.Item;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TemplateWrapper {

    public TemplateWrapper() {}

    private Template template;
    private List<Item> itemList = new ArrayList<>();

}
