package com.sjsu.proxyAuth.Controllers;

import com.sjsu.proxyAuth.Repository.LeavesRepo;
import com.sjsu.proxyAuth.Service.LeaveManagementService;
import com.sjsu.proxyAuth.model.Leaves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void applyLeave(@RequestBody  Leaves leaves) throws Exception {
        leaveManagementService.applyLeave(leaves);
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

}
