package edu.ucalgary.ensf409;


public class Furniture{
  
  public static void WriteOrderForm(String orgOrder, String[] ids, String price){
    String outputFile = "orderform.txt";
    File toWrite = new File(outputFile);
    try{
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
