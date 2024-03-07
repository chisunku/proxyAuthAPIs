package com.sjsu.proxyAuth.Repository;

import com.sjsu.proxyAuth.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceRepo extends MongoRepository<Attendance, String> {
    List<Attendance> findByEmailOrderByCheckInDateDesc(String email);

    Attendance findFirstByEmailOrderByCheckInDateDesc(String email);

    Attendance findFirstByEmailAndCheckInDateOrderByCheckInDateDesc(String email, Date checkInDate);




}
