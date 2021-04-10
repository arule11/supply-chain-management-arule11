/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : nicolas.teng@ucalgary.ca
          Ivan Lou Tompong : ivanlou.tompong@ucalgary.ca
          Alden Lien : alden.lien@ucalgary.ca
@version 1.10
@since 1.0
*/

package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.*;
import java.io.*;

/**
* Class representing a furniture database.
*/
public class FurnitureDataBase{

  private final String DBURL = "jdbc:mysql://localhost/inventory"; //Database URL
  private final String USERNAME = "scm"; //MySQL username
  private final String PASSWORD = "ensf409"; //MySQL password
  protected Connection dbConnect;
  protected ResultSet results;

  private static String furnitureRequest;
  private static String requestType;
  private static int requestNum;
  private ArrayList<Furniture> foundFurniture;


  /**
  * Set the variables furnitureRequest, requestType, and requestNum based on the given arguments
  * @param type : the specified specific type of furniture requested
  * @param request : the general type of furniture requested
  * @param number : the number of furniture items requested
  */
  public FurnitureDataBase(String type, String request, int number){
    setFurnitureRequest(request);
    setRequestType(type);
    setRequestNum(number);
    foundFurniture = new ArrayList<Furniture>();
  }


  /**
  * Creates a connection to the database
  */
  public void initializeConnection(){
      try{
          dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
      } catch (SQLException ex){
          ex.printStackTrace();
      }
  }

   /**
   *Returns the variable furnitureRequest, which specifies the general
   *type of Furniture requested, i.e. a Chair, Desk, Lamp, or Filing
   */
  public static String getFurnitureRequest(){
    return furnitureRequest;
  }

   /**
   * Returns the variable requestType, which specifies
   *  the specific type of the Furniture requested
   */
  public static String getRequestType(){
    return requestType;
  }

  /**
  * Returns the variable requestNum, which specifies the amount requested
  */
  public static int getRequestNum(){
    return requestNum;
  }

   /**
   * Returns the ArrayList foundFurniture, which contains all available items
   * that are of the requested general Furniture type
   */
  public ArrayList<Furniture> getFoundFurniture(){
    return foundFurniture;
  }

  /**
  * Sets the value of the variable furnitureRequest
  */
  public void setFurnitureRequest(String furniture){
    furnitureRequest = furniture;
  }

  /**
  * Sets the value of the variable requestType
  */
  public void setRequestType(String category){
    requestType = category;
  }

  /**
  * Sets the value of the variable requestNum
  */
  public void setRequestNum(int amount){
    requestNum = amount;
  }

  /**
  * Sets the value of the variable foundFurniture
  */
  public void setFoundFurniture(ArrayList<Furniture> furniture){
    foundFurniture = furniture;
  }


  /**
  * Adds all furniture of the type specified to the list of foundFurniture
  */
  public void addFurniture() throws InvalidOrderException{
    try{
        String category = getRequestType().toLowerCase();
        int amount = getRequestNum();
        boolean invalid = true;
        // check that the furniture order is one of the four valid types with a valid corresponding category
        switch(getFurnitureRequest().toLowerCase()){
          case "chair":
            if(category.equals("kneeling") || category.equals("task") || category.equals("mesh")
                    || category.equals("executive") || category.equals("ergonomic")){
              invalid = false;
              break;
            }
          case "desk":
            if(category.equals("standing") || category.equals("adjustable") || category.equals("traditional")){
              invalid = false;
              break;
            }
          case "lamp":
            if(category.equals("desk") || category.equals("study") || category.equals("swing arm")){
              invalid = false;
            }
            break;
          case "filing":
            if(category.equals("small") || category.equals("medium") || category.equals("large")){
              invalid = false;
            }
            break;
        }
        // check the amount order is greater than zero
        if(amount <= 0){
          invalid = true;
        }
        if(invalid){
          throw new InvalidOrderException();
        }

      Statement myStmt = dbConnect.createStatement();
      results = myStmt.executeQuery("SELECT * FROM " + furnitureRequest + " WHERE type = '" + requestType + "'");
      // add all of same type of furniture request found in the database to a list
      while (results.next()){
        if(getFurnitureRequest().equalsIgnoreCase("Chair")){
          foundFurniture.add(new Chair(results.getString("ID"), results.getString("Type"), results.getString("Legs"),
                results.getString("Arms"), results.getString("Seat"), results.getString("Cushion"), results.getInt("Price")));
          continue;
        }else if(getFurnitureRequest().equalsIgnoreCase("Desk")){
          foundFurniture.add(new Desk(results.getString("ID"), results.getString("Type"), results.getString("Legs"),
                results.getString("Top"), results.getString("Drawer"), results.getInt("Price")));
          continue;
        }else if(getFurnitureRequest().equalsIgnoreCase("Lamp")){
          foundFurniture.add(new Lamp(results.getString("ID"), results.getString("Type"), results.getString("Base"),
                 results.getString("Bulb"), results.getInt("Price")));
          continue;
        }else if(getFurnitureRequest().equalsIgnoreCase("Filing")){
          foundFurniture.add(new Filing(results.getString("ID"), results.getString("Type"), results.getString("Rails"),
                  results.getString("Drawers"), results.getString("Cabinet"), results.getInt("Price")));
          continue;
        }
      }
      myStmt.close();
    } catch(SQLException ex) {
      ex.printStackTrace();
    }
  }


  /**
  * Creates a list of all purchased orders of the given number requested
  * @return Returns an ArrayList of ArrayList of Furniture
  */
  public ArrayList<ArrayList<Furniture>> produceOrder(){
    ArrayList<ArrayList<Furniture>> orders = new ArrayList<ArrayList<Furniture>>();
    ArrayList<ArrayList<Furniture>> all = getSubsets(getFoundFurniture());
    ArrayList<ArrayList<Furniture>> valid = getValid(all);
    ArrayList<ArrayList<Furniture>> orderedCheapest = comparePrice(valid);

    int n = 0;
    boolean set;
    // adding up to the number requested
    while(n != getRequestNum()){
      for(int i = 0; i < orderedCheapest.size(); i++){
        set = true;
        for(int j = 0; j < orderedCheapest.get(i).size(); j++){
          // looking at furniture item
          for(ArrayList<Furniture> furn : orders){
            // if the list of orders already contains an id dont add that furniture combo to the order
            if(furn.contains(orderedCheapest.get(i).get(j))){
              set = false;
            }
          }
        }
        if(set){
          orders.add(orderedCheapest.get(i));
        }
      }
      n++;
    }
    return orders;
  }


  /**
  * Checks the list of purchased orders against the number of orders requested.
  *   if the list contains less items then requested, the order cannot be fulfilled. otherwise
  *   it is filled.
  * @param orders : the ArrayList of Lists of Furniture of purchases
  */
  public String checkOrder(ArrayList<ArrayList<Furniture>> orders, boolean gui){
    ArrayList<ArrayList<Furniture>> last = new ArrayList<ArrayList<Furniture>>();
    // if the amount requested is larger than the amount of valid orders found then the request fails
    if(getRequestNum() > orders.size()){
      return printOutputFail(gui);
    }else{
      for(int i = 0; i < getRequestNum(); i++){
        last.add(orders.get(i));
      }
      deleteOrders(last);
      return printOutput(last, gui);
    }
  }


  /**
  * Creates a list of all subsets for the specified list of furniture items
  * @param set : the ArrayList of Furniture containing only items of the requested type
  * @return Returns an ArrayList of list of Furniture containing all subsets
  */
  /**
  * code found from: https://stackoverflow.com/questions/14224953/get-all-subsets-of-a-set/14225105
  */
  public static ArrayList<ArrayList<Furniture>> getSubsets(ArrayList<Furniture> set) {
    ArrayList<ArrayList<Furniture>> allsubsets = new ArrayList<ArrayList<Furniture>>();
    // amount of subsets is 2^(set size)
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


  /**
  * Calculates the price of the combination of furniture items used to rebuild the requested items
  * @param subset : the ArrayList of Furniture of whose price needs to be calculated
  * @return Returns an int price for the given subset
  */
  public static int getComboPrice(ArrayList<Furniture> subset){
    int price = 0;
    for(Furniture furn : subset){
      price = price + furn.getPrice();
    }
    return price;
  }


  /**
  * Orders the given List of valid building combinations from cheapest to most expensive
  * @param valid : the ArrayList of Lists of Furniture containing only valid combinations
  * @return Returns an ArrayList of Lists of Furniture ordered from cheapest to most expensive
  */
  public ArrayList<ArrayList<Furniture>> comparePrice(ArrayList<ArrayList<Furniture>> valid ){
    ArrayList<ArrayList<Furniture>> ordered = new ArrayList<ArrayList<Furniture>>();

    int i = valid.size();
    while(i != 0){
      int price = getComboPrice(valid.get(0));
      ArrayList<Furniture> cheapest = valid.get(0);

      for(ArrayList<Furniture> combo : valid){
        if(getComboPrice(combo) < price){
          price = getComboPrice(combo);
          cheapest = combo;
        }
      }
      // adds the cheapest element to the list and then removes it from the param list
      ordered.add(cheapest);
      valid.remove(cheapest);
      i--;
    }
    return ordered;
  }


  /**
  * Creates a new List containing only valid build combinations from the specified list of
  *     all possible subsets
  * @param all : the ArrayList of Lists of Furniture containing all possible subsets of the
  *     requested furniture type
  * @return Returns an ArrayList of Lists of Furniture containing only valid built combinations
  */
  public ArrayList<ArrayList<Furniture>> getValid(ArrayList<ArrayList<Furniture>> all){
    ArrayList<ArrayList<Furniture>> validSets = new ArrayList<ArrayList<Furniture>>();
    for(ArrayList<Furniture> subset : all){
      if(getFurnitureRequest().equalsIgnoreCase("Chair")){
        if(checkChairSubset(subset)){
          validSets.add(subset);
        }
      }else if(getFurnitureRequest().equalsIgnoreCase("Desk")){
        if(checkDeskSubset(subset)){
          validSets.add(subset);
        }
      }else if(getFurnitureRequest().equalsIgnoreCase("Lamp")){
        if(checkLampSubset(subset)){
          validSets.add(subset);
        }
      }else if(getFurnitureRequest().equalsIgnoreCase("Filing")){
        if(checkFilingSubset(subset)){
          validSets.add(subset);
        }
      }
    }
    return validSets;
  }

  /**
  * Checks whether or not the specified subset is a valid chair build combination
  * @param subset : the ArrayList of Furniture containing a possible chair build combination
  * @return Returns a boolean indicating whether the subset is valid
  */
  public boolean checkChairSubset(ArrayList<Furniture> subset){
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
    // check if any of the chair pieces are missing and if so the combination is invalid
    if(!legs || !arms || !seat || !cushion){
      return false;
    }
    return true;
  }

  /**
  * Checks whether or not the specified subset is a valid desk build combination
  * @param subset : the ArrayList of Furniture containing a possible desk build combination
  * @return Returns a boolean indicating whether the subset is valid
  */
  public boolean checkDeskSubset(ArrayList<Furniture> subset){
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
    // check if any of the desk pieces are missing and if so the combination is invalid
    if(!legs || !top || !drawer){
      return false;
    }
    return true;
  }

  /**
  * Checks whether or not the specified subset is a valid lamp build combination
  * @param subset : the ArrayList of Furniture containing a possible lamp build combination
  * @return Returns a boolean indicating whether the subset is valid
  */
  public boolean checkLampSubset(ArrayList<Furniture> subset){
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
    // check if any of the lamp pieces are missing and if so the combination is invalid
    if(!base || !bulb){
      return false;
    }
    return true;
  }

  /**
  * Checks whether or not the specified subset is a valid filing build combination
  * @param subset : the ArrayList of Furniture containing a possible filing build combination
  * @return Returns a boolean indicating whether the subset is valid
  */
  public boolean checkFilingSubset(ArrayList<Furniture> subset){
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
    // check if any of the filing pieces are missing and if so the combination is invalid
    if(!rails || !cabinet || !drawers){
      return false;
    }
    return true;
  }

  /**
  * Deletes each furniture item in the specified list from the database
  * @param toDelete : the ArrayList of Lists of Furniture containing all purchased items
  */
  public void deleteOrders(ArrayList<ArrayList<Furniture>> toDelete){
    for(int i = 0; i < toDelete.size(); i++){
      for(int j = 0; j < toDelete.get(i).size(); j++){
        deleteItem(toDelete.get(i).get(j).getID());
      }
    }
  }

  /**
  * Deletes each furniture item with the specified ID from the database
  * @param deleteID : the ID of the furniture item to delete
  */
  public void deleteItem(String deleteID){
    try{
      // Creates a statement for the delete instruciton
      String query = "DELETE FROM " + getFurnitureRequest() + " WHERE ID = ?";
      PreparedStatement myStmt = dbConnect.prepareStatement(query);

      myStmt.setString(1, deleteID);
      myStmt.executeUpdate();
      myStmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }


  /**
  * Output for failed purchase request - printing names of suggested manufacturers for the order
  */
  public String printOutputFail(boolean gui){
    String[] manufactIDs = new String[6];
    // get the manufacturers for the specific furniture request
    if(getFurnitureRequest().equalsIgnoreCase("Chair")){
      manufactIDs = Chair.getManufacturers();
    }else if(getFurnitureRequest().equalsIgnoreCase("Desk")){
      manufactIDs = Desk.getManufacturers();
    }else if(getFurnitureRequest().equalsIgnoreCase("Lamp")){
      manufactIDs = Lamp.getManufacturers();
    }else if(getFurnitureRequest().equalsIgnoreCase("Filing")){
      manufactIDs = Filing.getManufacturers();
    }

    ArrayList<String> names = new ArrayList<String>();
    try{
      Statement myStmt = dbConnect.createStatement();
      for(int i = 0; i < manufactIDs.length; i++){
        results = myStmt.executeQuery("SELECT * FROM MANUFACTURER where ManuID = '" + manufactIDs[i] + "'");
        // add the name of all manufacturers for the furniture type to an array
        while (results.next()){
          names.add(results.getString("Name"));
        }
      }
      myStmt.close();
    } catch(SQLException ex) {
        ex.printStackTrace();
    }

    StringBuilder sb = new StringBuilder();
    for(int j = 0; j < names.size(); j++){
      if(j == names.size() - 1){
        sb.append("and " + names.get(j) + ".\n");
      }else{
        sb.append(names.get(j) + ", ");
      }
    }
    // print failed output message to console or GUI
    if(gui){
      return "Order cannot be fulfilled based on current inventory. Suggested manufacturers are " + sb.toString();
    }
    System.out.println("\nOrder cannot be fulfilled based on current inventory. Suggested manufacturers are " + sb.toString());
    return "";
  }


  /**
  * Output for filled order request - printing IDs of items purchased and the total price
  * @param purchased : the ArrayList of Lists of Furniture containing all purchased items
  */
  public static String printOutput(ArrayList<ArrayList<Furniture>> purchased, boolean gui){
    StringBuilder sb = new StringBuilder();
    int price = 0;
    sb.append("Purchase ");
    for(int i = 0; i < purchased.size(); i++){
      price = price + getComboPrice(purchased.get(i));
      for(int j = 0; j < purchased.get(i).size(); j++){
        if(i == 0 && j == 0){
          sb.append(purchased.get(i).get(j).getID() + ", ");
        }else if(i == (purchased.size() - 1) && j == (purchased.get(i).size() - 1)){
          sb.append("and " + purchased.get(i).get(j).getID());
        }else{
          sb.append(purchased.get(i).get(j).getID() + ", ");
        }
      }
    }
    sb.append(" for $" + price + ".\n");
    // create order form
    writeOrderForm(purchased, price);
    // print order summary message to console or GUI
    if(gui){
      return sb.toString();
    }
    System.out.println("\n" + sb.toString());
    return "";
  }


  /**
  * Creates an order form - containing the original request and a list of all purchased items and their price
  * @param purchased : the ArrayList of Lists of Furniture containing all purchased items
  * @param price : the total price of all the items purchased
  */
  public static void writeOrderForm(ArrayList<ArrayList<Furniture>> purchased, int price){
    String outputFile = "orderform.txt";
    File toWrite = new File(outputFile);
    ArrayList<String> ids = new ArrayList<String>();
    String orgOrder = getRequestType() + " " + getFurnitureRequest() + ", " + getRequestNum();

    try{
      // adds ID of each item purchased to a list of IDs
      for(int i = 0; i < purchased.size(); i++){
        for(int j = 0; j < purchased.get(i).size(); j++){
          ids.add(purchased.get(i).get(j).getID());
        }
      }

      if(toWrite.exists() && toWrite.isFile()){
        toWrite.delete();
      }
      toWrite.createNewFile();

      FileWriter writer = new FileWriter(outputFile);
      BufferedWriter out = new BufferedWriter(writer);
      // writes an output file contains a summary of the order
      out.write("Furniture Order Form \n\n");
      out.write("Faculty Name:\n");
      out.write("Contact:\n");
      out.write("Date:\n\n");

      out.write("Original Request: " + orgOrder + "\n\n");
      out.write("Items Ordered");

      for(String i : ids){
        out.write("\nID: " + i);
      }
      out.write("\n\nTotal Price: " + price);

      out.flush();
      out.close();
    }catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
  }


  /**
  * Runs the program in console - computing the requested order
  */
  public void run(){
    ArrayList<ArrayList<Furniture>> all = getSubsets(getFoundFurniture());
    ArrayList<ArrayList<Furniture>> valid = getValid(all);
    ArrayList<ArrayList<Furniture>> ordered = comparePrice(valid);
    ArrayList<ArrayList<Furniture>> orders = produceOrder();
    checkOrder(orders, false);
  }

  /**
  * Runs the program in GUI - computing the requested order
  */
  public String runGUI(){
    ArrayList<ArrayList<Furniture>> all = getSubsets(getFoundFurniture());
    ArrayList<ArrayList<Furniture>> valid = getValid(all);
    ArrayList<ArrayList<Furniture>> ordered = comparePrice(valid);
    ArrayList<ArrayList<Furniture>> orders = produceOrder();
    return checkOrder(orders, true);
  }

 }
