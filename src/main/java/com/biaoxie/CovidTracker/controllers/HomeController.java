package com.biaoxie.CovidTracker.controllers;

import com.biaoxie.CovidTracker.data.CovidRepository;
import com.biaoxie.CovidTracker.models.CaseInfo;
import com.biaoxie.CovidTracker.models.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class HomeController {
    @Autowired
    private CovidRepository covidRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<StateEntity> allStates = covidRepository.findAll();

        int cases = allStates.stream().mapToInt(s -> s.getTotalCases()).sum();

        model.addAttribute("totalCases", cases);
        model.addAttribute("allStates", allStates);

        return "home";
    }

    @GetMapping("/{fips}/latest")
    public String statePage(Model model, @PathVariable String fips) {

        StateEntity stateEntity = null;
        if (Pattern.matches("[0-9]+", fips)) {
            stateEntity = covidRepository.findByFips(Integer.parseInt(fips));
        }

        if (stateEntity != null) {
            model.addAttribute("stateName", stateEntity.getStateName());
            List<CaseInfo> caseInfos = stateEntity.getCaseInfos();
            caseInfos = caseInfos.subList(caseInfos.size()-11, caseInfos.size());
            model.addAttribute("totalCases", caseInfos.get(caseInfos.size()-1).getCases());

            List<CaseInfo> newCases = new ArrayList<>(caseInfos);
            for(int i = newCases.size() - 1; i > 0; i--) {
                CaseInfo newCase = new CaseInfo();
                CaseInfo current = newCases.get(i);
                CaseInfo past = newCases.get(i-1);
                newCase.setCases(current.getCases()-past.getCases());
                newCase.setDeaths(current.getDeaths()-past.getDeaths());
                newCases.set(i, newCase);
            }

            newCases.remove(0);
            caseInfos.remove(0);

            model.addAttribute("caseInfos", caseInfos);
            model.addAttribute("newCases", newCases);





        }



        return "state";
    }
}
