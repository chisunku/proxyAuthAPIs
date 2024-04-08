package com.sjsu.proxyAuth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Location")
public class Location {
    @Id@Getter@Setter
    private String id;
    @Getter@Setter
    private String address;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private List<Point> polygon;

    public static class Point {
        @Getter@Setter
        private double latitude;
        @Getter@Setter
        private double longitude;
    }
}
