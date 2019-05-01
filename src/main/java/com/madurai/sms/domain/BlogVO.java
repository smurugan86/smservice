package com.madurai.sms.domain;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.madurai.sms.util.Constants;


/**
 * @author sys-blog
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)	
public class BlogVO{
	
	public BlogVO(){}
	private String _id;
	private String title;
	private String description;
	private Date blogDate;
	private String createdBy;
	private String userId;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		
	public Date getBlogDate() {
		return blogDate;
	}

	public void setBlogDate(Date blogDate) {
		this.blogDate = blogDate;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Document BlogVOToDoc(BlogVO blogDoc) {			
		Document blog = new Document();
		blog.append(Constants._ID, UUID.randomUUID().toString());		
		blog.append(Constants.TITLE, blogDoc.getTitle());
		blog.append(Constants.DESCRIPTION, blogDoc.getDescription());
		blog.append(Constants.USER_ID, blogDoc.getUserId());
		blog.append(Constants.DATE, new Date());
		blog.append(Constants.CREATED_DATE, new Date());
		blog.append("blogDate", blogDoc.getBlogDate());
		return blog;
	}
	
	public Document BlogVOToDocUpdate(BlogVO blogDoc) {		
		
		Document blog = new Document();
		boolean isUpdate = false;
		
		if(Objects.nonNull(blogDoc.getTitle())){
			blog.append(Constants.TITLE, blogDoc.getTitle());
			isUpdate = true;
		}
		
		if(Objects.nonNull(blogDoc.getDescription())){
			blog.append(Constants.DESCRIPTION, blogDoc.getDescription());
			isUpdate = true;
		}
		
		if(Objects.nonNull(blogDoc.getBlogDate())){
			blog.append("blogDate", blogDoc.getBlogDate());
			isUpdate = true;
		}
		
		if(isUpdate){
			blog.append(Constants.UPDATED_DATE, new Date());
			return blog;
		}else{
			return null;
		}
	}
}



