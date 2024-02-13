package com.sjsu.proxyAuth;

import com.sjsu.proxyAuth.Service.AttendanceService;
import com.sjsu.proxyAuth.Service.LocationService;
import com.sjsu.proxyAuth.model.Attendance;
import com.sjsu.proxyAuth.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @PutMapping("/checkIn")
//    public String checkIn(@RequestBody Attendance attendance){
//        try{
//            attendanceService.updateCheckinTime(attendance);
//            return "updated successfully";
//        }catch(Exception e){
//            return "something went wrong!";
//        }
//    }
}
