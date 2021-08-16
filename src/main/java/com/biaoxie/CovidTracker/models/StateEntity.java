package com.biaoxie.CovidTracker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


import java.util.List;
import java.util.Objects;


public class StateEntity {

    private String stateName;
    @Id
    private int fips;
    private int totalCases;
    private int totalDeaths;
    private List<CaseInfo> caseInfos;

    public StateEntity() {
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


    public int getFips() {
        return fips;
    }

    public void setFips(int fips) {
        this.fips = fips;
    }


    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public List<CaseInfo> getCaseInfos() {
        return caseInfos;
    }

    public void setCaseInfos(List<CaseInfo> caseInfos) {
        this.caseInfos = caseInfos;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateEntity that = (StateEntity) o;
        return fips == that.fips;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fips);
    }
}
