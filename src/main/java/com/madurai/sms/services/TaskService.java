package com.madurai.sms.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.madurai.sms.dao.TaskDAOImpl;


@Path("/task")
@Component
public class TaskService {
	
@Autowired
TaskDAOImpl taskDAOImpl;
     
  @GET
  @Path("/list")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response taskList() {
	Iterable<Document> aa = taskDAOImpl.getAllTask();
	return Response.ok(aa).build();
  }
 
	
}
