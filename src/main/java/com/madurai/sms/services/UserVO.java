package com.madurai.sms.services;

import org.bson.Document;

public class UserVO{
	
	public int userId;
	public int id;
	public String title;
	public String body;
	
	
	public Document user;
	
	
	 
	
	public Document getUser() {
		return user;
	}
	public void setUser(Document user) {
		this.user = user;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
	
	
}



