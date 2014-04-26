package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import repositories.TodoRepository;
import entities.Todo;


@Stateless
@LocalBean
public class EntitiesEJB {

    @Inject
    private TodoRepository  todoRepository;

	public EntitiesEJB() {
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persistAllMasterTableRows(List<Todo> todos) {
		persistAllTodos(todos);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persistAllTodos(List<Todo> todos){
		int i = 0;
		for(Todo todo : todos) {
			todoRepository.mergeTodo(todo);
			i++;
		}
		System.out.println("Merged Callfailures = "+i);
	}
	
}