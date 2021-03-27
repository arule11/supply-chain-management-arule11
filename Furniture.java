package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.*;

public class Furniture{
    
    public final String DBURL; //store the database url information
    public final String USERNAME; //store the user's account username
    public final String PASSWORD; //store the user's account password
    
    private Connection dbConnect;
    private ResultSet results;
    private int price;

    public Furniture(String url, String username, String pswd){
        this.DBURL = url;
        this.USERNAME = username;
        this.PASSWORD = pswd;
      }
    
    public boolean initializeConnection(){
          try{
            dbConnect = DriverManager.getConnection(getDburl(), getUsername(), getPassword());
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return true;
      }
      
      /**
      * Retrieves the database URL
      * @return Returns a String
      */
    public String getDburl(){
        return this.DBURL;
      }
    
      /**
      * Retrieves the MySQL username
      * @return Returns a String
      */
    public String getUsername(){
    return this.USERNAME;
      }
    
      /**
      * Retrieves the MySQL password
      * @return Returns a String
      */
    public String getPassword(){
    return this.PASSWORD;
      }
    
    public void deleteItem(String ID, String item){
        try{
            // Creates a statement for the delete instruciton
            String query = "DELETE FROM ? WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            
            myStmt.setString(1,item);
            myStmt.setString(2,ID);
            myStmt.executeUpdate();            
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void checkChair(String type){
      try{
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM CHAIR WHERE type = '" + type + "'");
    
        ArrayList<String> chairs = new ArrayList<String>();
    
        while (results.next()){
          // if(results.getString("Legs").equals("Y")){
          // results.getString("ID");
          // }
    
        }
        StringBuffer row = new StringBuffer();
        row.append(results.getString("ID") + "=" + results.getString("Legs") + "," + results.getString("Arms") + "," + results.getString("Seat") + "," + results.getString("Cushion"));
    
    
      }catch (SQLException ex){
        ex.printStackTrace();
      }
    }
    
     public void checkDesk(String type){
      try{
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM DESK WHERE type = '" + type + "'");
    
        if(!results.next()){
          throw new IllegalArgumentException();
        }
      }catch (SQLException ex){
        ex.printStackTrace();
      }
    }
    
    
    public void checkFiling(String type){
      try{
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM FILING WHERE type = '" + type + "'");
    
        if(!results.next()){
          throw new IllegalArgumentException();
        }
      }catch (SQLException ex){
        ex.printStackTrace();
      }
    
    }
    
    public void checkLamp(String type){
      try{
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM LAMP WHERE type = '" + type + "'");
    
        if(!results.next()){
          throw new IllegalArgumentException();
        }
      }catch (SQLException ex){
        ex.printStackTrace();
      }
    
    }
    

    public String priceToString(){
        return "$" + price;
    }

    public int getPrice(){
        return this.price;
    }

}   
