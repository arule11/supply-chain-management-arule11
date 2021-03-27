package edu.ucalgary.ensf409;


public class Furniture{

  public void printOutput(){
    StringBuffer names = new StringBuffer();
    try{
      Statement myStmt = dbConnect.createStatement();
      results = myStmt.executeQuery("SELECT * FROM MANUFACTURER");
  
      while (results.next()){
        if(results.isLast()){
          names.append("and "+ results.getString("Name"));
        }else{
          names.append(results.getString("Name") + ", " );
        }
      }
      myStmt.close();
    } catch(SQLException ex) {
        ex.printStackTrace();
    }
    System.out.println("Order cannot be fulfilled based on current inventory. Suggested manufacturers are "
            + names.toString());
  }

  public static void printOutput(String[] ids, String price){
    System.out.print("Purchase ");
    for(int i = 0; i < ids.length; i++){
      if(i == ids.length - 1){
        System.out.print("and " + ids[i] );
      }else{
        System.out.print(ids[0] + " ");
      }
    }
    System.out.println(" for " + price);
  }


}
