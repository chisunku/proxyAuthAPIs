package com.sjsu.proxyAuth.Controllers;

import com.sjsu.proxyAuth.Service.AdminLeaveManagementService;
import com.sjsu.proxyAuth.Service.LeaveManagementService;
import com.sjsu.proxyAuth.model.Leaves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class AdminLeaveManagementController {

    @Autowired
    AdminLeaveManagementService adminLeaveManagementService;

    /**
     * This API is for admin for approving a specific leave request
     * @param id
     * @throws Exception
     */
    @PostMapping("/leave/approve/{id}")
    public String approveLeave(@PathVariable String id, @RequestParam String approvedBy) throws Exception {
        try {
            adminLeaveManagementService.approveLeave(id, approvedBy);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * This API is for admin for rejecting a specific leave request
     * @param id
     * @throws Exception
     */
    @PostMapping("/leave/reject/{id}")
    public String rejectLeave(@PathVariable String id, @RequestParam String rejectReason, @RequestParam String approvedBy) throws Exception {
        try{
            adminLeaveManagementService.rejectLeave(id, rejectReason, approvedBy);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * This API is for admin for getting all Leave-requests for all employees by status(Approved/Pending/Reject)
     * @param status
     * @return
     */
    @GetMapping("/leave/{status}")
    public List<Leaves> getAllLeavesByStatus(@PathVariable String status) {
        return adminLeaveManagementService.getLeavesByStatus(status);
    }

    /**
     * This API is for admin for getting all Leave-requests for all employees for all statuses
     * @return
     */
    @GetMapping("/leave")
    public List<Leaves> getAllLeaves() {
        return adminLeaveManagementService.getAllLeaves();
    }

    @GetMapping("/leave/notApproved")
    public List<Leaves> getNotApprovedLeaves(@RequestParam Date startDate) {
        return adminLeaveManagementService.getNotApprovedLeaves(startDate);
    }

    @GetMapping("/leave/getAllAfterStartDate")
    public List<Leaves> getAllLeavesAfterStartDate(@RequestParam Date startDate) {
        List<Leaves> leaves = adminLeaveManagementService.getAllLeavesAfterStartDate(startDate);
        System.out.println("Leaves after start date :"+leaves.size()+" start date : "+startDate);
        return leaves;
    }
}
