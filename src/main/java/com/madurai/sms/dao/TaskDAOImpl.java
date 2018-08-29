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
public class TaskDAOImpl{
	
	MongoCollection<Document> taskCollection;
	
	public TaskDAOImpl() {
		try{		
		 taskCollection = CommonUtil.getMongoDBConnection(Constants.CLS_MY_TASK);
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}

	public Document getNotesById(String id) {
		 Document task = null;
	        task = taskCollection.find(eq("_id", id)).first(); 
		return task;
	}
	
	public Iterable<Document> getAllTask() {
		Iterable<Document> taskList = taskCollection.find();
		return taskList;
	}

	public Document saveTask(Document task) {
		try {			
	        taskCollection.insertOne(task);
			return task;
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("task already in use");	               
	            }
		}
		return null;
	}

	public void updateTask(String id, Document taskDoc) {
		try {			
			taskCollection.updateOne(new Document(Constants._ID, id),
		        new Document("$set", taskDoc));		
		}catch (MongoWriteException e) {
            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
                System.out.println("Task id " + id);	               
            }
		}
	}

	public Document getTaskbyId(String id) {
		 Document task = null;
	        task = taskCollection.find(eq(Constants._ID, id)).first(); 
		return task;
	}

	public boolean deleteTaskById(String taskId) {
		taskCollection.deleteOne(new Document("_id", taskId));
		return true;
	}
	
}