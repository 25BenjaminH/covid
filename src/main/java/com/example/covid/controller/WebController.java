package com.example.covid.controller;

import com.example.covid.module.WeeklyRep;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class WebController {
    public WebController() {
    }

    @RequestMapping("/")
    public String doPost(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArrayList<WeeklyRep> arr = new ArrayList<>();
        int totalCases = 0;
        double avgCases = 0;
        int totalDeaths = 0;
        WeeklyRepController w = new WeeklyRepController();
        if (request.getParameter("button") != null) {
            try {
                // check input value is not null, set value to API ------ DONE
                if (request.getParameter("country") != "") {
                    if (request.getParameter("startTime") != "") {
                        if (request.getParameter("endTime") != "") {
                            arr = w.getWeeklyRepByCountry(request.getParameter("country"), request.getParameter("startTime"), request.getParameter("endTime"));
                            totalCases = w.getTotalCaseByCountry(request.getParameter("country"), request.getParameter("startTime"), request.getParameter("endTime"));
                            totalDeaths = w.getTotalDeathsByCountry(request.getParameter("country"), request.getParameter("startTime"), request.getParameter("endTime"));
                            avgCases = w.getAverageCaseByCountry(request.getParameter("country"), request.getParameter("startTime"), request.getParameter("endTime"));
                        } else {
                            arr = w.getWeeklyRepByCountry(request.getParameter("country"), request.getParameter("startTime"), null);
                            totalCases = w.getTotalCaseByCountry(request.getParameter("country"), request.getParameter("startTime"), null);
                            avgCases = w.getAverageCaseByCountry(request.getParameter("country"), request.getParameter("startTime"), null);
                            totalDeaths = w.getTotalDeathsByCountry(request.getParameter("country"), request.getParameter("startTime"), null);
                        }
                    } else if (request.getParameter("endTime") != "") {
                        arr = w.getWeeklyRepByCountry(request.getParameter("country"), null, request.getParameter("endTime"));
                        totalDeaths = w.getTotalDeathsByCountry(request.getParameter("country"), null, request.getParameter("endTime"));
                        totalCases = w.getTotalCaseByCountry(request.getParameter("country"), null, request.getParameter("endTime"));
                        avgCases = w.getAverageCaseByCountry(request.getParameter("country"), null, request.getParameter("endTime"));
                    } else {
                        arr = w.getWeeklyRepByCountry(request.getParameter("country"), null, null);
                        totalCases = w.getTotalCaseByCountry(request.getParameter("country"), null, null);
                        totalDeaths = w.getTotalDeathsByCountry(request.getParameter("country"), null, null);
                        avgCases = w.getAverageCaseByCountry(request.getParameter("country"), null, null);
                    }
                } else {
                    if (request.getParameter("startTime") != "") {
                        if (request.getParameter("endTime") != "") {
                            arr = w.getWeeklyRepByCountry("all", request.getParameter("startTime"), request.getParameter("endTime"));
                            totalCases = w.getTotalCaseByCountry("all", request.getParameter("startTime"), request.getParameter("endTime"));
                            totalDeaths = w.getTotalDeathsByCountry("all", request.getParameter("startTime"), request.getParameter("endTime"));
                            avgCases = w.getAverageCaseByCountry("all", request.getParameter("startTime"), request.getParameter("endTime"));
                        } else {
                            arr = w.getWeeklyRepByCountry("all", request.getParameter("startTime"), null);
                            totalCases = w.getTotalCaseByCountry("all", request.getParameter("startTime"), null);
                            avgCases = w.getAverageCaseByCountry("all", request.getParameter("startTime"), null);
                            totalDeaths = w.getTotalDeathsByCountry("all", request.getParameter("startTime"), null);
                        }
                    } else if (request.getParameter("endTime") != "") {
                        arr = w.getWeeklyRepByCountry("all", null, request.getParameter("endTime"));
                        totalDeaths = w.getTotalDeathsByCountry("all", null, request.getParameter("endTime"));
                        totalCases = w.getTotalCaseByCountry("all", null, request.getParameter("endTime"));
                        avgCases = w.getAverageCaseByCountry("all", null, request.getParameter("endTime"));
                    } else {
                        arr = w.getWeeklyRepByCountry("all", null, null);
                        totalCases = w.getTotalCaseByCountry("all", null, null);
                        totalDeaths = w.getTotalDeathsByCountry("all", null, null);
                        avgCases = w.getAverageCaseByCountry("all", null, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//            arr = w.getWeeklyRep();
//            totalCases = w.getTotalCaseByCountry(request.getParameter("country"));

        //set value to mini board(total case....)

        model.addAttribute("weeklyRepList", arr);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("avgCases", avgCases);
        model.addAttribute("totalDeaths", totalDeaths);
        return "covid19Data";
    }

    @RequestMapping("/insert")
    public String insert(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ArrayList<WeeklyRep> arr = new ArrayList<>();
        int totalCases = 0;
        double avgCases = 0;
        int totalDeaths = 0;
        String dataRep = request.getParameter("dataRep"); //2020/01/06
        String caseWeekly = request.getParameter("caseWeekly");
        String deathsWeekly = request.getParameter("deathsWeekly");
        String countryAndTerritory = request.getParameter("countryAndTerritory");
        String geoId = request.getParameter("geoId");
        String countryAndTerritoryCode = request.getParameter("countryAndTerritoryCode");
        String continentEXP = request.getParameter("continentEXP");

        WeeklyRepController w = new WeeklyRepController();
        if (request.getParameter("button") != null) {
            try {
                Date date = new SimpleDateFormat("yyyy/MM/dd").parse(dataRep);
                w.insertData(dataRep, caseWeekly, deathsWeekly, countryAndTerritory, geoId, countryAndTerritoryCode, continentEXP);
                arr = w.getWeeklyRepByCountry("all",null,null);
                totalCases = w.getTotalCaseByCountry("all",null,null);
                avgCases = w.getAverageCaseByCountry("all",null,null);
                totalDeaths = w.getTotalDeathsByCountry("all",null,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //set value to mini board(total case....)

        model.addAttribute("weeklyRepList", arr);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("avgCases", avgCases);
        model.addAttribute("totalDeaths", totalDeaths);

        return "covid19Data";
    }





}
