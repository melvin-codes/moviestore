package com.aca.moviestore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

public class MariaDbUtil {

    private static String connectionUrl = "jdbc:mariadb://localhost:3306/mymovies?user=root&password=code";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection connection = MariaDbUtil.getConnection();
        if(null != connection) {
            System.out.println("A real connection");

            try {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet rs = metaData.getTables(null, null, "%", null);
                while(rs.next()) {
                    System.out.println(rs.getString("table_name"));
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Help. Connection is null");
        }
    }
}
