package com.madurai.sms.domain;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.madurai.sms.util.Constants;


@JsonIgnoreProperties(ignoreUnknown = true)	
public class TaskVO{
	
	public TaskVO(){}
	private String _id;
	private String title;
	private String userStory;
	private String description;
	private Date taskDate;
	private String createdBy;
	
	public String getId() {
		return _id;
	}
	public void setId(String id) {
		this._id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserStory() {
		return userStory;
	}

	public void setUserStory(String userStory) {
		this.userStory = userStory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
		
	public Date getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}
	public Document TaskVOToDoc(TaskVO taskDoc) {			
		Document task = new Document();
		task.append(Constants._ID, UUID.randomUUID().toString());		
		task.append(Constants.TITLE, taskDoc.getTitle());
		task.append(Constants.USER_STORY, taskDoc.getUserStory());
		task.append(Constants.DESCRIPTION, taskDoc.getDescription());
		task.append(Constants.DATE, new Date());
		task.append(Constants.CREATED_DATE, new Date());
		task.append("taskDate", taskDoc.getTaskDate());
		return task;
	}
	
	public Document TaskVOToDocUpdate(TaskVO taskDoc) {		
		
		Document user = new Document();
		boolean isUpdate = false;
		
		
		if(Objects.nonNull(taskDoc.getTitle())){
			user.append(Constants.TITLE, taskDoc.getTitle());
			isUpdate = true;
		}
		if(Objects.nonNull(taskDoc.getUserStory())){
			user.append(Constants.USER_STORY, taskDoc.getUserStory());
			isUpdate = true;
		}
		
		if(Objects.nonNull(taskDoc.getDescription())){
			user.append(Constants.DESCRIPTION, taskDoc.getDescription());
			isUpdate = true;
		}
		
		if(Objects.nonNull(taskDoc.getTaskDate())){
			user.append("taskDate", taskDoc.getTaskDate());
			isUpdate = true;
		}
		
		if(isUpdate){
			user.append(Constants.UPDATED_DATE, new Date());
			return user;
		}else{
			return null;
		}
	}
}



