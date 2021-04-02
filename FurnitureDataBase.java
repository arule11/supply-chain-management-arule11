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
    int totalPrice = 0;
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




  public void printSubSets(){
    ArrayList<ArrayList<Furniture>> all = getSubsets(foundFurniture);

    for(ArrayList<Furniture> sub : all){
      for(int i = 0; i < sub.size(); i++){
        System.out.print(sub.get(i).getID() + " ");
      }
      System.out.println();
    }
    System.out.println("\nprinting valid sets");
//////

    ArrayList<ArrayList<Furniture>> valid = getValid(all);
    for(ArrayList<Furniture> combo : valid){
      for(int i = 0; i < combo.size(); i++){
        System.out.print(combo.get(i).getID() + " ");
      }
      System.out.print("combo price: " + getComboPrice(combo));
      System.out.println();
    }
//////
    System.out.println("\nprinting cheapests set");

    ArrayList<Furniture> cheapest = comparePrice(valid);
    totalPrice = totalPrice + getComboPrice(cheapest);
    for(Furniture x : cheapest){
      System.out.print(x.getID() + " ");
    }
    System.out.println("total price: " + totalPrice);
    System.out.println();
  }



  public static ArrayList<ArrayList<Furniture>> getSubsets(ArrayList<Furniture> set) {
        ArrayList<ArrayList<Furniture>> allsubsets = new ArrayList<ArrayList<Furniture>>();
        //there are 2 power n different subsets
        //int max = (int)Math.pow(2, set.size());
        //int max = pow(2, set.size());
        int max = 1 << set.size();

        for (int i = 0; i < max; i++) {
            ArrayList<Furniture> subset = new ArrayList<Furniture>();
            for (int j = 0; j < set.size(); j++) {
                if (((i >> j) & 1) == 1) {
                    subset.add(set.get(j));
                }
            }
            allsubsets.add(subset);
        }
        return allsubsets;
    }



  public int getComboPrice(ArrayList<Furniture> subset){
    int price = 0;
    for(Furniture furn : subset){
      price = price + furn.getPrice();
    }
    return price;
  }

  public ArrayList<Furniture> comparePrice(ArrayList<ArrayList<Furniture>> valid ){
    //ArrayList<ArrayList<Furniture>> valid = getValid(all);
    int price = getComboPrice(valid.get(0));
    ArrayList<Furniture> cheapest = valid.get(0);

    for(ArrayList<Furniture> combo : valid){
      if(getComboPrice(combo) < price){
        price = getComboPrice(combo);
        cheapest = combo;
      }
    }
    return cheapest;
  }


  public ArrayList<ArrayList<Furniture>> getValid(ArrayList<ArrayList<Furniture>> all){
    ArrayList<ArrayList<Furniture>> validSets = new ArrayList<ArrayList<Furniture>>();
    for(ArrayList<Furniture> subset : all){
      if(furnitureRequest.equalsIgnoreCase("Chair")){
        if(checkChairSubet(subset)){
          validSets.add(subset);
        }
      }else if(furnitureRequest.equalsIgnoreCase("Desk")){
        if(checkDeskSubet(subset)){
          validSets.add(subset);
        }
      }else if(furnitureRequest.equalsIgnoreCase("Lamp")){
        if(checkLampSubet(subset)){
          validSets.add(subset);
        }
      }else if(furnitureRequest.equalsIgnoreCase("Filing")){
        if(checkFilingSubet(subset)){
          validSets.add(subset);
        }
      }
    }
    return validSets;
  }

  public boolean checkChairSubet(ArrayList<Furniture> subset){
    boolean legs = false;
    boolean arms = false;
    boolean seat = false;
    boolean cushion = false;

    for(Furniture c : subset){
      if(c instanceof Chair){
        Chair chair = (Chair)c;
        if(chair.getLegs().equals("Y")){
          legs = true;
        }
        if(chair.getArms().equals("Y")){
          arms = true;
        }
        if(chair.getSeat().equals("Y")){
          seat = true;
        }
        if(chair.getCushion().equals("Y")){
          cushion = true;
        }
      }
    }
    if(!legs || !arms || !seat || !cushion){
      return false;
    }
    return true;
  }


  public boolean checkDeskSubet(ArrayList<Furniture> subset){
    boolean legs = false;
    boolean top = false;
    boolean drawer = false;

    for(Furniture d : subset){
      if(d instanceof Desk){
        Desk desk = (Desk)d;
        if(desk.getLegs().equals("Y")){
          legs = true;
        }
        if(desk.getTop().equals("Y")){
          top = true;
        }
        if(desk.getDrawer().equals("Y")){
          drawer = true;
        }
      }
    }
    if(!legs || !top || !drawer){
      return false;
    }
    return true;
  }


  public boolean checkLampSubet(ArrayList<Furniture> subset){
    boolean base = false;
    boolean bulb = false;

    for(Furniture l : subset){
      if(l instanceof Lamp){
        Lamp lamp = (Lamp)l;
        if(lamp.getBase().equals("Y")){
          base = true;
        }
        if(lamp.getBulb().equals("Y")){
          bulb = true;
        }
      }
    }
    if(!base || !bulb){
      return false;
    }
    return true;
  }


  public boolean checkFilingSubet(ArrayList<Furniture> subset){
    boolean rails = false;
    boolean cabinet = false;
    boolean drawers = false;

    for(Furniture f : subset){
      if(f instanceof Filing){
        Filing filing = (Filing)f;
        if(filing.getRails().equals("Y")){
          rails = true;
        }
        if(filing.getCabinet().equals("Y")){
          cabinet = true;
        }
        if(filing.getDrawers().equals("Y")){
          drawers = true;
        }
      }
    }
    if(!rails || !cabinet || !drawers){
      return false;
    }
    return true;
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
      //for(Furniture i : foundSet){
        System.out.println(i.getID() + ": " + i.getType() + ", " + i.priceToString());
      }
    }


 }
