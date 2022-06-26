package com.example.covid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CovidApplication {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            SpringApplication.run(CovidApplication.class, args);

        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("Driver unloaded");
            classNotFoundException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
