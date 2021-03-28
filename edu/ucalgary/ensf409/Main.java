package edu.ucalgary.ensf409;

public class Main{

  public static void main(String[] args) {
    Furniture item = new Furniture("jdbc:mysql://localhost/inventory", "nicolas", "ensf409");
    
    //Testing initializeConnection
    item.initializeConnection();

    /*Testing Getter functions
    System.out.println("\n");
    System.out.println(item.getDburl());
    System.out.println(item.getUsername());
    System.out.println(item.getPassword());
    System.out.println("\n");
    */
    System.out.println();

   // Testing Delete
    //item.deleteItem("D9387", "desk");
    item.possibleCombinations("chair", "Ergonomic");
  }

}
