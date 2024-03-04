package com.sjsu.proxyAuth.Service;

import com.sjsu.proxyAuth.Repository.LeavesRepo;
import com.sjsu.proxyAuth.model.Leaves;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LeaveManagementService {

    @Autowired
    LeavesRepo leavesRepo;

    /**
     * This method stores the applied leaves in DB. Initial status is set as Pending;
     * @param leaves
     */
    public void applyLeave(Leaves leaves){
        leavesRepo.save(leaves);
    }

    /**
     * This method updates an existing leave request. It first fetches if a particular
     * leave request is present in DB and ability to update specific fields
     * @param updatedLeave
     */
    public void updateLeave(Leaves updatedLeave) {
        Optional<Leaves> leavesOptional = leavesRepo.findById(new ObjectId(updatedLeave.getId()));
        leavesOptional.ifPresentOrElse(leaves -> {
                    if (leaves.getApprovalStatus().equals("Approved"))
                    {
                        throw new RuntimeException("Leaves cannot be updated once approved");
                    }
                    leaves.setLeaveType(updatedLeave.getLeaveType());
                    leaves.setLeaveReason(updatedLeave.getLeaveReason());
                    leaves.setLeaveSubType(updatedLeave.getLeaveSubType());
                    leaves.setContactNumber(updatedLeave.getContactNumber());
                    leaves.setStartDate(updatedLeave.getStartDate());
                    leaves.setEndDate(updatedLeave.getEndDate());
                    leavesRepo.save(leaves);
                },
                () -> {
                    log.error("Document with id not Found: " + updatedLeave.getId());
                    throw new RuntimeException("Leave Application Not Found");
                }
        );
    }

    public List<Leaves> getAllLeavesforEmployee(String employeeEmail) {
        List<Leaves> leavesList = leavesRepo.findByEmail(employeeEmail);
        return leavesList;

    }

    public List<Leaves> getAllLeavesforEmployeeByStatus(String employeeEmail, String approvalStatus) {
        List<Leaves> leavesList = leavesRepo.findByEmailAndApprovalStatus(employeeEmail,approvalStatus);
        return leavesList;
    }
}
