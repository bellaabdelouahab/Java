package Main;

import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

public class Connect {
    public MongoClient mongoClient;
    public MongoIterable<String> collections;
    public MongoDatabase database;
    public MongoCollection<Document> collection;

    public void StartConnection(){
        //connect to mongo db server 
        mongoClient = new MongoClient("localhost",27017);
    }
    public MongoIterable<String> GetDatabasesName(){
        //create a database name
        return mongoClient.listDatabaseNames();
    }
    public void GetCollectionNames(String DatabaseName){
        database = mongoClient.getDatabase(DatabaseName);
        collections = database.listCollectionNames();
    }
    public void getCollection(String name){
        collection= database.getCollection(name);
    }
    public MongoCursor<Document> ReadThrowCollection(){
        return collection.find().iterator();
    }
}
