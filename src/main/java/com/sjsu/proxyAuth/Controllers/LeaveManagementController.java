package com.sjsu.proxyAuth.Controllers;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.sjsu.proxyAuth.Repository.LeavesRepo;
import com.sjsu.proxyAuth.Service.LeaveManagementService;
import com.sjsu.proxyAuth.model.Leaves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class LeaveManagementController {
    @Autowired
    LeaveManagementService leaveManagementService;

    /**
     * This API is for applying leaves. All the parameters are captured and stored in Leaves collection
     * @param leaves
     * @throws Exception
     */
    @PostMapping("/leave")
    public Leaves applyLeave(@RequestBody  Leaves leaves) throws Exception {
        try {
            leaveManagementService.applyLeave(leaves);
        } catch (Exception e) {
            return null;
        }
        return leaves;
    }

    /**
     * This API is for updating an existing leave request for specific parameters
     * @param leave
     * @throws Exception
     */
    @PutMapping("/leave")
    public void updateLeave(@RequestBody  Leaves leave) throws Exception {
        leaveManagementService.updateLeave(leave);
    }


    /**
     *This API is for getting all leave requests
     * @param employeeId
     * @return
     */
    @GetMapping("/leave/{employeeId}/")
    public List<Leaves> getAllLeavesforEmployee(@PathVariable String employeeId) {
        List<Leaves> list = leaveManagementService.getAllLeavesforEmployee(employeeId);
        System.out.println("List of Leaves: "+list.size());
        return list;
    }

    /**
     *This API is for getting all leave requests per status
     * @param employeeEmail
     * @return
     */
    @GetMapping("/leave/{employeeEmail}/{status}")
    public List<Leaves> getAllLeavesforEmployeebyStatus(@PathVariable String employeeEmail, @PathVariable String status) {
        return leaveManagementService.getAllLeavesforEmployeeByStatus(employeeEmail,status);
    }

    @GetMapping("/getPastLeave")
    public List<Leaves> getPastLeaves(@RequestParam String employeeEmail, @RequestParam Date endDate) {
        List<Leaves> leaves = leaveManagementService.getPastLeaves(employeeEmail, endDate);
        System.out.println("past leaves :"+leaves.size());
        return leaves;
    }

    @GetMapping("/getUpcomingLeave")
    public List<Leaves> getUpcomingLeave(@RequestParam String employeeEmail, @RequestParam Date startDate) {
        List<Leaves> leaves = leaveManagementService.getUpcomingLeave(employeeEmail, startDate);
        System.out.println("past leaves :"+leaves.size());
        return leaves;
    }

    @GetMapping("/getLeavesStatusCount")
    public List<Leaves> getLeavesStatusCount(@RequestParam String employeeEmail){
        List<Leaves> leaves = leaveManagementService.findLeavesByEmail(employeeEmail);
        System.out.println("leaves : "+leaves);
        return leaves;
    }

}
