package com.sjsu.proxyAuth.Service;

import com.sjsu.proxyAuth.Repository.AttendanceRepo;
import com.sjsu.proxyAuth.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public void findById(String id){ attendanceRepo.findById(id);}

    public List<Attendance> getUserAttendance(String email) {
        return attendanceRepo.findByEmailOrderByCheckInDateDesc(email);
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

    public Attendance getLatestAttendance(String email) {
        // Find the latest attendance record for the given email
        return attendanceRepo.findFirstByEmailOrderByCheckInDateDesc(email);
    }

    public boolean isLatestRecordFromToday(String email) {
        // Get the latest attendance record
        Attendance latestAttendance = getLatestAttendance(email);

        // Check if the latest record exists and is from today
        return Optional.ofNullable(latestAttendance)
                .map(attendance -> attendance.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .filter(today -> today.isEqual(LocalDate.now()))
                .isPresent();
    }

}
