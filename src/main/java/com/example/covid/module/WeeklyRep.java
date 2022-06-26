package com.example.covid.module;

public class WeeklyRep {
    private int id;
    private String dataRep;
    private String yearWeek;
    private int caseWeekly;
    private int deathsWeekly;
    private String countryAndTerritory;
    private String geoId;
    private String countryTerritoryCode;
    private String continentExp;

    public String getContinentExp() {
        return continentExp;
    }

    public void setContinentExp(String continentExp) {
        this.continentExp = continentExp;
    }

    public WeeklyRep() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataRep() {
        return dataRep;
    }

    public void setDataRep(String dateRep) {
        this.dataRep = dateRep;
    }

    public String getYearWeek() {
        return yearWeek;
    }

    public void setYearWeek(String yearWeek) {
        this.yearWeek = yearWeek;
    }

    public int getCaseWeekly() {
        return caseWeekly;
    }

    public void setCaseWeekly(int caseWeekly) {
        this.caseWeekly = caseWeekly;
    }

    public int getDeathsWeekly() {
        return deathsWeekly;
    }

    public void setDeathsWeekly(int deathsWeekly) {
        this.deathsWeekly = deathsWeekly;
    }

    public String getCountryAndTerritory() {
        return countryAndTerritory;
    }

    public void setCountryAndTerritory(String countryAndTerritory) {
        this.countryAndTerritory = countryAndTerritory;
    }

    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    public String getCountryTerritoryCode() {
        return countryTerritoryCode;
    }

    public void setCountryTerritoryCode(String countryTerritoryCode) {
        this.countryTerritoryCode = countryTerritoryCode;
    }
}
