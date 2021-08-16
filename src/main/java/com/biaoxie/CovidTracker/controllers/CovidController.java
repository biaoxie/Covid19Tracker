package com.biaoxie.CovidTracker.controllers;

import com.biaoxie.CovidTracker.models.StateEntity;
import com.biaoxie.CovidTracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CovidController {

    @Autowired
    private CovidDataService covidDataService;

    @GetMapping("/{fips}")
    public StateEntity findStateByFips(@PathVariable String fips) {
        return covidDataService.findStateByFips(Integer.parseInt(fips));
    }

}
