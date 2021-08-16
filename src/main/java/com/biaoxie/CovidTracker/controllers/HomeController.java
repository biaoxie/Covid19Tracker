package com.biaoxie.CovidTracker.controllers;

import com.biaoxie.CovidTracker.data.CovidRepository;
import com.biaoxie.CovidTracker.models.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
}
