package com.alierkin.EarthquakeApp.Repositories;

import com.alierkin.EarthquakeApp.DTOs.Earthquake;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EarthquakeRepository extends MongoRepository<Earthquake, String> {
}
