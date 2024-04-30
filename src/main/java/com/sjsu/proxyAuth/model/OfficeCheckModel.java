package com.sjsu.proxyAuth.model;

import lombok.Getter;
import lombok.Setter;


public class OfficeCheckModel {
    @Getter@Setter
    private Employee employee;
    @Getter@Setter
    private double latitude;
    @Getter@Setter
    private double longitude;
}
