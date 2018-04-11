package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataRepository {

    @Autowired
    private DataSource dataSource;

    public String getAllPictures() {
        String myString = "";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT URL FROM dbo.Picture WHERE PictureID = 1")) {
            while (rs.next()) {
                myString = rs.getString("URL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(myString);
        return myString;

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
