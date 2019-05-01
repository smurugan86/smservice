package com.madurai.sms.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.madurai.sms.domain.AccountsVO;
import com.madurai.sms.manager.AccountManager;
import com.madurai.sms.util.CommonUtil;
import com.madurai.sms.util.Constants;
	
@Path("/account")
@Component
public class AccountService {
	
	@Autowired
	AccountManager accountManager;

	 @POST
	  @Path("/saveAccount")
	  @Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response saveAccount(@RequestBody AccountsVO accountvo){
		  AccountsVO acc = new AccountsVO();  
		  Document accDoc = acc.AccountVOToDoc(accountvo);
		 Document accData = accountManager.saveAccount(accDoc);
		  return Response.ok(accData).build();
		}	
	
	 @GET
	  @Path("/{_id}")
	  public Response getAccount(@PathParam("_id") String id) {
		  Document accountDoc = accountManager.getAccountById(id);
		  return Response.ok(accountDoc).build();
	  }
	
	 
	 @POST
	    @Path("/updateAccount")
	    @Consumes(MediaType.APPLICATION_JSON)
	  	@Produces(MediaType.APPLICATION_JSON)
	  	public Response updateAccount(@RequestBody AccountsVO acc){
		 AccountsVO accountsVO = new AccountsVO();  
	  	  String id = acc.get_id();
	  	  Document accountsDoc = accountsVO.UpdateAccountVOToDoc(acc);
	  	  accountManager.updateAccount(id,accountsDoc);
	  	  return Response.ok(acc).build();
	  	}
	    
	    @GET
	    @Path("/deleteTask/{_id}")
	    public Response deleteTask(@PathParam("_id") String taskId) {
	  	  boolean isDelete = accountManager.deleteAccountById(taskId);
	  	  return Response.ok(isDelete).build();
	    }
	    
	 
	@RequestMapping("/addAccount")
    public String addAccount(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {         
        model.addAttribute("date", "");
        model.addAttribute("title", "");
        model.addAttribute("userStory", "");
        model.addAttribute("description", "");   
        
        String userId = CommonUtil.getSessionCookie(request,"_id");
        List<Document> aclist = accountManager.findAllCategory(userId);
        model.addAttribute("lists", aclist);        
        return "account/addaccount";         
    }
    
    @RequestMapping("/saveAccount2")
    public String saveAccount2(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String date = request.getParameter("date");
         String accountType = request.getParameter("accountType");
         String categoryName = request.getParameter("categoryName");
         String description = request.getParameter("description");
         long amount = 0;
         if(Objects.nonNull(request.getParameter("amount"))){
        	 amount = Long.parseLong(request.getParameter("amount"));
         }
         
         AccountsVO accountsVO = new AccountsVO(request);
         
         boolean isCheck = CommonUtil.accountValidation(model,date,accountType,categoryName,description,amount);
         
         if(isCheck){
        	 Document accountsDoc = accountsVO.AccountVOToDoc(accountsVO);
        	 
        	 String userId = CommonUtil.getSessionCookie(request,"_id");
        	 String taskId = accountManager.saveAccount(date,accountType,categoryName, description,amount,userId);        	 
        	
        		try {
					reponses.sendRedirect("accountlist");
				} catch (IOException e) {					
					e.printStackTrace();
				}
        		 
        		/* List<Document> aclist = accountManager.findAllAccount("","","","",0, userId);
                 model.addAttribute("lists", aclist);*/
                 return "account/accountlist";
              
         }else{
        	 model.addAttribute("date", date);
 	        model.addAttribute("accountType", accountType);
 	        model.addAttribute("categoryName", categoryName);
 	        model.addAttribute("description", description);      
 	        model.addAttribute("amount", amount);
        	 return "account/addaccount";	
         }
              
    }
    
    @GET
    @Path("/list/{userId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response accountList(@PathParam("userId") String userId) {
    	List<Document> aclist = accountManager.findAllAccountByUserId(userId);
    	return Response.ok(aclist).build();
	}
    
    @GET
	@Path("/getTotalAccount/{userId}")
	public Response getTotalAccount(@PathParam("userId") String userId) {
		  Document accountDoc = accountManager.getTotalAccount(userId);
		  return Response.ok(accountDoc).build();
	  }
    
    @RequestMapping("/accountlist")
    public String accountlist(HttpServletRequest request,
            HttpServletResponse reponses,Model model) {  
    	
    	 String date = request.getParameter("date");
    	 String endDate = request.getParameter("endDate");
         String accountType = request.getParameter("accountType"); 
         String categoryName = request.getParameter("categoryName");
         String description = request.getParameter("description");
         String Search = request.getParameter("Search");
         
         System.out.println("Search ===================== > "+Search);
         long amount = 0;
         if(Objects.nonNull(request.getParameter("amount"))){
        	 amount = Long.parseLong(request.getParameter("amount"));
         }
    	 String userId="123";// = CommonUtil.getSessionCookie(request,"_id");
    	    	 
    	 String screenName = "Account";
    	 Document searchFeilds = accountManager.getMySearchFeilds(userId,screenName);
    	 if(searchFeilds==null){
    		    searchFeilds = new Document();
    		    searchFeilds.append(Constants._ID, UUID.randomUUID().toString());    		    
    		    searchFeilds.append("screenName", "Account");
    		    searchFeilds.append(Constants.ACCOUNT_TYPE, accountType);
    		    searchFeilds.append(Constants.CATEGORY_NAME, categoryName);
    		    searchFeilds.append(Constants.AMOUNT, request.getParameter("amount"));
    		    searchFeilds.append(Constants.DESCRIPTION, description);		
    		    searchFeilds.append(Constants.USER_ID, userId);		
    		    searchFeilds.append(Constants.START_DATE, date);
    		    searchFeilds.append(Constants.END_DATE, endDate);
    		    searchFeilds.append(Constants.CREATED_DATE, new Date());
    		    searchFeilds = accountManager.saveMySearchFeilds(searchFeilds);
    	 }else if(Objects.nonNull(date) || Objects.nonNull(endDate) || Objects.nonNull(accountType) ||
    			 Objects.nonNull(categoryName) || Objects.nonNull(description) || 
    			 (Objects.nonNull(Search) && Search.equalsIgnoreCase("Search"))){    		 
    		searchFeilds.append(Constants.ACCOUNT_TYPE, accountType);
 		    searchFeilds.append(Constants.CATEGORY_NAME, categoryName);
 		    searchFeilds.append(Constants.AMOUNT, request.getParameter("amount"));
 		    searchFeilds.append(Constants.DESCRIPTION, description);		
 		    searchFeilds.append(Constants.USER_ID, userId);		
 		    searchFeilds.append(Constants.START_DATE, date);
 		    searchFeilds.append(Constants.END_DATE, endDate);
    		searchFeilds = accountManager.updateMySearchFeilds(searchFeilds.get("_id").toString(),searchFeilds);
    	 }else{
    		 if(null!=searchFeilds.get(Constants.START_DATE)){
    			 date = searchFeilds.get(Constants.START_DATE).toString();
    		 }
    		 if(null!=searchFeilds.get(Constants.END_DATE)){
    			 endDate = searchFeilds.get(Constants.END_DATE).toString();
    		 }
    		 if(null!=searchFeilds.get(Constants.ACCOUNT_TYPE)){
    			 accountType = searchFeilds.get(Constants.ACCOUNT_TYPE).toString();
    		 }
    		 if(null!=searchFeilds.get(Constants.CATEGORY_NAME)){
    			 categoryName = searchFeilds.get(Constants.CATEGORY_NAME).toString();
    		 }
    		 if(null!=searchFeilds.get(Constants.DESCRIPTION)){
    			 description = searchFeilds.get(Constants.DESCRIPTION).toString();
    		 }
    	 }
    	 
    	 model = accountManager.searchAccount(date,endDate,accountType,categoryName,description,amount,userId,model);
    	 
         model.addAttribute("date", date);
         model.addAttribute("endDate", endDate);
         model.addAttribute("accountType", accountType);
         model.addAttribute("categoryName", categoryName);
         model.addAttribute("amount", amount);
         model.addAttribute("description", description);
         
         if(Objects.isNull(date) && Objects.isNull(endDate) && Objects.isNull(accountType) &&
        		 Objects.isNull(description) && Objects.isNull(categoryName)){        	 
        	 Pair<Date, Date> dates = CommonUtil.getCurrentMonthDateRange();    		
    		 String range = CommonUtil.dateToString(dates.getLeft())+" to "+CommonUtil.dateToString(dates.getRight());    		 
    		 model.addAttribute("range", range);    		
         }else{
        	 model.addAttribute("range", "Search Result");
         }
         
         return "account/accountlist";
    }
    
    
    @RequestMapping("/editaccount/{id}")
    public String editAccount(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id) {
    	               
    	 Document task = accountManager.getAccountById(id);         
    	 model.addAttribute("_id", task.get("_id").toString());
    	 model.addAttribute("date", CommonUtil.dateToString(task.getDate("date")));
         model.addAttribute("amount", task.get("amount").toString());
         model.addAttribute("accountType", task.get("accountType").toString());
         
         List<Document> aclist = accountManager.findAllCategory(CommonUtil.getSessionCookie(request,"_id"));
         model.addAttribute("lists", aclist);
         
         if(null != task.get("categoryName")){
        	 model.addAttribute("categoryName", task.get("categoryName").toString());
         }else{
        	 model.addAttribute("categoryName", "");
         }
         model.addAttribute("description", task.get("description").toString());
         return "account/editaccountpage";          
    }
    
   
    
    @RequestMapping("/updateAccount2")
    public String updateAccount2(HttpServletRequest request,
            HttpServletResponse reponses, Model model) {
    	
    	 String date = request.getParameter("date");    	 
         String accountType = request.getParameter("accountType");
         String description = request.getParameter("description");
         long amount = Long.parseLong(request.getParameter("amount"));
         String categoryName = request.getParameter("categoryName");
         
         String acId = request.getParameter("_id");
         boolean isCheck = CommonUtil.accountValidation(model,date,accountType,categoryName,description,amount);
         if(isCheck){
        	 accountManager.updateAccount(acId,date,accountType,categoryName,description,amount);     
        	 try {
					reponses.sendRedirect("accountlist");
				} catch (IOException e) {					
					e.printStackTrace();
				}
        	 
        	 //model = accountManager.searchAccount("","","","",0,userId,model);
             return "account/accountlist";
         }else{
        	 model.addAttribute("_id", acId);
        	 model.addAttribute("date", date);
 	        model.addAttribute("accountType", accountType);
 	        model.addAttribute("categoryName", categoryName);
 	        model.addAttribute("description", description);      
 	        model.addAttribute("amount", amount);
             return "account/editaccountpage";
         }        
    }
    
    @RequestMapping("/deleteaccount/{id}")
    public String deleteAccount(HttpServletRequest request,
            HttpServletResponse reponses,Model model,
            @PathVariable(value = "id") String id) {    	              
    	 boolean isRemove = accountManager.deleteAccountById(id);  
    	 String userId = CommonUtil.getSessionCookie(request,"_id");
    	 model = accountManager.searchAccount("","","","","",0,userId,model);
               
         return "account/accountlist";      
    }

    @RequestMapping("/addcategory")
	public String addCategory(Model model) {
		// TODO Auto-generated method stub
		  model.addAttribute("categoryName", "");
	      model.addAttribute("categoryType", "");
	        
		return "account/addcategory";
	}

    @RequestMapping("/savecategory")
	public String saveCategory(HttpServletRequest request,
			HttpServletResponse reponses, Model model) {
		

    	 String categoryName = request.getParameter("categoryName");
         String categoryType = request.getParameter("categoryType");
        
         boolean isCheck = CommonUtil.categoryValidation(model,categoryType,categoryName);
        
         if(isCheck){
        	 String userId = CommonUtil.getSessionCookie(request,"_id");
        	 String taskId = accountManager.saveCategory(categoryName,categoryType,userId);        	 
        	 if(null!=taskId){
        		  List<Document> aclist = accountManager.findAllCategory(userId);
        	      model.addAttribute("lists", aclist);
                 return "account/categorylist";
               }else{
               	model.addAttribute("ecategoryType", "Save category Error");
               	return "account/addcategory"; 
               }        	 
         }else{
        	 model.addAttribute("categoryType", categoryType);
 	        model.addAttribute("categoryName", categoryName);
        	 return "account/addcategory"; 
         }
       
         
         
	}

	@RequestMapping("/categorylist")
	public String findAllCategory(HttpServletRequest request,
			HttpServletResponse reponses, Model model) {
		
		String userId = CommonUtil.getSessionCookie(request,"_id");
		List<Document> aclist = accountManager.findAllCategory(userId);
	    model.addAttribute("lists", aclist);
        return "account/categorylist";
	}
	
	
	
	
   
	/*@RequestMapping("/accExport")
    public ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response){
				
		String date = request.getParameter("date");
   	    String endDate = request.getParameter("endDate");
        String accountType = request.getParameter("accountType"); 
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");
        
        
        String screenName = "Account";
        String userId = CommonUtil.getSessionCookie(request,"_id");
   	    Document searchFeilds = accountManager.getMySearchFeilds(userId,screenName);
   	 if(null!=searchFeilds){
		   	 if(null!=searchFeilds.get(Constants.START_DATE)){
				 date = searchFeilds.get(Constants.START_DATE).toString();
			 }
			 if(null!=searchFeilds.get(Constants.END_DATE)){
				 endDate = searchFeilds.get(Constants.END_DATE).toString();
			 }
			 if(null!=searchFeilds.get(Constants.ACCOUNT_TYPE)){
				 accountType = searchFeilds.get(Constants.ACCOUNT_TYPE).toString();
			 }
			 if(null!=searchFeilds.get(Constants.CATEGORY_NAME)){
				 categoryName = searchFeilds.get(Constants.CATEGORY_NAME).toString();
			 }
			 if(null!=searchFeilds.get(Constants.DESCRIPTION)){
				 description = searchFeilds.get(Constants.DESCRIPTION).toString();
			 }
   	 }
        System.out.println("date "+date);
        System.out.println("endDate "+endDate);
        System.out.println("accountType "+accountType);
        
        long amount = 0;
        if(StringUtil.isNotBlank(request.getParameter("amount"))){
       	 amount = Long.parseLong(request.getParameter("amount"));
        }
   	    
		List<Document> acc = accountManager.findAllAccount(date, endDate, accountType, description, amount, userId);
    	 
		System.out.println("Count "+acc.size());
		return new ModelAndView("ExcelAccountsSummary","revenueData",acc);
			
	}*/
	
}