package com.sjsu.proxyAuth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Employee")
public class Employee {
    @Getter@Setter
    String name;
    @Getter@Setter
    String email;
    @Getter@Setter
    Location location;
    @Getter@Setter
    Face face;
    @Getter@Setter
    String password;
}
