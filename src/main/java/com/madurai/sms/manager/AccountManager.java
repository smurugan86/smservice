package com.madurai.sms.manager;



import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.madurai.sms.dao.AccountDAO;
import com.madurai.sms.util.CommonUtil;
import com.mongodb.client.AggregateIterable;


@Component
public class AccountManager
{
	@Autowired
	AccountDAO accountDAO;

	////date,accountType, description,amount,userId
	public String saveAccount(String date, String accountType,String categoryName,
			String description,long amount,String userId) {
		// TODO Auto-generated method stub
		return accountDAO.saveAccount(date,accountType,categoryName,description,amount,userId);
	}



	public List<Document> findAllAccount(String date,String endDate,String accountType,String description,long amount, String userId) {
		// TODO Auto-generated method stub
		Iterable<Document> acList = accountDAO.findAllAccount(date,endDate,accountType,description,description,amount,userId);		
		if(null!=acList){
			List<Document> accList = new ArrayList<Document>();
			for (Document document : acList) {
				String de =document.get("description").toString();
				 de =	de.replaceAll("<p>", " ");
				 de =	de.replaceAll("\\r?\\n", " ");	
				 document.put("description", de);
				document.put("date", CommonUtil.dateToString(document.getDate("date")));
				accList.add(document);
			}
			return accList;
		}else{
			return null;
		}
	}

	public Document getAccountById(String id) {
		// TODO Auto-generated method stub
		return accountDAO.getAccountById(id);
	}
//String acId, String date,String accountType,String description,long amount
	public void updateAccount(String acId, String date,String accountType,String categoryName,String description,long amount) {
		// TODO Auto-generated method stub
		accountDAO.updateAccount(acId,date, accountType, categoryName,description,amount);
	}
	public boolean deleteAccountById(String taskId) {
		// TODO Auto-generated method stub
		return accountDAO.deleteAccountById(taskId);
	}
	public List<Document> findAllCategory(String userId) {
		// TODO Auto-generated method stub
		Iterable<Document> acList = accountDAO.findAllCategory(userId);
		if(null!=acList){
			List<Document> accList = new ArrayList<Document>();
			for (Document document : acList) {				
				accList.add(document);
			}
			return accList;
		}
		
		/*
		 * Credit money 
		 * Debit  money
		 * 
		 * Money    
		 * SbiCard 
		 */
		return null;
	}
	public String saveCategory(String categoryName, String categoryType,
			String userId) {
		return accountDAO.saveCategory(categoryName,categoryType,userId);
	}
	
	public Model searchAccount(String date, String endDate, String accountType, String categoryName,
			String description, long amount, String userId, Model model) {
		// TODO Auto-generated method stub		
		Iterable<Document> acList = accountDAO.findAllAccount(date,endDate,accountType,categoryName,description,amount,userId);				
		List<Document> accountlist = new ArrayList<Document>();
		for (Document document : acList) {
			document.put("date", CommonUtil.dateToString(document.getDate("date")));
			document.put("amount", CommonUtil.convertLongToString(document.getLong("amount")));
			accountlist.add(document);
		}
		
		 if(accountlist.isEmpty()){
        	 model.addAttribute("error", "Account Information Not Found");
         }else{
        	 model.addAttribute("error", null);
        	 model.addAttribute("lists", accountlist);
        	 
        	AggregateIterable<Document> doc = accountDAO.getAccountTotal(date,endDate,accountType,categoryName,description,amount,userId);
        	long credit = 0;
        	long debit = 0;
     		for (Document document : doc) {
     			
     			if(document.get("_id").equals("Credit")){
     				credit = document.getLong("total");
     				System.out.println("document Total credit" +document.getLong("total"));
     			}
     			if(document.get("_id").equals("Debit")){
     				debit = document.getLong("total");
     				System.out.println("document Total debit" +document.getLong("total"));
     			}
     		}
     		
     		 model.addAttribute("credit", CommonUtil.convertLongToString(credit));
     		 model.addAttribute("debit", CommonUtil.convertLongToString(debit));
     		 model.addAttribute("balance", CommonUtil.convertLongToString(credit-debit));
     		 
        	 
         }
		
		
		
		return model;
	}
	public Document getMySearchFeilds(String userId, String screenName) {
		return accountDAO.getMySearchFeilds(userId,screenName);
	}
	public Document saveMySearchFeilds(Document searchFeilds) {
		// TODO Auto-generated method stub
		return accountDAO.saveMySearchFeilds(searchFeilds);
	}
	public Document updateMySearchFeilds(String id, Document searchFeilds) {
		// TODO Auto-generated method stub
		return accountDAO.updateMySearchFeilds(id,searchFeilds);
	}
	
}