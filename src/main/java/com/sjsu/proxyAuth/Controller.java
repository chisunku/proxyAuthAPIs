package com.sjsu.proxyAuth;

import com.sjsu.proxyAuth.Service.AttendanceService;
import com.sjsu.proxyAuth.Service.LocationService;
import com.sjsu.proxyAuth.model.Attendance;
import com.sjsu.proxyAuth.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
public class Controller {

    @Autowired
    LocationService locationService;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    private MongoTemplate mongoTemplate;


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

    @GetMapping("/getUserAttendance")
    public List<Attendance> getUserAttendance(@RequestParam String email){
        System.out.println("in fetching attendance");
        return attendanceService.getUserAttendance(email);
    }

    @GetMapping("/getLatestRecord")
    public HashMap<String, Date> getLatestRecord(@RequestParam String email){
        Attendance attendance = attendanceService.getLatestAttendanceByEmail(email);
        HashMap<String, Date> map = new HashMap<>();
        map.put("Check In", attendance.getCheckInDate());
        map.put("Check Out", attendance.getCheckOutDate());
        return map;
    }

    @GetMapping("/isUserInsideAnyOffice")
    public Location isUserInsideAnyOffice(@RequestParam double latitude,
                                         @RequestParam double longitude) {
        PolygonChecker polygonChecker = new PolygonChecker();
        List<Location> officeLocations = locationService.getAllLocations();
        Location.Point userLoc = new Location.Point();
        userLoc.setLatitude(latitude);
        userLoc.setLongitude(longitude);
        System.out.println("office locations: "+officeLocations.size());
        for (Location office : officeLocations) {
            if (office.getPolygon() != null && polygonChecker.isPointInsidePolygon(userLoc, office.getPolygon())) {
                return office;  // User is inside at least one office location
            }
        }

        return null;  // User is not inside any office location
    }
}
