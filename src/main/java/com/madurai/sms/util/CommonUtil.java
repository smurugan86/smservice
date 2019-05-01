package com.madurai.sms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.springframework.ui.Model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


//import sun.misc.BASE64Encoder;


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
		  
		  
		/*public static String makePasswordHash(String password, String salt) {
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
	    }*/

		public static Random getRandom() {
			final ThreadLocal<Random> random = new ThreadLocal<Random>();
	        Random result = random.get();
	        if (result == null) {
	            result = new Random();
	            random.set(result);
	        }
	        return result;
	    }
		
		// 8-byte Salt
		static byte[] salt = {
			(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
		};
		// Iteration count
		static int iterationCount = 19;

		
		public static String encrypt(String secretKey, String plainText) 
				throws Exception{

			if(! (null != secretKey && secretKey.trim().length() > 0 && null != plainText && plainText.trim().length() > 0)) {
				throw new Exception("Encrypt :Please provide valid input!");
			}

			//Key generation for enc and desc
			KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);        
			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			//Enc process
			Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);      
			Charset charSet=StandardCharsets.UTF_8;       
			byte[] in = plainText.getBytes(charSet.toString());
			byte[] out = ecipher.doFinal(in);
			String encStr=Base64.encodeBase64String(out);
			return encStr;
		}
		
		
		public static String decrypt(String secretKey, String encryptedText)
				throws Exception{
			if(! (null != secretKey && secretKey.trim().length() > 0 && null != encryptedText && encryptedText.trim().length() > 0)) {
				throw new Exception("Decrypt :Please provide valid input!");
			}

			//Key generation for enc and desc
			KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);        
			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
			//Decryption process; same key will be used for decr
			Cipher dcipher=Cipher.getInstance(key.getAlgorithm());
			dcipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
			byte[] enc = DatatypeConverter.parseBase64Binary(encryptedText);
			byte[] utf8 = dcipher.doFinal(enc);
			Charset charSet=StandardCharsets.UTF_8;     
			String plainStr = new String(utf8, charSet.toString());
			return plainStr;
		}    
		
		public static void main(String[] args) throws Exception {
			String saltKey = "vmdev";//UUID.randomUUID().toString().replaceAll("-", "");   
			String plainText = "{6/Mn2wJ6SMY";
			//String saltKey = args[0];   
			//String plainText = args[1];
			//d571f458784247fcaf878b222ce6230c
			try{
				if(null != saltKey && saltKey.trim().length() > 0 && null != plainText && plainText.trim().length() > 0){

					String enc = encrypt(saltKey, plainText);
					System.out.println("saltKey: "+saltKey);
					System.out.println("Original text: "+plainText);
					System.out.println("Encrypted text: "+enc);

					//String enc = "SGv3Nu/qk8lA+Uxdmb1zlg==1";
					String plainAfter = decrypt("vmstage", "UPUZ56p6E7l+W2gTW+1Q0g==");

					System.out.println("Original text after decryption: "+plainAfter);
				}
				else {
					//System.out.println("Please provide input properly!");
				}
			}
			catch(Exception e) {
				//System.out.println("Exception in VMPasswordSecureUtil: "+e.toString());
				e.printStackTrace();
			}
		}
		
}