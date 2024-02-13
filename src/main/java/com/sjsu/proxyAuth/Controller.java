package com.sjsu.proxyAuth;

import com.sjsu.proxyAuth.Service.LocationService;
import com.sjsu.proxyAuth.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class Controller {

    @Autowired
    LocationService locationService;

    @GetMapping("/")
    public String test(){
        return "Connected..";
    }

    @PostMapping("/addLocation")
    public String addLocation(@RequestBody Location location){
        try {
            locationService.saveLocation(location);
            return "Location added";
        }catch(Exception e){
            return "something went wrong..";
        }
    }

    @GetMapping("/getAllLocations")
    public List<Location> getAllLocation(){
        return locationService.getAllLocations();
    }
}
