package com.sjsu.proxyAuth;

import com.sjsu.proxyAuth.Service.AttendanceService;
import com.sjsu.proxyAuth.Service.EmployeeService;
import com.sjsu.proxyAuth.Service.LocationService;
import com.sjsu.proxyAuth.model.Attendance;
import com.sjsu.proxyAuth.model.Employee;
import com.sjsu.proxyAuth.model.Location;
import jakarta.websocket.server.PathParam;
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
    EmployeeService employeeService;

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
    public Attendance getLatestRecord(@RequestParam String email){
        return attendanceService.getLatestAttendanceByEmail(email);
    }

    @PostMapping("/checkInUser")
    public Attendance markAttendance(@RequestBody Attendance attendance){

        attendanceService.saveAttendance(attendance);
        return attendance;
        //check if record is there
//        Attendance rec = getLatestRecord(attendance.getEmail());
//        if(attendanceService.isLatestRecordFromToday(attendance.getEmail())){
//
//        }
//        else{
//            attendanceService.saveAttendance(attendance);
//            System.out.println("in checkin saving attendance "+attendance.getEmail());
//        }
//        return attendance;
        //update
        //save
    }

    @PutMapping("/checkout")
    public void checkOut(@RequestBody Attendance attendance){
        attendanceService.saveAttendance(attendance);
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

    @PostMapping("/registerEmp")
    public Employee registerEmp(@RequestBody Employee employee){
        if(employee == null)
            System.out.println("yes!");
        System.out.println("in register employee: "+ employee.getName());
        try {
            employeeService.saveEmployee(employee);
            return employee;
        }catch(Exception e){
            return null;
        }
    }

    @GetMapping("/getEmployeeByEmail")
    public Employee getEmployeeByEmail(@RequestParam String email){
        Employee emp = employeeService.getByEmail(email);
        System.out.println("in get emp : "+emp.getName());
        return emp;
    }
}
