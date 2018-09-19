package com.madurai.sms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.springframework.ui.Model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import sun.misc.BASE64Encoder;


public class CommonUtil{
	
	
		public static MongoCollection<Document> getMongoDBConnection(
				String collectionName) {			
			 MongoClient mongoClient = new MongoClient(new MongoClientURI(Constants.DB_URL));
		     MongoDatabase myDatabase = mongoClient.getDatabase(Constants.DB_NAME);
		     MongoCollection<Document>  userCollection = myDatabase.getCollection(collectionName);			 
			return userCollection;
		}

		 public static Date stringToDate(String dateString){
			  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			  try {
					Date date = formatter.parse(dateString);
					return date;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			return null;		  
		  }
	    
		  public static String dateToString(Date date){		  
			  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");					
			  String closeDateStr = sdf.format(date); 					 
			  return closeDateStr;			  
		  }
		  
	
		  public static Pair<Date, Date> getCurrentMonthDateRange() {
			    Date begining, end;

			    {
			        Calendar calendar = getCalendarForNow();
			        calendar.set(Calendar.DAY_OF_MONTH,
			                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			        setTimeToBeginningOfDay(calendar);
			        begining = calendar.getTime();
			    }

			    {
			        Calendar calendar = getCalendarForNow();
			        calendar.set(Calendar.DAY_OF_MONTH,
			                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			        setTimeToEndofDay(calendar);
			        end = calendar.getTime();
			    }		    
			    return Pair.of(begining, end);
			}
		  
		  private static Calendar getCalendarForNow() {
			    Calendar calendar = GregorianCalendar.getInstance();
			    calendar.setTime(new Date());
			    return calendar;
			}

			private static void setTimeToBeginningOfDay(Calendar calendar) {
			    calendar.set(Calendar.HOUR_OF_DAY, 0);
			    calendar.set(Calendar.MINUTE, 0);
			    calendar.set(Calendar.SECOND, 0);
			    calendar.set(Calendar.MILLISECOND, 0);
			}
			
		  private static void setTimeToEndofDay(Calendar calendar) {
			    calendar.set(Calendar.HOUR_OF_DAY, 23);
			    calendar.set(Calendar.MINUTE, 59);
			    calendar.set(Calendar.SECOND, 59);
			    calendar.set(Calendar.MILLISECOND, 999);
			}
		  
		  public static String convertLongToString(long spendDollars) {			
				DecimalFormat formatter = new DecimalFormat("Rs ###,###,###.00");
				return formatter.format(spendDollars);			
		}

		  public static boolean accountValidation(Model model, String date,
					String accountType, String categoryName, String description,
					long amount) {
					
				boolean ischeck = true;
			
				 if(Objects.isNull(date)){
					 model.addAttribute("edate", "Date Required");
					 ischeck =  false;
				 }			 
				 if(Objects.isNull(accountType)){
					 model.addAttribute("eaccountType", "Account Type Required");
					 ischeck =  false;
				 }
				 if(Objects.isNull(categoryName)){
					 model.addAttribute("ecategoryName", "Category Required");
					 ischeck =  false;
				 }
				if(Objects.isNull(description)){
					model.addAttribute("edescription", "Description Required");
					ischeck =  false;
				 }
				
				if(amount==0){
					model.addAttribute("eamount", "Amount Required");
					ischeck =  false;
				}
				return ischeck;
			}
		  
		  
		  public static String getSessionCookie(final HttpServletRequest request,String cookieName) {
		        if (request.getCookies() == null) {
		            return null;
		        }
		        for (Cookie cookie : request.getCookies()) {
		            if (cookie.getName().equals(cookieName)) {
		                try {
							return URLDecoder.decode(cookie.getValue(), "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
		            }
		        }
		        return null;
		    }
		  
		  public static boolean categoryValidation(Model model,
					String categoryType, String categoryName) {
				
				boolean ischeck = true;
								
				 if(Objects.isNull(categoryType)){
					 model.addAttribute("ecategoryType", "Category Type Required");
					 ischeck =  false;
				 }
				 if(Objects.isNull(categoryName)){
					 model.addAttribute("ecategoryName", "Category Required");
					 ischeck =  false;
				 }
				
				return ischeck;
			}

		/*public static Object makePasswordHash(String password, String salt) {
			// TODO Auto-generated method stub
			return null;
		}*/
		  
		  
		public static String makePasswordHash(String password, String salt) {
	        try {
	            String saltedAndHashed = password + "," + salt;
	            MessageDigest digest = MessageDigest.getInstance("MD5");
	            digest.update(saltedAndHashed.getBytes());
	            BASE64Encoder encoder = new BASE64Encoder();
	            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
	            return encoder.encode(hashedBytes) + "," + salt;
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("MD5 is not available", e);
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
	        }
	    }

		public static Random getRandom() {
			final ThreadLocal<Random> random = new ThreadLocal<Random>();
	        Random result = random.get();
	        if (result == null) {
	            result = new Random();
	            random.set(result);
	        }
	        return result;
	    }
		

		
}