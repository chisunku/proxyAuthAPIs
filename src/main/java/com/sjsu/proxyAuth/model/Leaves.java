package com.sjsu.proxyAuth.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a")
    private Date endDate;
    private String leaveReason;
    private String approvalManager;
    private String approvalStatus = "Pending";
    private int count;
}
