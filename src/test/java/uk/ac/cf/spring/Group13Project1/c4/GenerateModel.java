package uk.ac.cf.spring.Group13Project1.c4;

import com.structurizr.Workspace;
import com.structurizr.analysis.ComponentFinder;
import com.structurizr.analysis.ReferencedTypesSupportingTypesStrategy;
import com.structurizr.analysis.SpringComponentFinderStrategy;
import com.structurizr.analysis.StructurizrAnnotationsComponentFinderStrategy;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.*;
import com.structurizr.view.*;
import org.junit.jupiter.api.Test;


public class GenerateModel {
    private final static int WORKSPACE_ID = 89054;
    private final static String API_KEY = "b4d36841-f502-4c0c-994e-45e73715ec44";
    private final static String API_SECRET = "e6074d74-f004-4715-b6df-a824abd1f526";


    @Test
    public void generateModel() throws Exception {
        //set up workspace and model
        // these are the core objects of our
        Workspace workspace = new Workspace("Group Project", "Bipsync Client project");
        Model model = workspace.getModel();

        // create the basic model (the stuff we can't get from the code)
        SoftwareSystem groupProject = model.addSoftwareSystem("Bipsync",
                "Checklist tool");
        Person owner = model.addPerson("Heather", " Admin with checklist ");

        owner.uses(groupProject, "Uses");

        Container webApplication = groupProject.addContainer(
                "Spring Boot Application", "The web application", "Embedded web" +
                        "container. Tomcat 7.0");
        Container relationalDatabase = groupProject.addContainer(
                "Relational Database", "Stores information regarding the" +
                        "products.", "MySQL");
        owner.uses(webApplication, "Uses", "HTTPS");

        webApplication.uses(relationalDatabase, "Reads from and writes to","JDBC, port 3306");

//         and now automatically find all Spring
//         @Controller, @Component,@Service and @Repository components
        ComponentFinder componentFinder= new ComponentFinder(
                webApplication,
                "uk.ac.cf.spring.Group13Project1",//CHANGE THIS TO YOUR PACKAGE
                new SpringComponentFinderStrategy(
                        new ReferencedTypesSupportingTypesStrategy()
                ));
        componentFinder.findComponents();

        ComponentFinder componentFinderByAnnotation = new ComponentFinder(
                webApplication,"uk.ac.cf.spring.Group13Project1",//CHANGE THIS TO YOUR PACKAGE
                new StructurizrAnnotationsComponentFinderStrategy()
        );
        componentFinderByAnnotation.findComponents();

        webApplication.getComponents().stream()
                .filter(c ->
                        c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY
                        ))
                .forEach(c -> c.uses(relationalDatabase, "Reads from and writes to", "JDBC"));

        ViewSet viewSet = workspace.getViews();
        SystemContextView contextView =
                viewSet.createSystemContextView(groupProject, "context", "The System" +
                        "Context diagram for the Group Project.");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();
        contextView.enableAutomaticLayout();

        ContainerView containerView = viewSet.createContainerView(groupProject,
                "containers", "The Containers diagram for the Group Project.");
        containerView.addAllPeople();
        containerView.addAllSoftwareSystems();
        containerView.addAllContainers();
        containerView.enableAutomaticLayout();

        ComponentView componentView =
                viewSet.createComponentView(webApplication, "components", "The" +
                        "Components diagram for Group Project.");
        componentView.addAllComponents();
        componentView.addAllPeople();
        componentView.add(relationalDatabase);
        componentView.enableAutomaticLayout();

        groupProject.addTags("Group Project 2023");
        webApplication.getComponents().stream().filter(c ->
                        c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
                .forEach(c -> c.addTags("Spring MVC Controller"));
        webApplication.getComponents().stream().filter(c ->
                        c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
                .forEach(c -> c.addTags("Spring REST Controller"));

        webApplication.getComponents().stream().filter(c ->
                        c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_SERVICE))
                .forEach(c -> c.addTags("Spring Service"));
        webApplication.getComponents().stream().filter(c ->
                        c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY))
                .forEach(c -> c.addTags("Spring Repository"));
        relationalDatabase.addTags("Database");

        Styles styles = viewSet.getConfiguration().getStyles();
        styles.addElementStyle("Group Project 2023").background("#6CB33E").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#519823").color("#ffffff").shape(Shape.Person);
        styles.addElementStyle(Tags.CONTAINER).background("#91D366").color("#ffffff");
        styles.addElementStyle("Database").shape(Shape.Cylinder);
        styles.addElementStyle("Spring RESTController").background("#D4FFC0").color("#000000");
        styles.addElementStyle("Spring MVCController").background("#D4F3C0").color("#000000");
        styles.addElementStyle("SpringService").background("#6CB33E").color("#000000");
        styles.addElementStyle("SpringRepository").background("#95D46C").color("#000000");

        StructurizrClient structurizrClient = new StructurizrClient(API_KEY,
                API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }
}
