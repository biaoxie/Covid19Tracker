package com.biaoxie.CovidTracker.services;

import com.biaoxie.CovidTracker.data.CovidRepository;
import com.biaoxie.CovidTracker.models.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class CovidDataService {

    @Autowired
    private CovidRepository covidRepository;

    public StateEntity findStateByFips(int fips) {
        return covidRepository.findByFips(fips);
    }

    public List<StateEntity> findAllStates() {
        return covidRepository.findAll();
    }

}
