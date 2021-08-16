package com.biaoxie.CovidTracker.models;


import java.util.Date;

public class CaseInfo {

    private Date date;
    private int cases;
    private int deaths;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }


}
