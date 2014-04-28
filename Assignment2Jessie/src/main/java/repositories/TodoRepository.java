package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import entities.Member;
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
	
	public void persistTodo(Todo todo){
		em.persist(todo);
	}
	
	public Todo findById(Long id) {
		return em.find(Todo.class, id);
	}
	
	public List<Todo> findAllTodosByMember(Member member) {
    	System.out.println("looking up todos for: " + member.getName());
		String loginQueryString = "SELECT description, name FROM Todo WHERE id = ?";
		String[] result = new String[2];
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			loginStatement = connection.prepareStatement(loginQueryString);
			loginStatement.setString(1, ""+member.getId());
			loginResultSet = loginStatement.executeQuery();		
			List<Todo> todos = null;
			while (loginResultSet.next()) {
				result[0] = loginResultSet.getString(1);
				result[1] = loginResultSet.getString(2);
				Todo todo = new Todo(result[0], result[1], member);
				todos.add(todo);
			}
			return todos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
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
	
	public List<Todo> findByNameAndDescList(String name, String description) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Todo> criteria = cb.createQuery(Todo.class);
		Root<Todo> todo = criteria.from(Todo.class);
		criteria.select(todo).where(cb.equal(todo.get("name"), name)).where(cb.equal(todo.get("description"), description));
		List<Todo> todos = em.createQuery(criteria).getResultList();
		if(todos.size() > 0){
			return todos;
		}
		return null;
	}
	
	public List<Todo> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Todo> criteria = cb.createQuery(Todo.class);
		Root<Todo> todo = criteria.from(Todo.class);
		criteria.select(todo).orderBy(cb.asc(todo.get("name")));
		return em.createQuery(criteria).getResultList();
	}
}
