package com.sjsu.proxyAuth.model;

import com.mongodb.client.model.geojson.Point;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Location")
public class Location {
    @Getter@Setter
    private String address;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private List<Point> polygon;
}
