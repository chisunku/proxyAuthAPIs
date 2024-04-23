package com.sjsu.proxyAuth.Service;

import com.sjsu.proxyAuth.Repository.LeavesRepo;
import com.sjsu.proxyAuth.model.Leaves;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminLeaveManagementService {

    @Autowired
    LeavesRepo leavesRepo;

    /**
     * This method approve a leave for employee
     * @param id
     */
    public void approveLeave(String id, String approvedBy) {
        Optional<Leaves> leavesOptional = leavesRepo.findById(new ObjectId(id));
        leavesOptional.ifPresentOrElse(leaves -> {
                    leaves.setApprovalStatus("Approved");
                    leaves.setApprovalManager(approvedBy);
                    leavesRepo.save(leaves);
                },
                () -> {
                    log.error("Document with id not Found: " + id);
                    throw new RuntimeException("Leave Application Not Found");
                }
        );
    }

    /**
     * This method gets All leaves of all employees by status
     * @param status
     * @return
     */
    public List<Leaves> getLeavesByStatus(String status) {
        List<Leaves> leavesList = leavesRepo.findByApprovalStatus(status);
        return  leavesList;
    }

    /**
     * This method gets All leaves of all employees
     * @return
     */
    public List<Leaves> getAllLeaves() {
        List<Leaves> leavesList = leavesRepo.findAll();
        return  leavesList;
    }

    /**
     * his method reject a leave for employee
     * @param id
     */
    public void rejectLeave(String id, String rejectReason, String approvedBy) {
        Optional<Leaves> leavesOptional = leavesRepo.findById(new ObjectId(id));
        leavesOptional.ifPresentOrElse(leaves -> {
                    leaves.setApprovalStatus("Rejected");
                    leaves.setRejectReason(rejectReason);
                    leaves.setApprovalManager(approvedBy);
                    leavesRepo.save(leaves);
                },
                () -> {
                    log.error("Document with id not Found: " + id);
                    throw new RuntimeException("Leave Application Not Found");
                }
        );
    }

    public List<Leaves> getNotApprovedLeaves(Date date){
        return leavesRepo.findByApprovalStatusAndStartDate("Pending", date);
    }

    public List<Leaves> getAllLeavesAfterStartDate(Date date){
        return leavesRepo.findByStartDate(date);
    }

}
