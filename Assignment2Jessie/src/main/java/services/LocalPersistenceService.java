package services;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import entities.Todo;

@Path("/persist")
@RequestScoped
@Stateful
public class LocalPersistenceService {

	@Inject
	private EntitiesEJB entEJB;
	private List<Todo> todos;

	public LocalPersistenceService() {
		super();
	}

	public void persist() {
		entEJB.persistAllTodos(todos);
	}

	public void checkInjection() {
		System.out.println("entEJB: " + entEJB);
	}

}
