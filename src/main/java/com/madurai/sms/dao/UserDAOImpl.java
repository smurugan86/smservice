package com.madurai.sms.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.Date;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.madurai.sms.util.CommonUtil;
import com.madurai.sms.util.Constants;
import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;


@Repository
public class UserDAOImpl{
	
	MongoCollection<Document> userCollection;
	
	public UserDAOImpl() {
		try{		
		 userCollection = CommonUtil.getMongoDBConnection(Constants.CLS_USERS);
		}catch (MongoWriteException e) {
			System.out.println(e.getError());            
		}
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
	
	
	public Document getNotesById(String id) {
		 Document task = null;
	        task = userCollection.find(eq("_id", id)).first(); 
		return task;
	}
	
	public Iterable<Document> getAllUsers() {
	  Iterable<Document> userList = userCollection.find();
	  return userList;
	}

	public Document getUserbyId(String id) {
		 Document user = null;
	        user = userCollection.find(eq(Constants._ID, id)).first(); 
		return user;
	}

	public void updateUser(String userId, Document userDoc) {
		try {			
			userCollection.updateOne(new Document(Constants._ID, userId),
		        new Document("$set", userDoc));		
		}catch (MongoWriteException e) {
            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
                System.out.println("userId " + userId);	               
            }
		}
	}
	
	public boolean deleteUserById(String userId) {
		userCollection.deleteOne(new Document("_id", userId));
		return true;
	}

	public boolean isValidUser(String email, String password) {
		 Document user = null;
	        user = userCollection.find(eq(Constants.EMAIL, email)).first();   
	        
	        if(null!=user){
		        String hashedAndSalted = user.get(Constants.PASSWORD).toString();
		        String salt = hashedAndSalted.split(",")[1];
		        //if (hashedAndSalted.equals(CommonUtil.makePasswordHash(password, salt))) {
		        	
		        if (password.equals(user.get("textPassword").toString())) { 
		        	userCollection.updateOne(new Document(Constants._ID, user.get(Constants._ID).toString()),
		    		        new Document("$set", new Document(Constants.LAST_LOGIN_DATE, new Date())));
		            return true;
		        }
	        }
	        
	        System.out.println("Submitted password is not a match");
	        return false;
	}

	public Document getUserbyEmail(String email) {
		// TODO Auto-generated method stub
		return userCollection.find(eq(Constants.EMAIL, email)).first();
	}
	
}