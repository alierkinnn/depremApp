package com.alierkin.EarthquakeApp.Services;

import com.alierkin.EarthquakeApp.DTOs.Earthquake;
import com.alierkin.EarthquakeApp.Repositories.EarthquakeRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EarthquakeService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EarthquakeRepository earthquakeRepository;

    public List<Earthquake> getEarthquakes() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime pastTime = currentTime.minusMinutes(1); // Son 1 dakikayı alıyoruz
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        Query query = new Query();
        query.addCriteria(Criteria.where("time")
                .gte(pastTime.format(formatter)) // ISO 8601 formatına dönüştür
                .lte(currentTime.format(formatter))); // ISO 8601 formatına dönüştür

        List<Earthquake> allEarthquakes = mongoTemplate.find(query, Earthquake.class);
        List<Earthquake> earthquakes = new ArrayList<>();

        for (int i = 0; i < allEarthquakes.size(); i++) {
            Earthquake bigOne = new Earthquake();
            Earthquake current = allEarthquakes.get(i);
            boolean isWithin50km = false;

            for (int j = 0; j < allEarthquakes.size(); j++) {
                if (i != j) {
                    Earthquake other = allEarthquakes.get(j);
                    if (isWithin50km(current, other)) {
                        isWithin50km = true;
                        if(current.getMag() > other.getMag()){
                            bigOne = current;
                        }
                        else{
                            bigOne = other;
                        }
                        break;
                    }
                }
            }

            if (!isWithin50km) {
                earthquakes.add(current);
            }
            else{
                earthquakes.add(bigOne);
            }
        }

        return earthquakes;


    }

    private boolean isWithin50km(Earthquake earthquake1, Earthquake earthquake2) {
        // Earthquake nesnelerinden latitude ve longitude değerlerini al
        double lat1 = Math.toRadians(earthquake1.getLat());
        double lon1 = Math.toRadians(earthquake1.getLon());
        double lat2 = Math.toRadians(earthquake2.getLat());
        double lon2 = Math.toRadians(earthquake2.getLon());

        // Dünya yarıçapı (km cinsinden)
        double R = 6371;

        // Haversine formülüne göre iki nokta arasındaki mesafeyi hesapla
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        // Mesafeyi 50 km ile karşılaştır
        return distance <= 50;
    }
    public List<Earthquake> getAllEarthquakes() {
        return earthquakeRepository.findAll();
    }
}
