package com.madurai.sms.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.madurai.sms.util.CommonUtil;
import com.madurai.sms.util.Constants;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;


@Repository
public class NoteDAOImpl{
	
	MongoCollection<Document> noteCollection;
	
	public NoteDAOImpl() {
		try{		
		 noteCollection = CommonUtil.getMongoDBConnection(Constants.CLS_MY_NOTES);
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
	}

	public Document getNotesById(String id) {
		 Document task = null;
	        task = noteCollection.find(eq("_id", id)).first(); 
		return task;
	}


	
	public Iterable<Document> getAllNotes() {
		
	        Iterable<Document> taskList = noteCollection.find();
	        
		return taskList;
	}
	
}