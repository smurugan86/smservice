package com.madurai.sms.domain;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.madurai.sms.util.CommonUtil;
import com.madurai.sms.util.Constants;


@JsonIgnoreProperties(ignoreUnknown = true)	
public class User{
	
	public User(){}
	private String _id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private String password;
	private String textPassword;
	private String status = Constants.ACT;
	private String userRole = Constants.ROLE_USER;
	
	public String getId() {
		return _id;
	}
	public void setId(String id) {
		this._id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTextPassword() {
		return textPassword;
	}
	public void setTextPassword(String textPassword) {
		this.textPassword = textPassword;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public Document UserVOToDoc(User userDoc) {			
		Document user = new Document();
		user.append(Constants._ID, UUID.randomUUID().toString());		
		user.append(Constants.FIRST_NAME, userDoc.getFirstName());
		user.append(Constants.LAST_NAME, userDoc.getLastName());
		user.append(Constants.EMAIL, userDoc.getEmail());
		user.append(Constants.PASSWORD, CommonUtil.makePasswordHash(userDoc.getPassword(), Integer.toString(CommonUtil.getRandom().nextInt())));		
		user.append(Constants.TEXT_PASSWORD, userDoc.getTextPassword());
		user.append(Constants.STATUS, Constants.ACT);
		user.append(Constants.USER_ROLE, Constants.ROLE_USER);
		user.append(Constants.DATE, new Date());
		user.append(Constants.CREATED_DATE, new Date());
		user.append(Constants.LAST_LOGIN_DATE, new Date());
		
		return user;
	}
	
	public Document UserVOToDocUpdate(User userDoc) {		
		
		Document user = new Document();
		boolean isUpdate = false;
		
		
		if(Objects.nonNull(userDoc.getFirstName())){
			user.append(Constants.FIRST_NAME, userDoc.getFirstName());
			isUpdate = true;
		}
		if(Objects.nonNull(userDoc.getLastName())){
			user.append(Constants.LAST_NAME, userDoc.getLastName());
			isUpdate = true;
		}
		
		/*if(Objects.nonNull(userDoc.getPassword())){
			user.append(Constants.PASSWORD, CommonUtil.makePasswordHash(userDoc.getPassword(), Integer.toString(CommonUtil.getRandom().nextInt())));
			user.append(Constants.TEXT_PASSWORD,userDoc.getPassword());
			isUpdate = true;
		}*/
		if(Objects.nonNull(userDoc.getUserRole())){
			user.append(Constants.USER_ROLE, userDoc.getUserRole());
			isUpdate = true;
		}
		/*if(Objects.nonNull(userDoc.getStatus())){
			user.append(Constants.STATUS,userDoc.getStatus());
			isUpdate = true;
		}*/
		
		if(isUpdate){
			user.append(Constants.UPDATED_DATE, new Date());
			return user;
		}else{
			return null;
		}
	}
}



