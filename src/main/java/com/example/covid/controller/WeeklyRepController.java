package com.example.covid.controller;

import com.example.covid.module.WeeklyRep;
import com.example.covid.util.DBConnector;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WeeklyRepController {
    public static final ZoneId DEFAULT_TIME_ZONE = ZoneId.systemDefault();

    @GetMapping("/weeklyRep")
    public ArrayList<WeeklyRep> getWeeklyRep() throws Exception {
        try {
            ArrayList<WeeklyRep> result = new ArrayList<>();

            Connection connection = DBConnector.getConnection();
            Statement statement = connection.createStatement();
            String sql = new String(Files.readAllBytes(Paths.get("C:/Users/25benjaminh/IdeaProjects/covid/src/main/java/com/example/covid/sql/weeklyRep/getAll.sql")), StandardCharsets.UTF_8);

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                WeeklyRep wr = new WeeklyRep();
                wr.setId(resultSet.getInt("Id"));
                wr.setDataRep(resultSet.getString("dataRep"));
                wr.setYearWeek(resultSet.getString("yearWeek"));
                wr.setCaseWeekly(resultSet.getInt("casesWeekly"));
                wr.setDeathsWeekly(resultSet.getInt("deathsWeekly"));
                wr.setCountryAndTerritory(resultSet.getString("countryAndTerritory"));
                wr.setGeoId(resultSet.getString("geoId"));
                wr.setCountryTerritoryCode(resultSet.getString("countryTerritoryCode"));
                wr.setGeoId(resultSet.getString("geoId"));
                wr.setContinentExp(resultSet.getString("continentExp"));
                result.add(wr);
            }
            return result;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    @GetMapping("/weeklyRep/{country}")
    public ArrayList<WeeklyRep> getWeeklyRepByCountry(@PathVariable("country") String country, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) throws Exception {
        ArrayList<WeeklyRep> result = new ArrayList<>();
        Connection connection = DBConnector.getConnection();
        Statement statement = connection.createStatement();
        String sql = new String(Files.readAllBytes(Paths.get("C:/Users/25benjaminh/IdeaProjects/covid/src/main/java/com/example/covid/sql/weeklyRep/getFromCountry.sql")), StandardCharsets.UTF_8);

        sql = String.format(sql,
                "`weeklyrep`.`id`,\n" +
                        "    date_format(str_to_date(dataRep,'%d/%m/%Y'), '%Y/%m/%d') as dataRep,\n" +
                        "    `weeklyrep`.`casesWeekly`,\n" +
                        "    `weeklyrep`.`deathsWeekly`,\n" +
                        "    `weeklyrep`.`countryAndTerritory`,\n" +
                        "    `weeklyrep`.`geoId`,\n" +
                        "    `weeklyrep`.`countryTerritoryCode`,\n" +
                        "    `weeklyrep`.`continentExp`",
                country.equals("all") ? " 1=1" : "countryAndTerritory = '" + country + "'",
                startTime != null ? "str_to_date(dataRep,'%d/%m/%Y') > str_to_date('" + startTime + "','%Y-%m-%d')" : " 1=1",
                endTime != null ? "str_to_date(dataRep,'%d/%m/%Y') < str_to_date('" + endTime + "','%Y-%m-%d')" : " 1=1");

        sql = sql + " order by  str_to_date(dataRep,'%d/%m/%Y');";

        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            WeeklyRep wr = new WeeklyRep();
            wr.setId(resultSet.getInt("Id"));
            wr.setDataRep(resultSet.getString("dataRep"));
            wr.setCaseWeekly(resultSet.getInt("casesWeekly"));
            wr.setDeathsWeekly(resultSet.getInt("deathsWeekly"));
            wr.setCountryAndTerritory(resultSet.getString("countryAndTerritory"));
            wr.setGeoId(resultSet.getString("geoId"));
            wr.setCountryTerritoryCode(resultSet.getString("countryTerritoryCode"));
            wr.setGeoId(resultSet.getString("geoId"));
            wr.setContinentExp(resultSet.getString("continentExp"));
            result.add(wr);
        }
        return result;
    }

    @GetMapping("weeklyRep/{country}/totalCase")
    public int getTotalCaseByCountry(@PathVariable("country") String country, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) throws Exception {
        ArrayList<WeeklyRep> arr = getWeeklyRepByCountry(country, startTime, endTime);
        int result = 0;
        for (WeeklyRep wr : arr) {
            result += wr.getCaseWeekly();
        }
        return result;
    }

    @GetMapping("weeklyRep/{country}/avgCase")
    public double getAverageCaseByCountry(@PathVariable("country") String country, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) throws Exception {
        ArrayList<WeeklyRep> arr = getWeeklyRepByCountry(country, startTime, endTime);
        if (arr.size() < 1) {
            return 0;
        }

        Date minDate = new SimpleDateFormat("yyyy/MM/dd").parse(arr.get(0).getDataRep());
        Date maxDate = new SimpleDateFormat("yyyy/MM/dd").parse(arr.get(arr.size() - 1).getDataRep());
        LocalDate localMinDate = minDate.toInstant().atZone(DEFAULT_TIME_ZONE).toLocalDate();
        LocalDate localMaxDate = maxDate.toInstant().atZone(DEFAULT_TIME_ZONE).toLocalDate();
        long daysBetween = DAYS.between(localMinDate, localMaxDate);
        Long longResult = getTotalCaseByCountry(country, startTime, endTime) / daysBetween;
        double result = Math.round(longResult.doubleValue() * 100.0) / 100.0;
        return result;
    }

    @GetMapping("weeklyRep/{country}/totalDeaths")
    public int getTotalDeathsByCountry(@PathVariable("country") String country, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) throws Exception {
        ArrayList<WeeklyRep> arr = getWeeklyRepByCountry(country, startTime, endTime);
        int result = 0;
        for (WeeklyRep wr : arr) {
            result += wr.getDeathsWeekly();
        }
        return result;
    }

    @GetMapping("weeklyRep/sort")
    public ArrayList<WeeklyRep> sortWeeklyRep() throws Exception {
        try {
            ArrayList<WeeklyRep> result = getWeeklyRep();
            for (int i = 0; i < result.size(); i++) {
                int minPos = i;
                int min = result.get(minPos).getId();
                for (int j = i + 1; j < result.size(); j++) {
                    if (result.get(j).getId() < min) {
                        minPos = j;
                        min = result.get(minPos).getId();
                        System.out.println(min);
                    }
                }
                WeeklyRep tmp = result.get(i);
                result.set(i, result.get(minPos));
                result.set(minPos, tmp);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @GetMapping("/weeklyRep/insert")
    public ArrayList<WeeklyRep> insertData(
            @RequestParam(required = true) String dataRep,
            @RequestParam(required = true) String casesWeekly,
            @RequestParam(required = true) String deathsWeekly,
            @RequestParam(required = true) String countryAndTerritory,
            @RequestParam(required = true) String geoId,
            @RequestParam(required = true) String countryAndTerritoryCode,
            @RequestParam(required = true) String continentExp) throws Exception {
        try {
            ArrayList<WeeklyRep> result = new ArrayList<>();

            Connection connection = DBConnector.getConnection();
            Statement statement = connection.createStatement();
            String sql = new String(Files.readAllBytes(Paths.get("C:/Users/25benjaminh/IdeaProjects/covid/src/main/java/com/example/covid/sql/weeklyRep/insert.sql")), StandardCharsets.UTF_8);

            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(dataRep);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dataRep = dateFormat.format(date);

            sql = String.format(sql, dataRep, casesWeekly, deathsWeekly, countryAndTerritory, geoId, countryAndTerritoryCode, continentExp);
            System.out.println(sql);


            statement.execute(sql);
//
            return result;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }
}