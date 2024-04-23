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

    @Query("{'approvalStatus':?0, 'startDate':{ $gte: ?1 }}")
    List<Leaves> findByApprovalStatusAndStartDate(String status, Date startDate);

    List<Leaves> findByEmail(String email);

    List<Leaves> findByEmailAndApprovalStatus(String email, String status);

    @Query("{'email': ?0, 'endDate': { $lte: ?1 }}")
    //find past leaves
    List<Leaves> findByEmailAndEndDateBefore(String email, Date endDate);

    @Query("{'email': ?0, 'startDate': { $gte: ?1 }}")
    List<Leaves> findByEmailAndGreaterThanEqualStartDate(String email, Date startDate);

    //get leaves by email and group leaves by status along with count.
    @Query("{$group: {_id: '$status', count: {$sum: 1}, leaves: {$push: '$$ROOT'}}}")
    List<Map<String, Object>> findByEmailAndGroupByStatus(String email);

    @Query("{'startDate': { $gte: ?0 }}")
    List<Leaves> findByStartDate(Date startDate);

}
