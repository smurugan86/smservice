package com.madurai.sms.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.madurai.sms.util.CommonUtil;
import com.madurai.sms.util.Constants;
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
	
}