package repositories;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import entities.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class MemberRepository {
	Connection connection = null;
	PreparedStatement loginStatement = null;
	ResultSet loginResultSet = null;
	
    @PersistenceContext(unitName="Assignment2Jessie")
    private EntityManager em;

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
        Root<Member> member = criteria.from(Member.class);
        criteria.select(member).where(cb.equal(member.get("name"), name));
        return em.createQuery(criteria).getSingleResult();
    }
    
    public Member findByNameAndPass(String name, String password) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
        Root<Member> member = criteria.from(Member.class);
        criteria.select(member).where(cb.equal(member.get("name"), name)).where(cb.equal(member.get("password"), password));
        return em.createQuery(criteria).getSingleResult();
    }
    
    public Boolean nameTaken(String name){
    	System.out.println("looking up: " + name);
		String loginQueryString = "SELECT name FROM Member WHERE name = ?";
		String result = "";
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			loginStatement = connection.prepareStatement(loginQueryString);
			loginStatement.setString(1, name);
			loginResultSet = loginStatement.executeQuery();		
			
			while (loginResultSet.next()) {
				result = loginResultSet.getString(1);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public String[] getUserByNameAndPass(String name, String password){
    	System.out.println("looking up: " + name);
		String loginQueryString = "SELECT name, password, dateJoined FROM Member WHERE name = ? and password=?";
		String[] result = new String[3];
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			loginStatement = connection.prepareStatement(loginQueryString);
			loginStatement.setString(1, name);
			loginStatement.setString(2, password);
			loginResultSet = loginStatement.executeQuery();		
			
			while (loginResultSet.next()) {
				result[0] = loginResultSet.getString(1);
				result[1] = loginResultSet.getString(2);
				result[2] = loginResultSet.getString(3);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;   	
    }
    
    public String[] getUserByName(String name){
    	System.out.println("looking up: " + name);
		String loginQueryString = "SELECT name, password, dateJoined, id FROM Member WHERE name = ?";
		String[] result = new String[4];
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			loginStatement = connection.prepareStatement(loginQueryString);
			loginStatement.setString(1, name);
			loginResultSet = loginStatement.executeQuery();		
			
			while (loginResultSet.next()) {
				result[0] = loginResultSet.getString(1);
				result[1] = loginResultSet.getString(2);
				result[2] = loginResultSet.getString(3);
				result[3] = loginResultSet.getString(4);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;   	
    }
    
    public List<Member> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
        Root<Member> member = criteria.from(Member.class);
        criteria.select(member).orderBy(cb.asc(member.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
