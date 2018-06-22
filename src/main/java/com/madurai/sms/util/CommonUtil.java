package com.madurai.sms.util;

import org.bson.Document;

//import sun.misc.BASE64Encoder;

//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.pdf.PdfWriter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class CommonUtil{
	
	
		public static MongoCollection<Document> getMongoDBConnection(
				String collectionName) {			
			 MongoClient mongoClient = new MongoClient(new MongoClientURI(Constants.DB_URL));
		     MongoDatabase myDatabase = mongoClient.getDatabase(Constants.DB_NAME);
		     MongoCollection<Document>  userCollection = myDatabase.getCollection(collectionName);			 
			return userCollection;
		}

	
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
		

		
}