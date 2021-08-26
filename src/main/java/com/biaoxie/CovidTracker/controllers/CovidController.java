package com.biaoxie.CovidTracker.controllers;

import com.biaoxie.CovidTracker.models.CaseInfo;
import com.biaoxie.CovidTracker.models.StateEntity;
import com.biaoxie.CovidTracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CovidController {

    @Autowired
    private CovidDataService covidDataService;

    @GetMapping("/{fips}")
    public List<CaseInfo> findLatestStateInfoByFips(@PathVariable String fips) {
        StateEntity stateEntity = covidDataService.findStateByFips(Integer.parseInt(fips));
        return stateEntity.getCaseInfos().subList(stateEntity.getCaseInfos().size()-10, stateEntity.getCaseInfos().size());
    }


}
