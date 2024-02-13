package com.sjsu.proxyAuth.Repository;

import com.sjsu.proxyAuth.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepo extends MongoRepository<Location, String> {
    Location findByName(String name);
}
