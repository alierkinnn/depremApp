package com.alierkin.EarthquakeApp.Services;

import com.alierkin.EarthquakeApp.DTOs.Earthquake;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;


public class MongoDBSink extends RichSinkFunction<Earthquake> {

    private transient MongoCollection<Document> collection;
    private final String connectionString;
    private final String databaseName;
    private final String collectionName;

    public MongoDBSink(String connectionString, String databaseName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        this.collection = getMongoCollection();
    }

    @Override
    public void invoke(Earthquake value, SinkFunction.Context context) throws Exception {
        Document document = new Document();
        document.append("lat", value.getLat())
                .append("lon", value.getLon())
                .append("mag", value.getMag())
                .append("time", value.getTime());
        collection.insertOne(document);
    }

    private MongoCollection<Document> getMongoCollection() {
        // MongoDB bağlantısı oluşturma
        var mongoClient = MongoClients.create(connectionString);

        // Veritabanı ve koleksiyonu seçme
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        return database.getCollection(collectionName);
    }

}
