package com.sjsu.proxyAuth.Controllers;

import com.sjsu.proxyAuth.Service.AdminLeaveManagementService;
import com.sjsu.proxyAuth.Service.LeaveManagementService;
import com.sjsu.proxyAuth.model.Leaves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void approveLeave(@PathVariable String id) throws Exception {
        adminLeaveManagementService.approveLeave(id);
    }

    /**
     * This API is for admin for rejecting a specific leave request
     * @param id
     * @throws Exception
     */
    @PostMapping("/leave/reject/{id}")
    public void rejectLeave(@PathVariable String id) throws Exception {
        adminLeaveManagementService.rejectLeave(id);
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
}
