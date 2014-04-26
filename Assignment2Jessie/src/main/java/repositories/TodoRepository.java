package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import entities.Todo;

@ApplicationScoped
public class TodoRepository {
	Connection connection = null;
	PreparedStatement loginStatement = null;
	ResultSet loginResultSet = null;

	@PersistenceContext(unitName="Assignment2Jessie")
	private EntityManager em;
	
	public void mergeTodo(Todo todo){
		em.merge(todo);
	}
	
	public Todo findById(Long id) {
		return em.find(Todo.class, id);
	}

	public List<Todo> findByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Todo> criteria = cb.createQuery(Todo.class);
		Root<Todo> todo = criteria.from(Todo.class);
		criteria.select(todo).where(cb.equal(todo.get("name"), name));
		return em.createQuery(criteria).getResultList();
	}
	
	public Todo findByNameAndDesc(String name, String description) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Todo> criteria = cb.createQuery(Todo.class);
		Root<Todo> todo = criteria.from(Todo.class);
		criteria.select(todo).where(cb.equal(todo.get("name"), name)).where(cb.equal(todo.get("description"), description));
		return em.createQuery(criteria).getSingleResult();
	}
	
	public List<Todo> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Todo> criteria = cb.createQuery(Todo.class);
		Root<Todo> todo = criteria.from(Todo.class);
		criteria.select(todo).orderBy(cb.asc(todo.get("name")));
		return em.createQuery(criteria).getResultList();
	}
}
