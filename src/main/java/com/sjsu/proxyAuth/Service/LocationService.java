package com.sjsu.proxyAuth.Service;

import com.sjsu.proxyAuth.Repository.LocationRepo;
import com.sjsu.proxyAuth.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationService {
    private final LocationRepo locationRepository;

    @Autowired
    public LocationService(LocationRepo locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public void deleteLocation(String id) {
        locationRepository.deleteById(id);
    }

}
