package com.alierkin.EarthquakeApp.Controllers;

import com.alierkin.EarthquakeApp.DTOs.Earthquake;
import com.alierkin.EarthquakeApp.Services.EarthquakeDataProcessor;
import com.alierkin.EarthquakeApp.Services.EarthquakeKafkaProducer;
import com.alierkin.EarthquakeApp.Services.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("earthquake")
@CrossOrigin
public class EarthquakeController {

    @Autowired
    EarthquakeKafkaProducer earthquakeKafkaProducer;

    @Autowired
    EarthquakeService earthquakeService;


    @PostMapping("/add")
    public Earthquake sendEarthquake(@RequestBody Earthquake earthquake) throws Exception {

        LocalDateTime now = LocalDateTime.now();

        // Tarihi istenen formata dönüştür
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = now.format(formatter);

        earthquake.setTime(formattedDateTime);
        earthquakeKafkaProducer.send(earthquake);

        return earthquake;
    }

    @GetMapping("/get")
    public List<Earthquake> getEarthquakes() {
        return earthquakeService.getEarthquakes();
    }

    @GetMapping("/getAll")
    public List<Earthquake> getAllEarthquakes() {
        return earthquakeService.getAllEarthquakes();
    }

}
