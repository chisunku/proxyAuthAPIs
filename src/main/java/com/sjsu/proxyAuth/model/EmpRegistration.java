package com.sjsu.proxyAuth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class EmpRegistration {
    @Getter @Setter
    Employee employee;
    @Getter @Setter
    MultipartFile file;
    @Getter @Setter
    String filename;
}
