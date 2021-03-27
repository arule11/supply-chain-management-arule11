package edu.ucalgary.ensf409;

public class Main{

  public static void main(String[] args) {
    Furniture item = new Furniture("302", "teng", "ensf409");
    
    //Testing initializeConnection
    item.initializeConnection();

    //Testing Getter functions
    System.out.println("\n");
    System.out.println(item.getDburl());
    System.out.println(item.getUsername());
    System.out.println(item.getPassword());
    System.out.println("\n");

   // Testing Delete
    item.deleteItem("D9387", "desk");

  }

}
