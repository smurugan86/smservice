package com.madurai.sms.domain;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;

import com.madurai.sms.util.Constants;

public class AccountsVO{

	private String _id;
	private String accountType;
	private String categoryName;
	private Long amount;
	private String description;
	private String userId;
	private Date date = new Date();
	private Date createdDate = new Date();
	
	public AccountsVO() {
	}
	
	public AccountsVO(HttpServletRequest request) {
		super();
		this._id = request.getParameter(Constants._ID);
		this.accountType = request.getParameter(Constants.ACCOUNT_TYPE);
		this.categoryName = request.getParameter(Constants.CATEGORY_NAME);
		this.amount = Long.parseLong(request.getParameter(Constants.AMOUNT));
		this.description = request.getParameter(Constants.DESCRIPTION);
		this.userId = request.getParameter(Constants.USER_ID);			
	}

	public Document AccountVOToDoc(AccountsVO accountVO) {			
		Document account = new Document();
		account.append(Constants._ID, UUID.randomUUID().toString());		
		account.append(Constants.ACCOUNT_TYPE, accountVO.getAccountType());
		account.append(Constants.CATEGORY_NAME, accountVO.getCategoryName());
		account.append(Constants.AMOUNT, accountVO.getAmount());
		account.append(Constants.DESCRIPTION, accountVO.getDescription());		
		account.append(Constants.USER_ID, accountVO.getUserId());		
		account.append(Constants.DATE, new Date());
		account.append(Constants.CREATED_DATE, new Date());			
		return account;
	}
	
	public Document UpdateAccountVOToDoc(AccountsVO accountVO) {			
		Document account = new Document();
		//account.append(Constants._ID, UUID.randomUUID().toString());		
		account.append(Constants.ACCOUNT_TYPE, accountVO.getAccountType());
		account.append(Constants.CATEGORY_NAME, accountVO.getCategoryName());
		account.append(Constants.AMOUNT, accountVO.getAmount());
		account.append(Constants.DESCRIPTION, accountVO.getDescription());		
		account.append(Constants.USER_ID, accountVO.getUserId());		
		account.append(Constants.DATE, new Date());
		account.append(Constants.CREATED_DATE, new Date());			
		return account;
	}
	
	public Document AccountVOToDocUpdate(AccountsVO userDoc) {	
		Document account = new Document();
		boolean isUpdate = false;	
		
		if(userDoc.getDate()!=null){
			account.append(Constants.DATE,userDoc.getDate());
			isUpdate = true;
		}		
		if(Objects.nonNull(userDoc.getAccountType())){
			account.append(Constants.ACCOUNT_TYPE, userDoc.getAccountType());
			isUpdate = true;
		}
		if(Objects.nonNull(userDoc.getCategoryName())){
			account.append(Constants.CATEGORY_NAME, userDoc.getCategoryName());
			isUpdate = true;
		}		
		if(userDoc.getAmount()!=null){
			account.append(Constants.AMOUNT, userDoc.getAmount());
			isUpdate = true;
		}
		if(Objects.nonNull(userDoc.getDescription())){
			account.append(Constants.DESCRIPTION,userDoc.getDescription());
			isUpdate = true;
		}		
		if(isUpdate){
			account.append(Constants.UPDATED_DATE, new Date());
			return account;
		}else{
			return null;
		}
	}
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}	
}