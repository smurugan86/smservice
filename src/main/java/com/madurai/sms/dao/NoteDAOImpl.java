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
public class NoteDAOImpl{
	
	MongoCollection<Document> userCollection;
	
	public NoteDAOImpl() {
		try{		
		 userCollection = CommonUtil.getMongoDBConnection(Constants.CLS_USERS);
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}

	public Document getNotesById(String id) {
		 Document task = null;
	        task = userCollection.find(eq("_id", id)).first(); 
		return task;
	}


	
	public Iterable<Document> getAllNotes() {
		
	        Iterable<Document> taskList = userCollection.find();
	        
		return taskList;
	}

	public Document saveUser(Document user) {
		try {			
			Document checkUser = userCollection.find(eq(Constants.EMAIL, user.get(Constants.EMAIL).toString())).first();
	        if(checkUser==null){
	        	userCollection.insertOne(user);
	        }
			return user;
		}catch (MongoWriteException e) {
	            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
	                System.out.println("Username already in use");	               
	            }
		}
		return null;
	}
	
}