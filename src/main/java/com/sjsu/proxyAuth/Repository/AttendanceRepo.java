package com.sjsu.proxyAuth.Repository;

import com.sjsu.proxyAuth.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttendanceRepo extends MongoRepository<Attendance, String> {
    List<Attendance> findByEmail(String email);

    Attendance findFirstByEmailOrderByCheckInDateDesc(String email);

}
