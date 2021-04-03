//package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.*;
import java.io.*;

public class FurnitureDataBase{

    private final String DBURL = "jdbc:mysql://localhost/inventory"; //Database URL
    private final String USERNAME = "athena"; //MySQL username
    private final String PASSWORD = "chilli123"; //MySQL password
    private Connection dbConnect;
    private ResultSet results;

    static String furnitureRequest;
    static String requestType;
    static int requestNum;
    ArrayList<Furniture> foundFurniture;

    public String getFurnitureRequest(){
      return this.furnitureRequest;
    }

    public String getRequestType(){
      return this.requestType;
    }

    public int getRequestNum(){
      return this.requestNum;
    }


    public FurnitureDataBase(String type, String request, int number){
    //public FurnitureDataBase(String request, String type, int number){
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

  /**
  * Adds all furniture of the type specified to the list of foundFurniture
  */
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

    ArrayList<ArrayList<Furniture>> all = getSubsets(foundFurniture);
    ArrayList<ArrayList<Furniture>> valid = getValid(all);
    ArrayList<ArrayList<Furniture>> orderedCheapest = comparePrice(valid);

    int n = 0;
    boolean set;
    while(n != requestNum){
      for(int i = 0; i < orderedCheapest.size(); i++){
        set = true;
        for(int j = 0; j < orderedCheapest.get(i).size(); j++){
          // looking at furniture item
          for(ArrayList<Furniture> forn : orders){
            if(forn.contains(orderedCheapest.get(i).get(j))){
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
  public void checkOrder(ArrayList<ArrayList<Furniture>> orders){
    ArrayList<ArrayList<Furniture>> last = new ArrayList<ArrayList<Furniture>>();

    if(requestNum > orders.size()){
      //System.out.println("CANNOT FULFIL ORDER !");
      printOutputFail();
      return;
    }else{
      for(int i = 0; i < requestNum; i++){
        last.add(orders.get(i));
      }
      deleteOrders(last);
      printOutput(last);
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
/////// TRY TO REDO THIS CODE ^^^^^^^^^^



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
      if(furnitureRequest.equalsIgnoreCase("Chair")){
        if(checkChairSubset(subset)){
          validSets.add(subset);
        }
      }else if(furnitureRequest.equalsIgnoreCase("Desk")){
        if(checkDeskSubset(subset)){
          validSets.add(subset);
        }
      }else if(furnitureRequest.equalsIgnoreCase("Lamp")){
        if(checkLampSubset(subset)){
          validSets.add(subset);
        }
      }else if(furnitureRequest.equalsIgnoreCase("Filing")){
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
      String query = "DELETE FROM " + furnitureRequest + " WHERE ID = ?";
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
  public void printOutputFail(){
    String[] manufactIDs = new String[6];

    if(furnitureRequest.equalsIgnoreCase("Chair")){
      manufactIDs = Chair.getManufacturers();
    }else if(furnitureRequest.equalsIgnoreCase("Desk")){
      manufactIDs = Desk.getManufacturers();
    }else if(furnitureRequest.equalsIgnoreCase("Lamp")){
      manufactIDs = Lamp.getManufacturers();
    }else if(furnitureRequest.equalsIgnoreCase("Filing")){
      manufactIDs = Filing.getManufacturers();
    }

    ArrayList<String> names = new ArrayList<String>();
    try{
      Statement myStmt = dbConnect.createStatement();
      for(int i = 0; i < manufactIDs.length; i++){
        results = myStmt.executeQuery("SELECT * FROM MANUFACTURER where ManuID = '" + manufactIDs[i] + "'");
        while (results.next()){
          names.add(results.getString("Name"));
        }
      }
      myStmt.close();
    } catch(SQLException ex) {
        ex.printStackTrace();
    }
    // print failed output message to console
    System.out.println("Order cannot be fulfilled based on current inventory. Suggested manufacturers are ");
    for(int j = 0; j < names.size(); j++){
      if(j == names.size() - 1){
        System.out.println("and " + names.get(j) + ".");
      }else{
        System.out.print(names.get(j) + ", ");
      }
    }
  }


  /**
  * Output for filled order request - printing IDs of items purchased and the total price
  * @param purchased : the ArrayList of Lists of Furniture containing all purchased items
  */
  public static void printOutput(ArrayList<ArrayList<Furniture>> purchased){
    int price = 0;
    System.out.print("Purchase ");
    for(int i = 0; i < purchased.size(); i++){
      price = price + getComboPrice(purchased.get(i));
      for(int j = 0; j < purchased.get(i).size(); j++){
        if(i == 0 && j == 0){
          System.out.print(purchased.get(i).get(j).getID() + ", ");
        }else if(i == (purchased.size() - 1) && j == (purchased.get(i).size() - 1)){
          System.out.print("and " + purchased.get(i).get(j).getID());
        }else{
          System.out.print(purchased.get(i).get(j).getID() + ", ");
        }
      }
    }
    System.out.println(" for $" + price + ".");
    // create order form
    WriteOrderForm(purchased, price);
  }


  /**
  * Creates an order form - containing the original request and a list of all purchased items and their price
  * @param purchased : the ArrayList of Lists of Furniture containing all purchased items
  * @param price : the total price of all the items purchased
  */
  public static void WriteOrderForm(ArrayList<ArrayList<Furniture>> purchased, int price){
    String outputFile = "orderform.txt";
    File toWrite = new File(outputFile);
    ArrayList<String> ids = new ArrayList<String>();
    //String orgOrder = getRequestType() + " " + getFurnitureRequest() + ", " + getRequestNum();
    String orgOrder = requestType + " " + furnitureRequest + ", " + requestNum;

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
      //out.write();
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
  *
  */
  public void run(){
    ArrayList<ArrayList<Furniture>> all = getSubsets(foundFurniture);
    ArrayList<ArrayList<Furniture>> valid = getValid(all);
    ArrayList<ArrayList<Furniture>> ordered = comparePrice(valid);
    ArrayList<ArrayList<Furniture>> orders = produceOrder();
    checkOrder(orders);
  }

 }
