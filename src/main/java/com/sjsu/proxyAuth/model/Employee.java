package com.sjsu.proxyAuth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Employee")
public class Employee {

    @Getter@Setter@Id
    String id;
    @Getter@Setter
    String name;
    @Getter@Setter
    String email;
    @Getter@Setter
    Location location;
    @Getter@Setter
    FaceModel face;
    @Getter@Setter
    String password;
    @Getter@Setter
    String userId;
    @Getter@Setter
    String designation;
    @Getter@Setter
    String address;
    @Getter@Setter
    String imageURL;
    @Getter@Setter
    String contactNo;

    public Employee(String name, String email, Location location, String designation, String address, String contactNo) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.designation = designation;
        this.address = address;
        this.contactNo = contactNo;
    }
}
