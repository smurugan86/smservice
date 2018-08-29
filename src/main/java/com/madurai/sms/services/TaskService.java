package com.madurai.sms.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.madurai.sms.dao.TaskDAOImpl;
import com.madurai.sms.domain.TaskVO;


@Path("/task")
@Component
public class TaskService {
	
@Autowired
TaskDAOImpl taskDAOImpl;
//private Object taskDAOImpl;
     
  @GET
  @Path("/list")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response taskList() {
	Iterable<Document> aa = taskDAOImpl.getAllTask();
	return Response.ok(aa).build();
  }
 
  @POST
  @Path("/saveTask")
  @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveTask(@RequestBody TaskVO taskDoc){
	  TaskVO taskVO = new TaskVO();  
	  Document task = taskVO.TaskVOToDoc(taskDoc);
	  Document taskData = taskDAOImpl.saveTask(task);
	  return Response.ok(taskData).build();
	}
  
  @POST
  @Path("/updateTask")
  @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTask(@RequestBody TaskVO task){
	  TaskVO taskVO = new TaskVO();  
	  String id = task.getId();
	  Document taskDoc = taskVO.TaskVOToDocUpdate(task);
	  taskDAOImpl.updateTask(id,taskDoc);
	  return Response.ok(task).build();
	}
  
  @GET
  @Path("/{_id}")
  public Response getTask(@PathParam("_id") String id) {
	  Document taskDoc = taskDAOImpl.getTaskbyId(id);
	  return Response.ok(taskDoc).build();
  }
  
  @GET
  @Path("/deleteTask/{_id}")
  public Response deleteTask(@PathParam("_id") String taskId) {
	  boolean isDelete = taskDAOImpl.deleteTaskById(taskId);
	  return Response.ok(isDelete).build();
  }
  
}
