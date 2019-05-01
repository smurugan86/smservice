package com.madurai.sms.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.madurai.sms.util.CommonUtil;
import com.madurai.sms.util.Constants;
import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

@Repository
public class BlogDAOImpl{
	
	MongoCollection<Document> blogPostCollection;
	
	public BlogDAOImpl() {
		try{		
		 blogPostCollection = CommonUtil.getMongoDBConnection(Constants.CLS_MY_POST);
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}

	public Iterable<Document> getAllTask(String userId) {
		Iterable<Document> taskList = blogPostCollection.find(eq(Constants.USER_ID, userId));
		return taskList;
	}

	public Document saveBlog(Document blog) {
		try {			
	        blogPostCollection.insertOne(blog);
			return blog;
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("task already in use");	               
	            }
		}
		return null;
	}

	public void updateBlog(String id, Document blogDoc) {
		try {			
			blogPostCollection.updateOne(new Document(Constants._ID, id),
		        new Document("$set", blogDoc));		
		}catch (MongoWriteException e) {
            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
                System.out.println("Task id " + id);	               
            }
		}
	}

	public Document getBlogbyId(String id) {
		 Document task = null;
	        task = blogPostCollection.find(eq(Constants._ID, id)).first(); 
		return task;
	}

	public boolean deleteBlogById(String blogId) {
		blogPostCollection.deleteOne(new Document("_id", blogId));
		return true;
	}
	
}