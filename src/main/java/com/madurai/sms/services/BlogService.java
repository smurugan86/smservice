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

import com.madurai.sms.dao.BlogDAOImpl;
import com.madurai.sms.domain.BlogVO;
import com.madurai.sms.domain.TaskVO;


@Path("/blog")
@Component
public class BlogService {
	
@Autowired
BlogDAOImpl blogDAOImpl;
     
  @GET
  @Path("/list/{userId}")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response taskList(@PathParam("userId") String userId) {
	  System.out.println("userId ================ > "+userId);
	Iterable<Document> aa = blogDAOImpl.getAllTask(userId);
	return Response.ok(aa).build();
  }
 
  @POST
  @Path("/saveBlog")
  @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveBlog(@RequestBody BlogVO blogDoc){
	  BlogVO blogVO = new BlogVO();  
	  Document blog = blogVO.BlogVOToDoc(blogDoc);
	  Document blogData = blogDAOImpl.saveBlog(blog);
	  return Response.ok(blogData).build();
	}
  
  @POST
  @Path("/updateTask")
  @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTask(@RequestBody BlogVO blog){
	  BlogVO blogVO = new BlogVO();  
	  String id = blog.get_id();
	  Document blogDoc = blogVO.BlogVOToDocUpdate(blog);
	  blogDAOImpl.updateBlog(id,blogDoc);
	  return Response.ok(blog).build();
	}
  
  @GET
  @Path("/{_id}")
  public Response getTask(@PathParam("_id") String id) {
	  Document taskDoc = blogDAOImpl.getBlogbyId(id);
	  return Response.ok(taskDoc).build();
  }
  
  @GET
  @Path("/deleteTask/{_id}")
  public Response deleteTask(@PathParam("_id") String blogId) {
	  boolean isDelete = blogDAOImpl.deleteBlogById(blogId);
	  return Response.ok(isDelete).build();
  }
  
}
