package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisMain {

    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;

    private static JedisPool pool = null;

    public JedisMain() {
        pool = new JedisPool(redisHost, redisPort);
    }   

    public String getFromCache(String key) {
        try {
            Jedis jedis = pool.getResource();
            String value = null;
            if (jedis.get(key) == null || jedis.get(key).isEmpty()) {
                System.out.println("Cache doesn't have the data, Querying DB");
                String query = 
                "select session_id from employees.session_data where user_id='" + key + "'";
                value = getResultsFromMySQL(query);
                System.out.println("SQL Result" + value);
                jedis.set(key, value);
            } else {
                System.out.println("Fetching Value From Cache for key - " +key);
                value = jedis.get(key);
            }
            System.out.println("Value for Key "+key + " is : " +value);
            return value;
        } catch (Exception e) {
            System.out.println("Error in getFromCache" + e.getMessage());
        }
        return null;
    }

    public static String getResultsFromMySQL(String query) {

        String url = "jdbc:mysql://<CLOUDSQL IP>/<DATABASE>";
        try (Connection conn = DriverManager.getConnection(url, "<user>", "<Password>");
                Statement stmt = conn.createStatement();) {
            String value = null;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                value = rs.getString("session_id");
                System.out.print("session_id: " + value);
            }
            rs.close();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        JedisMain main = new JedisMain();
        main.getFromCache("XYZ");
    }
}