package com.sjsu.proxyAuth.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "Attendance")
public class Attendance {
    @Getter@Setter
    String email;
    @Getter@Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a")
    Date checkInDate;
    @Getter@Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a")
    Date checkOutDate;
    @Getter@Setter
    private String img;
    @Getter@Setter
    private Location location;
}
