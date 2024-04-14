package com.alierkin.EarthquakeApp.Services;

import com.alierkin.EarthquakeApp.DTOs.Earthquake;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.util.Properties;

@Component
public class EarthquakeDataProcessor {

    public static class ParseEarthquakeFunction implements MapFunction<String, Earthquake> {

        @Override
        public Earthquake map(String value) throws Exception {
            return parseEarthquake(value);
        }

        private Earthquake parseEarthquake(String json) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                double lat = jsonObject.getDouble("lat");
                double lon = jsonObject.getDouble("lon");
                double mag = jsonObject.getDouble("mag");
                String time = jsonObject.getString("time");

                return new Earthquake(lat, lon, mag,time);
            } catch (JSONException e) {
                System.err.println("JSON parse hatası: " + json + e.getMessage());
                throw new RuntimeException("Failed to parse JSON: " + json + e.getMessage());
            }
        }
    }

    public void processEarthquakeData() throws Exception {
        // Flink environment oluşturma
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Kafka parametreleri
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "flink-consumer-group");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        // Kafka tüketici başlatma
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>("earthquake-topic", new SimpleStringSchema(), properties);

        // Kafka'dan verileri okuma
        DataStream<String> kafkaStream = env.addSource(kafkaConsumer);

        // Verileri işleme
        DataStream<Earthquake> processedStream = kafkaStream.map(new ParseEarthquakeFunction());

        // MongoDB'ye veri aktarımı
        processedStream.addSink(new MongoDBSink("mongodb+srv://alierkinbuyukmedar:12345@cluster0.qeafytn.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0", "Earthquake-App", "earthquakes"));


        // Verileri işleme
        processedStream.print();

        // Akışı başlatma
        env.execute("Earthquake Data Processor");
    }
}

