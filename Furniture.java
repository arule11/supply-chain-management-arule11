package edu.ucalgary.ensf409;

import java.sql.*;

public class Furniture{
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;
    private Connection dbConnect;
    private ResultSet results;
    private int price;


    public Furniture(String dburl, String username, String password){
        DBURL = dburl;
        USERNAME = username;
        PASSWORD = password;
    }

    public void initializeConnection(){
        try{
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/inventory", USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace(); //Or any error message
        }
    }

    public String priceToString(){
        return "$" + price;
    }

    public int getPrice(){
        return this.price;
    }
}   
