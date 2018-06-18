package com.madurai.sms.util;

/**
 * Constants.
 *
 * @author Murugan Srinivasan
 *  */
public final class Constants {

   
	private Constants() { }
    
	public static final String DB_URL = "mongodb://localhost";
    public static final String DB_NAME = "murugan";
    
    public static final String CLS_USERS = "users";
    public static final String CLS_MY_ACC = "my_account";
    public static final String CLS_CATEGORY = "category";
    public static final String CLS_MY_NOTES = "notes";
    public static final String CLS_MY_POST = "my_post";
    public static final String CLS_MY_TASK = "my_task";    
    public static final String CLS_RATE = "rate";    
    public static final String CLS_SEARCH_REPORT = "search_report";
    
    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATED_DATE = "updatedDate";
    public static final String CREATED_BY = "createdBy";
    public static final String UPDATED_BY = "updatedBy";
        
    public static final String USER_NAME = "username";    
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String TEXT_PASSWORD = "textPassword";        
    public static final String TEXT_VERIFY_PASSWORD = "verify";    
    public static final String STATUS = "status";
    public static final String USER_ROLE = "userRole";    
    public static final String LAST_LOGIN_DATE = "lastLoginDate";
        
    public static final String ROLE_USER ="USER";
    public static final String ROLE_ADMIN ="ADMIN";    
    public static final String ACT ="ACT";
        
    public static final String ACCOUNT_TYPE = "accountType";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String AMOUNT = "amount";
    public static final String DESCRIPTION = "description";
    public static final String USER_ID = "userId";
	    
    public static final String TITLE = "title";
    public static final String USER_STORY = "userStory";
   // public static final String CREATED_BY = "createdBy";
    
    public static final String AUTHOR = "author";
    public static final String POST = "post";
        
    public static final String QUANTITY = "quantity";
    public static final String PRODUCT_NAME = "productName";
    
    //Error Constants
    public static final String USER_PASS_REQUIRED = "username / password required";
    public static final String LOGIN_ERROR = "login_error";
        
    public static final String BILL_Id = "billId";
    public static final String BILL_NAME = "billName";
    public static final String BILL_AMOUNT = "billAmount";
    public static final String RECEIVED_AMOUNT = "receivedAmount";
    public static final String BALANCE_AMOUNT = "balanceAmount";
        
    public static final String BILL_QNY = "billQuantity";
	public static final String TAGS = "tags";
	public static final String BODY = "body";
	public static final String COMMENTS = "comments";
	public static final String GTE = "$gte";
	public static final String LTE = "$lte";
	public static final String REGEX = "$regex";
	public static final String OPTIONS = "$options";
    
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";

}