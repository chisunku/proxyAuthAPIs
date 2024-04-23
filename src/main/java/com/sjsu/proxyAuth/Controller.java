package com.sjsu.proxyAuth;

import com.sjsu.proxyAuth.Service.AttendanceService;
import com.sjsu.proxyAuth.Service.EmployeeService;
import com.sjsu.proxyAuth.Service.LocationService;
import com.sjsu.proxyAuth.Service.S3Service;
import com.sjsu.proxyAuth.model.Attendance;
import com.sjsu.proxyAuth.model.Employee;
import com.sjsu.proxyAuth.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


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

    @Autowired
    private S3Service s3Service;


    @GetMapping("/")
    public String test(){
        Date date = new Date();
        System.out.println("date timezone : "+date.getTimezoneOffset());
        return "Connected..";
    }

    @GetMapping("/adminEmail")
    public Employee adminEmail(@RequestParam String email, @RequestParam String password){
        try{
            Employee employee = employeeService.getByEmail(email);
            if(employee.getPassword().equals(password)) {
                System.out.println("admin email : " + email + " " + employee.getName());
                return employee;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/emailAuth")
    public Employee emailAuth(@RequestParam String email, @RequestParam String password, @RequestParam String userId){
        try{
            Employee employee = employeeService.getByEmail(email);
            System.out.println("Email auth : "+email+" "+password+" "+userId+" "+employee.getUserId());
            if(employee.getUserId().equals(userId) && employee.getPassword().equals(password)) {
                System.out.println("email verified! "+employee.getName());
                return employee;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/userIdAuth")
    public Employee userIdAuth(@RequestParam String userId){
        try{
            System.out.println("user id : "+userId);
            Employee employee = employeeService.getByUserId(userId);
//            System.out.println("biometric auth : "+userId+" "+employee.getUserId());
            return employee;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
        List<Location> loc =  locationService.getAllLocations();
        System.out.println("get all locations : "+loc);
        return loc;
    }

    @GetMapping("/getUserAttendance")
    public List<Attendance> getUserAttendance(@RequestParam String email){
        System.out.println("in fetching attendance");
        return attendanceService.getUserAttendance(email);
    }

    @GetMapping("/getLatestRecord")
    public ResponseEntity<Attendance> getLatestRecord(@RequestParam String email){
        SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy");
        Date today = new Date();
        System.out.println("today : "+today+" email : "+email);
        Attendance attendance = attendanceService.getLatestAttendanceByEmail(email);
        if(attendance == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        Date myDate = Date.from(attendance.getCheckInDate().atZone(ZoneId.of("America/Los_Angeles")).toInstant());
        String formattedDate = formatter.format(attendance.getCheckInDate());
        String todayDate = formatter.format(today);
        System.out.println("checkin sting date: "+formattedDate+" today : "+todayDate);
        if(formattedDate.equals(todayDate)){
            System.out.println("yes the dates match!!");
            return ResponseEntity.ok(attendance);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        return ResponseEntity.ok(attendance);

    }

    @PostMapping("/checkInUser")
    public Attendance markAttendance(@RequestBody Attendance attendance){
        System.out.println("in checkin user : "+attendance.getEmail());
        attendanceService.saveAttendance(attendance);
        return attendance;
    }

    @PostMapping("/checkout")
    public Attendance checkOut(@RequestBody Attendance attendance){
        //fetch by id -> set -> save
        Attendance checkin = attendanceService.getLatestAttendanceByEmail(attendance.getEmail());
        System.out.println("ID : "+checkin.getId());
        checkin.setCheckOutDate(attendance.getCheckOutDate());
        attendanceService.saveAttendance(checkin);
        return checkin;
    }

    @GetMapping("/isUserInsideAnyOffice")
    public Location isUserInsideAnyOffice(@RequestParam double latitude,
                                          @RequestParam double longitude) {
        PolygonChecker polygonChecker = new PolygonChecker();
        List<Location> officeLocations = locationService.getAllLocations();
        Location.Point userLoc = new Location.Point();
        userLoc.setLatitude(latitude);
        userLoc.setLongitude(longitude);
        System.out.println("office locations: "+ officeLocations.size());
        for (Location office : officeLocations) {
            if (office.getPolygon() != null && polygonChecker.isPointInsidePolygon(userLoc, office.getPolygon())) {
                System.out.println("found office: "+office.getName());
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
        if(emp ==  null)
            return null;
        System.out.println("in get emp : "+emp.getName());
        return emp;
    }

    @PostMapping("/registerEmployee")
    public Employee registerEmployee(@RequestBody Employee employee) {
        System.out.println("Registering user : "+employee.getName()+" "+employee.getImageURL());
        try{
            //find the emp
            Employee emp = employeeService.getByEmail(employee.getEmail());
            //if not there then tell invalid emp, reach out to admin
            if(emp==null){
                return null;
            }
            else{
                emp.setFace(employee.getFace());
                emp.setImageURL(employee.getImageURL());
                emp.setPassword(employee.getPassword());
                emp.setUserId(employee.getUserId());
                employeeService.saveEmployee(emp);
                return emp;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/putImageToBucket")
    public String putImageToBucket(@RequestParam MultipartFile file, @RequestParam String file_name) {
        try {
            s3Service.uploadFile(file, file_name);
            return "https://"+s3Service.getBucketName()+".s3."+s3Service.getRegion()+".amazonaws.com/"+file_name;
        } catch (IOException e) {
            return "Error creating object";
        }
    }

    @GetMapping("/getImages")
    public List<String> getImages(){
        return s3Service.listObjects();
    }

    @GetMapping("/getEmp")
    public Employee getEmp(@RequestParam Employee employee){
        System.out.println("in get emp : "+employee.getName());
        return employee;
    }

    @DeleteMapping("/deleteLocation")
    public String deleteLocation(@RequestParam String locationId){
        try {
            locationService.deleteLocation(locationId);
            return "Location deleted";
        }catch(Exception e){
            return "something went wrong..";
        }
    }

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return employees;
    }

    @PostMapping("/addEmployees")
    public String addEmployees(@RequestParam MultipartFile file) {
        //read from file and save employees
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }
        employeeService.saveEmployeesFromCSV(file);
        return "File uploaded successfully";
    }
}
