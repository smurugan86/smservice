package com.madurai.sms.services;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.madurai.sms.dao.UserDAOImpl;
import com.madurai.sms.domain.User;


@Path("/user")
@Component
public class UserService {
	
@Autowired
UserDAOImpl userDAOImpl;

/*@Autowired
UserManager userManager;*/

  @GET
  @Path("/{_id}")
  public Response getUser(@PathParam("_id") String id) {
	  Document userD = userDAOImpl.getUserbyId(id);
	  return Response.ok(userD).build();
  }
    
  @GET
  @Path("/getAllUser")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response userLists2() {
	Iterable<Document> users = userDAOImpl.getAllUsers();
	return Response.ok(users).build();
  }
   
  @POST
  @Path("/saveUser")
  @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postCustomer(@RequestBody User user){
	  User userVO = new User();  
	  Document userD = userVO.UserVOToDoc(user);
	  Document userData = userDAOImpl.saveUser(userD);
	  return Response.ok(userData).build();
	}
  
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@RequestBody User user){
	  User userVO = new User();  
	 // Document userD = userVO.UserVOToDoc(user);
	  //Document userData = userDAOImpl.saveUser(userD);
	  boolean isValid = userDAOImpl.isValidUser(user.getEmail(), user.getPassword());
	  if(isValid){
		  Document userD = userDAOImpl.getUserbyEmail(user.getEmail());
		  return Response.ok(userD).build();
	  }else{
		  return Response.status(500).build();
	  }
	 
	}
  
  @POST
  @Path("/updateuser")
  @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateuser(@RequestBody User user){
	  User userVO = new User();  
	  String id = user.get_id();
	  Document userDoc = userVO.UserVOToDocUpdate(user);
	  userDAOImpl.updateUser(id,userDoc);
	  
	HashMap<String, Object> data = new HashMap<>();
	data.put("user", user);
	data.put("usr_tab", "active");
	
	
	  return Response.ok(data).build();
	}
  
  @GET
  @Path("/deleteuser/{_id}")
  public Response deleteUser(@PathParam("_id") String userId) {
	  boolean isDelete = userDAOImpl.deleteUserById(userId);
	  return Response.ok(isDelete).build();
  }
  
	
	public static Response populateResponse(AtomicInteger restStatusCode, String parseError, int jobId) {
		ResponseBuilder responseBuilder = Response.noContent().type(MediaType.APPLICATION_JSON);
		//ResponseMessageVO responseVO = new ResponseMessageVO();	
		
		// Set atomic integer restStatusCode to int
		int statusCode = restStatusCode.get();
		
		//responseVO.setStatusCode(statusCode);
		
		//check the error code and produce the respective response

		switch (Status.fromStatusCode(statusCode)) {
			case OK:
				//responseVO.setStatus(RestConstants.SUCCESS);
				// Added status code here because only success scenario returning the status code for failure error code will return. 
				///responseVO.setStatusCode(statusCode);
				//responseBuilder = Response.status(Status.OK).header(RestConstants.STATUS, HttpStatus.OK.value());
				responseBuilder = Response.status(Status.OK).header(RestConstants.STATUS, "OK");
				break;
			case CREATED:
				//responseVO.setStatus(RestConstants.SUCCESS);
				// Added status code here because only success scenario returning the status code for failure error code will return.
				//responseVO.setStatusCode(statusCode);
				responseBuilder = Response.status(Status.CREATED).header(RestConstants.STATUS, "CREATED");
				//responseBuilder = Response.status(Status.CREATED).header(RestConstants.STATUS, HttpStatus.CREATED.value());
				//responseVO.setData(getDataVO(jobId, RestConstants.REQUEST_PAYLOAD_CREATED));
				break;
			case BAD_REQUEST:
				//responseVO.setStatus(RestConstants.ERROR);
				//responseBuilder = Response.status(Status.BAD_REQUEST).header(RestConstants.STATUS, HttpStatus.BAD_REQUEST.value());
				responseBuilder = Response.status(Status.BAD_REQUEST).header(RestConstants.STATUS, "BAD_REQUEST");
				//responseVO.setError(getErrorVO(statusCode, parseError));
				break;
			case INTERNAL_SERVER_ERROR:
				//responseVO.setStatus(RestConstants.ERROR);
				responseBuilder = Response.status(Status.INTERNAL_SERVER_ERROR).header(RestConstants.STATUS, "INTERNAL_SERVER_ERROR");
				//responseVO.setError(getErrorVO(statusCode, RestConstants.UNEXPECTED_ERROR));
				break;
			case UNAUTHORIZED:
				//responseVO.setStatus(RestConstants.ERROR);
				responseBuilder = Response.status(Status.UNAUTHORIZED).header(RestConstants.STATUS, "UNAUTHORIZED");
				//responseVO.setError(getErrorVO(statusCode, parseError));
				break;
			case UNSUPPORTED_MEDIA_TYPE:
				//responseVO.setStatus(RestConstants.ERROR);
				responseBuilder = Response.status(Status.UNSUPPORTED_MEDIA_TYPE).header(RestConstants.STATUS, "UNSUPPORTED_MEDIA_TYPE");
				//responseVO.setError(getErrorVO(statusCode,Status.UNSUPPORTED_MEDIA_TYPE.toString()));
				break;
			default:
				//responseVO.setStatus(RestConstants.ERROR);
				responseBuilder = Response.status(Status.UNSUPPORTED_MEDIA_TYPE).header(RestConstants.STATUS, "UNSUPPORTED_MEDIA_TYPE");
				//responseVO.setError(getErrorVO(statusCode, RestConstants.UNEXPECTED_ERROR));
		}

		return responseBuilder.entity("Message").build();

	}	
	
}
