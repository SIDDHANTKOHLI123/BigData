package mongodb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

public class MongoConnection implements Block<Document> {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Block<Document> printBlock=new MongoConnection();
		MongoClient c=MongoClients.create(); 
		MongoDatabase db=c.getDatabase("nyse_A");
		MongoCollection<Document> col=db.getCollection("A");
		List<Document> doc=new ArrayList<Document>();
		try {
		File input=new File("C:\\Users\\kohli\\Downloads\\NYSE_daily_prices_A.csv");
		Scanner scanner;
		scanner = new Scanner(input);
		
		while(scanner.hasNext())
		{
			Document d=new Document();
			String line=scanner.nextLine();
			String[] tokens=line.split(",");
			d.append("exchange", tokens[0]);
			d.append("stock_symbol", tokens[1]);
			d.append("date", tokens[2]);
			d.append("stock_price_open", tokens[3]);
			d.append("stock_price_high", tokens[4]);
			d.append("stock_price_low", tokens[5]);
			d.append("stock_price_close", tokens[6]);
			d.append("stock_volume", tokens[7]);
			d.append("stock_price_adj_close", tokens[8]);
			doc.add(d);
			//System.out.println(d);
		}
		//col.insertMany(doc);
		System.out.println("document count :"+ col.countDocuments());
		
		col.aggregate(
				Arrays.asList(
						Aggregates.match(Filters.eq("stock_symbol","AEA")),
								Aggregates.group("$stock_symbol", Accumulators.sum("count",1))
								))
				.forEach(printBlock);
		
		
	
		
	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		c.close();
	}
		public void apply(Document t) {
			// TODO Auto-generated method stub
			System.out.println(t.toJson());
			
		}
		
	

	}


