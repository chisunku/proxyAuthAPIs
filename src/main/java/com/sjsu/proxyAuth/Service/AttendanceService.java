package com.sjsu.proxyAuth.Service;

import com.sjsu.proxyAuth.Repository.AttendanceRepo;
import com.sjsu.proxyAuth.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepo attendanceRepo;

    @Autowired
    public AttendanceService(AttendanceRepo attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    public void saveAttendance(Attendance attendance) {
        attendanceRepo.save(attendance);
    }

    public List<Attendance> getUserAttendance(String email) {
        return attendanceRepo.findByEmail(email);
    }

    public Attendance getLatestAttendanceByEmail(String email) {
        return attendanceRepo.findFirstByEmailOrderByCheckInDateDesc(email);
    }

    public void updateCheckinTime(Attendance att) {
        Attendance attendance = attendanceRepo.findFirstByEmailOrderByCheckInDateDesc(att.getEmail());
        if (attendance != null) {
            attendance.setCheckInDate(att.getCheckInDate());
            attendanceRepo.save(attendance);
        }
        else{
            attendanceRepo.save(att);
        }
    }

}
