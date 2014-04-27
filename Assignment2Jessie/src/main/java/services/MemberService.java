package services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import registration.MemberRegistration;
import repositories.MemberRepository;
import entities.Member;

@Path("/members")
@RequestScoped
@Stateful
public class MemberService {
	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private MemberRepository repository;

	@Inject
	MemberRegistration registration;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> listAllMembers() {
		System.out.println("||||||||||||Got in listAllMembers||||||||||||||||");
		return repository.findAllOrderedByName();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Member lookupMemberById(@PathParam("id") long id) {
		System.out.println("||||||||||||Got in lookupMemberById||||||||||||||||");
		Member member = repository.findById(id);
		if (member == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return member;
	}
	
	@GET
	@Path("/login")
//	@Path("/login/{name}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public String[] lookupMemberByNameAndPass(@QueryParam("name") String name,@QueryParam("password") String password) {
//	public String[] lookupMemberByNameAndPass(@PathParam("name") String name,@PathParam("password") String password) {
		System.out.println("||||||||||||Got in lookupMemberByNameAndPass||||||||||||||||");
		return repository.getUserByNameAndPass(name,password);
	}
	
	@GET
	@Path("/register")
//	@Path("/register/{name}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean checkIfMemberExists(@QueryParam("name") String name) {
//	public Boolean checkIfMemberExists(@PathParam("name") String name) {
		System.out.println("||||||||||||Got in checkIfMemberExists||||||||||||||||");
		return repository.nameTaken(name);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMember(Member member) {
		System.out.println("||||||||||||Got in createMember||||||||||||||||");
		System.out.println("member: "+ member.getName() + ", password: " + member.getPassword());
		Response.ResponseBuilder builder = null;
		try {
			if(!repository.nameTaken(member.getName())){
				registration.register(member);
				builder = Response.ok();
			}
		} catch (ConstraintViolationException ce) {
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("name", "Username taken");
			builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
		} catch (Exception e) {
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}
		return builder.build();
	}

	private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
		log.fine("Validation completed. violations found: " + violations.size());

		Map<String, String> responseObj = new HashMap<String, String>();

		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
		}

		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}
}
