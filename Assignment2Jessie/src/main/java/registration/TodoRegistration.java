package registration;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entities.Todo;

import java.util.logging.Logger;

@Stateless
public class TodoRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Todo> todoEventSrc;

    public void register(Todo todo) throws Exception {
        log.info("Persisting " + todo.getName() + ", " + todo.getDescription());
        em.persist(todo);
        todoEventSrc.fire(todo);
    }
}
