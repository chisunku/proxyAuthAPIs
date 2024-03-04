package com.sjsu.proxyAuth.Repository;
import com.sjsu.proxyAuth.model.Leaves;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LeavesRepo extends MongoRepository<Leaves, ObjectId> {

    List<Leaves> findByApprovalStatus(String status);

}
