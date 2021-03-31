//package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.*;

public class FurnitureDataBase{

    private final String DBURL = "jdbc:mysql://localhost/inventory"; //Database URL
    private final String USERNAME = ""; //MySQL username
    private final String PASSWORD = ""; //MySQL password
    private Connection dbConnect;
    private ResultSet results;

    //private int price;
    String furnitureRequest;
    String requestType;
    int requestNum;
    ArrayList<Furniture> foundFurniture;


    public FurnitureDataBase(String request, String type, int number){
      this.furnitureRequest = request;
      this.requestType = type;
      this.requestNum = number;
      foundFurniture = new ArrayList<Furniture>();
    }


  public void initializeConnection(){
      try{
          dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
      } catch (SQLException ex){
          ex.printStackTrace();
      }
  }

  public void addFurniture(){
    try{
      Statement myStmt = dbConnect.createStatement();
      results = myStmt.executeQuery("SELECT * FROM " + furnitureRequest + " WHERE type = '" + requestType + "'");

      while (results.next()){
        if(furnitureRequest.equalsIgnoreCase("Chair")){
          foundFurniture.add(new Chair(results.getString("ID"), results.getString("Type"), results.getString("Legs"),
                results.getString("Arms"), results.getString("Seat"), results.getString("Cushion"), results.getInt("Price")));
          continue;
        }else if(furnitureRequest.equalsIgnoreCase("Desk")){
          foundFurniture.add(new Desk(results.getString("ID"), results.getString("Type"), results.getString("Legs"),
                results.getString("Top"), results.getString("Drawer"), results.getInt("Price")));
          continue;
        }else if(furnitureRequest.equalsIgnoreCase("Lamp")){
          foundFurniture.add(new Lamp(results.getString("ID"), results.getString("Type"), results.getString("Base"),
                 results.getString("Bulb"), results.getInt("Price")));
          continue;
        }else if(furnitureRequest.equalsIgnoreCase("Filing")){
          foundFurniture.add(new Filing(results.getString("ID"), results.getString("Type"), results.getString("Rails"),
                  results.getString("Drawers"), results.getString("Cabinet"), results.getInt("Price")));
          continue;
        }else{
          break;
        }
      }
      myStmt.close();
    } catch(SQLException ex) {
        ex.printStackTrace();
    }
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

  

  //  printing to test
    public void printFurn(){
      for(Furniture i : foundFurniture){
        System.out.println(i.getID() + ": " + i.getType() + ", " + i.priceToString());
      }
    }


 }
