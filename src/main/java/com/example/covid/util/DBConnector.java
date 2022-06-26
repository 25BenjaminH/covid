package com.example.covid.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public static Connection getConnection() throws SQLException {
        String serverName = "localhost";
        String database = "COVID";
        String url = "jdbc:mysql://" + serverName + "/" + database;
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
