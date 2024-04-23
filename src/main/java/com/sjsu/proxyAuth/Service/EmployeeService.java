package com.sjsu.proxyAuth.Service;

import com.sjsu.proxyAuth.Repository.EmployeeRepo;
import com.sjsu.proxyAuth.model.Employee;
import com.sjsu.proxyAuth.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    private final LocationService locationService;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo, LocationService locationService) {
        this.employeeRepo = employeeRepo;
        this.locationService = locationService;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    public void saveEmployee(Employee employee) {
        employeeRepo.save(employee);
    }

    public List<Employee> getAllEmployee() {
        return employeeRepo.findAll();
    }

    public Employee getByEmail(String email){
        return employeeRepo.findByEmail(email);
    }

    public Employee getByUserId(String userId){
        return employeeRepo.findByUserId(userId);
    }

    public void saveEmployeesFromCSV(MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int header = 0;
            while ((line = br.readLine()) != null) {
                if(header == 0){
                    header++;
                    continue;
                }
                String[] data = line.split(",");
                Location location = locationService.getLocation(data[2]);
                Employee employee = new Employee(data[0], data[1], location, data[3], data[4], data[5]);
                employeeRepo.save(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
