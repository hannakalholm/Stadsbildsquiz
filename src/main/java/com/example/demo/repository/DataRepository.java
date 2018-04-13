package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataRepository {

    @Autowired
    private DataSource dataSource;

    public List<String> getAllPictures(String city) {
        List<String> allPictures = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT URL FROM dbo.Picture WHERE City_name = '" + city + "' ORDER BY Points DESC")) {
            while (rs.next()) {
                allPictures.add(rs.getString("URL"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPictures;
    }

    public List<String> getAllCities() {
        List<String> res = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Cityname FROM dbo.City ORDER BY Cityname")) {
            while (rs.next()) {
                res.add(rs.getString("Cityname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}