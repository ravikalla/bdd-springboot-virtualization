package in.ravikalla.linkcollectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import in.ravikalla.bean.Person;
import in.ravikalla.controllers.PersonController;

public class PersonResourceLnks extends ResourceSupport {

    private final Person person;
    
    public PersonResourceLnks(final Person person) {
        this.person = person;
        this.add(new Link("htgd", "person-link"));
        this.add(linkTo(PersonController.class).withRel("bookmarks"));
        this.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
    }
    
    public Person getPerson(){
        return person;
    }
}
