package com.madurai.sms.services;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.GET;
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

import com.madurai.sms.dao.NoteDAOImpl;


@Path("/user")
@Component
public class UserService {

	
@Autowired
NoteDAOImpl noteDAOImpl;


/*public NoteDAOImpl getNoteDAOImpl() {
	return noteDAOImpl;
}

public void setNoteDAOImpl(NoteDAOImpl noteDAOImpl) {
	this.noteDAOImpl = noteDAOImpl;
}*/

@GET
  @Path("/{name}")
  public String sayHello(@PathParam("name") String name) {
    String output = "Hi from Jersey REST: " + name;
    return output;
  }
    
  @GET
  @Path("/list")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response userLists2() {
	Iterable<Document> aa = noteDAOImpl.getAllNotes();
	return Response.ok(aa).build();
  }
   
  /*@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("senders")
	public Response processPriceCatalogLocationDocument(	
			@RequestBody String payload, @Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest)
			{
		StringBuilder parseError = new StringBuilder();
		StringBuilder flowId = new StringBuilder();
		AtomicInteger statusCode = new AtomicInteger(Status.OK.getStatusCode());
		int jobId = 0;
		Response response = populateResponse(statusCode, parseError.toString(), jobId);
		return response;
	}*/
	
	
	
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
