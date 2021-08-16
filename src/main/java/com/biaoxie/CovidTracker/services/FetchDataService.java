package com.biaoxie.CovidTracker.services;

import com.biaoxie.CovidTracker.data.CovidRepository;
import com.biaoxie.CovidTracker.models.CaseInfo;
import com.biaoxie.CovidTracker.models.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FetchDataService {
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent" +
            ".com/nytimes/covid-19-data/master/us-states.csv";

    private List<StateEntity> allStates;
    private Map<Integer, StateEntity> stateMap;

    @Autowired
    private CovidRepository covidRepository;

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException, ParseException {
        stateMap = new TreeMap<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder(URI.create(VIRUS_DATA_URL)).build();

        HttpResponse<String> httpResponse = client.send(request,
                HttpResponse.BodyHandlers.ofString());


        // Iterate through the information
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {

            CaseInfo caseInfo = new CaseInfo();
            caseInfo.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(record.get("date")));
            caseInfo.setCases(Integer.parseInt(record.get("cases")));
            caseInfo.setDeaths(Integer.parseInt(record.get("deaths")));

            int fips = Integer.parseInt(record.get("fips"));

            if (!stateMap.containsKey(fips)) {
                StateEntity stateEntity = new StateEntity();

                stateEntity.setFips(fips);
                stateEntity.setStateName(record.get("state"));
                stateEntity.setCaseInfos(new ArrayList<>());

                stateMap.put(fips, stateEntity);
            }

            stateMap.get(fips).getCaseInfos().add(caseInfo);

        }

        for (int fips: stateMap.keySet()) {
            StateEntity stateEntity = stateMap.get(fips);


            int numOfCases = stateEntity.getCaseInfos().size();
            int totalCases = stateEntity.getCaseInfos().get(numOfCases-1).getCases();
            int totalDeaths = stateEntity.getCaseInfos().get(numOfCases-1).getDeaths();

            stateEntity.setTotalCases(totalCases);
            stateEntity.setTotalDeaths(totalDeaths);

            covidRepository.save(stateMap.get(fips));
        }



    }



}
