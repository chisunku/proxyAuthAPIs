package com.sjsu.proxyAuth.Service;

import com.sjsu.proxyAuth.Repository.LeavesRepo;
import com.sjsu.proxyAuth.model.Leaves;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class LeaveManagementService {

    @Autowired
    LeavesRepo leavesRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

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

    public List<Leaves> getPastLeaves(String employeeEmail, Date endDate) {
        List<Leaves> leavesList = leavesRepo.findByEmailAndEndDateBefore(employeeEmail,endDate);
        return leavesList;
    }

    public List<Leaves> getUpcomingLeave(String employeeEmail, Date startDate) {
        List<Leaves> leavesList = leavesRepo.findByEmailAndStartDateAfter(employeeEmail,startDate);
        return leavesList;
    }

    public List<Map<String, Object>> findByEmailAndGroupByStatus(String email) {
        List<Map<String, Object>> leaves = leavesRepo.findByEmailAndGroupByStatus(email);
        System.out.println("leaves : "+leaves);
        return leaves;
    }

    public List<Leaves> findLeavesByEmail(String email) {

        List<Leaves> leaves = mongoTemplate.findAll(Leaves.class, "Leaves");

        for (Leaves leave : leaves) {
            System.out.println("Approval status: " + leave.getApprovalStatus());
        }

        MatchOperation match = Aggregation.match(Criteria.where("email").is(email));
        GroupOperation group = Aggregation.group("approvalStatus").count().as("count");
        ProjectionOperation project = Aggregation.project( "_id", "count").and("_id").as("approvalStatus");
        Aggregation aggregation = Aggregation.newAggregation(match, group, project);
        AggregationResults<Leaves> results = mongoTemplate.aggregate(aggregation, "Leaves", Leaves.class);
        System.out.println("Results : "+results.getMappedResults().size());
        return results.getMappedResults();
    }

    class LeavesStatusCount{
        @Getter@Setter
        private String status;
        @Getter@Setter
        private int count;
    }
}
