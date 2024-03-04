package com.sjsu.proxyAuth.Service;

import com.sjsu.proxyAuth.Repository.EmployeeRepo;
import com.sjsu.proxyAuth.Repository.LocationRepo;
import com.sjsu.proxyAuth.model.Employee;
import com.sjsu.proxyAuth.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
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

}
