package uk.ac.cf.spring.Group13Project1.itemTemplates.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.Group13Project1.itemTemplates.domain.models.Template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TemplatesRepositoryImpl implements TemplatesRepository {

    private JdbcTemplate jdbc;
    private RowMapper<Template> templateRowMapper;

    public TemplatesRepositoryImpl(JdbcTemplate aJdbc) {
        this.jdbc = aJdbc;
        setEmployeeRowMapper();
    }

    private void setEmployeeRowMapper() {
        templateRowMapper = (rs, i) -> new Template(
                rs.getLong("id"),
                rs.getString("name")
        );
    }

    public Template saveTemplate(Template template) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("templates")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", template.getTemplateName());
        Number newItemId = jdbcInsert.executeAndReturnKey(parameters);
        template.setTemplateId(newItemId.longValue());
        return template;
    }

    public Template getTemplateById(String id) {
        String sql = "select * from templates where id = ?";
        return jdbc.queryForObject(sql, templateRowMapper, id);
    }

    public Template updateTemplate(Template template) {
        String sql = "update templates set name = ? where id = ?";
        try {
            jdbc.update(sql,
                    template.getTemplateName(),
                    template.getTemplateId()
            );
            return template;
        } catch (Exception e) {
            System.err.println("Error updating employee with ID: " + template.getTemplateId());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Template> getTemplates() {
        String sql = "SELECT * FROM templates";
        return jdbc.query(sql, templateRowMapper);
    }

    public void deleteTemplateById(Long templateId) {
        String sql = "DELETE FROM templates WHERE id=?";
        jdbc.update(sql, templateId);
    }


}
