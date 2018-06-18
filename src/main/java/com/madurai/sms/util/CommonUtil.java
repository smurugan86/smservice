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

	
		

		
}