package com.sjsu.proxyAuth.Repository;
import com.sjsu.proxyAuth.model.Leaves;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LeavesRepo extends MongoRepository<Leaves, ObjectId> {

    List<Leaves> findByApprovalStatus(String status);

    List<Leaves> findByEmail(String email);

    List<Leaves> findByEmailAndApprovalStatus(String email, String status);

    //find past leaves
    List<Leaves> findByEmailAndEndDateBefore(String email, Date endDate);

    List<Leaves> findByEmailAndStartDateAfter(String email, Date startDate);

    //get leaves by email and group leaves by status along with count.
    @Query("{$group: {_id: '$status', count: {$sum: 1}, leaves: {$push: '$$ROOT'}}}")
    List<Map<String, Object>> findByEmailAndGroupByStatus(String email);

}
