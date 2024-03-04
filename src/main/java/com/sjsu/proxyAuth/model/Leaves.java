package com.sjsu.proxyAuth.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
@Document(collection = "Leaves")
@Data
public class Leaves {

    @MongoId
    private String id;

    private String email;
    private String leaveType;
    private String leaveSubType;
    private String contactNumber;
    private Date startDate;
    private Date endDate;
    private String leaveReason;
    private String approvalStatus = "Pending";
}
