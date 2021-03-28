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

    //Data Members for the setExistance method and possibleCombinations method
    private String legsExists = "N";
    private String armsExists = "N";
    private String seatExists = "N";
    private String cushionExists = "N";
    private ArrayList <String> id = new ArrayList<String>();

    //Constructor
    public Furniture(String url, String username, String pswd){
        this.DBURL = url;
        this.USERNAME = username;
        this.PASSWORD = pswd;
      }

    //Changes existance values to Y is value found for corresponding commodities
    public boolean setExistance(int i){
        boolean set = false;
        try{
        Statement myStmt = dbConnect.createStatement();

        if (legsExists.equals("N")){
            ResultSet leg = myStmt.executeQuery("SELECT Legs FROM chair WHERE ID = '" + id.get(i) +"'");
            leg.next();
            legsExists = leg.getString("Legs");
            set = true;
        }
        if (armsExists.equals("N")){
            ResultSet arm = myStmt.executeQuery("SELECT Arms FROM chair WHERE ID = '" + id.get(i) +"'");
            arm.next();
            armsExists = arm.getString("Arms");
            set = true;
        }
        if (seatExists.equals("N")){
            ResultSet seat = myStmt.executeQuery("SELECT Seat FROM chair WHERE ID = '" + id.get(i) +"'");
            seat.next();
            seatExists = seat.getString("Seat");
           set = true;
        }
        if (cushionExists.equals("N")){
            ResultSet cushion = myStmt.executeQuery("SELECT Cushion FROM chair WHERE ID = '" + id.get(i) +"'");
            cushion.next();
            cushionExists = cushion.getString("Cushion");
            set = true;
        }
        myStmt.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return set;
    }    

    //Gets the price of an item on the database
    public int getPrice (String tableName, String ID){
        int price = 0;
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT Price From " + tableName + " WHERE ID = '" + ID + "'");
            results.next();
            price = results.getInt("Price");            
            myStmt.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return price;
    }

    //Cycles through the chair table of the specified type and finds possible combinations
    public void possibleCombinations(String tableName, String type){
        
        //List all possible combinations of ids seperated by a space and ,
        ArrayList <String> idCombinations = new ArrayList<String>();

        String multipleIDs = ""; //Temporarily holds the merged ID values for idCombinations
        int rawPrice = 0;
        ArrayList <Integer> prices = new ArrayList<Integer>();
        

        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + tableName + " WHERE TYPE = '" + type + "'");
            
            while (results.next()){
                id.add(results.getString("ID"));
            }
            
            for (int i = 0; i < id.size(); i++){
                // Adds all IDs that reuslt in a successful combination
                multipleIDs = id.get(i);
                setExistance(i);
                rawPrice = getPrice(tableName, id.get(i));
                for (int j = i+1 ; j < id.size(); j++){
                    if (setExistance(j)){
                        multipleIDs += ", " + id.get(j);
                        rawPrice += getPrice(tableName, id.get(j));
                    }
                }
                if (legsExists.equals("Y") && armsExists.equals("Y") && seatExists.equals("Y") && cushionExists.equals("Y")){
                    idCombinations.add(multipleIDs);
                    prices.add(rawPrice);
                    //System.out.println("Entered all equals Y statement");
                }
                legsExists = "N";
                armsExists = "N";
                seatExists = "N";
                cushionExists = "N";
            }

            System.out.println("There are : " + idCombinations.size() + " successful combinations.");
            for (int i = 0; i < prices.size(); i++){
                System.out.println("Index: " + i + " consists of ids: " +idCombinations.get(i) + " and has a price of: " +
                 prices.get(i));
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void initializeConnection(){
          try{
            dbConnect = DriverManager.getConnection(getDburl(), getUsername(), getPassword());
          } catch (SQLException e) {
              e.printStackTrace();
          }
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
