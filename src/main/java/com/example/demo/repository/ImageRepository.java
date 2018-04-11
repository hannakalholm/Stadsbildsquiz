package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImageRepository {

    @Autowired
    private DataSource dataSource;

    public String getAllPictures() {
        String myString = "";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT URL FROM dbo.Picture WHERE PictureID = 1")) {
            while (rs.next()) {
                myString = rs.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myString;

    }
}
