package com.sjsu.proxyAuth.Repository;

import com.sjsu.proxyAuth.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepo extends MongoRepository<Employee, String> {
    Employee findByEmail(String email);
}
