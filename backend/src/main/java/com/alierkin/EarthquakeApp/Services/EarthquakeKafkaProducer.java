package com.alierkin.EarthquakeApp.Services;

import com.alierkin.EarthquakeApp.DTOs.Earthquake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EarthquakeKafkaProducer {

    private final KafkaTemplate<String, Earthquake> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public EarthquakeKafkaProducer(KafkaTemplate<String, Earthquake> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Earthquake earthquake){
        this.kafkaTemplate.send(topic,earthquake);
        System.out.println("Earthquake is sent to kafka : " + earthquake);
    }

}

