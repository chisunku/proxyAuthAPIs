package com.sjsu.proxyAuth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Attendance")
public class Attendance {
    @Getter@Setter
    String email;
    @Getter@Setter
    String time;
    @Getter@Setter
    String timeRef;
    @Getter@Setter
    Date checkInDate;
    @Getter@Setter
    Date checkOutDate;
    @Getter@Setter
    private int imgId;
    @Getter@Setter
    private Location location;
}
