package com.biaoxie.CovidTracker.data;

import com.biaoxie.CovidTracker.models.StateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CovidRepository extends MongoRepository<StateEntity, String> {
    StateEntity findByFips(int fips);

}
