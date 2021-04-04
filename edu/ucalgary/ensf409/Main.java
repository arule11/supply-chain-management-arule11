package edu.ucalgary.ensf409;

import java.util.regex.*;

public class Main{

  public static void main(String[] args) {
    if(args.length < 3){
      System.out.println("Usage: Main <category> <furniture> <amount>");
      System.exit(1);
    }
    String type = createArg(args[1]);

    FurnitureDataBase item = new FurnitureDataBase(args[0], type, Integer.parseInt(args[2]));
    item.initializeConnection();
    item.addFurniture();
    item.run();

    //System.out.println();
    // FurnitureDataBase item2 = new FurnitureDataBase("Standing", "Desk", 2);
    // item2.initializeConnection();
    // item2.addFurniture();
    // item2.run();
  }

  public static String createArg(String type){
    Pattern pattern = Pattern.compile("[a-zA-Z]{4,6}");
    Matcher matcher = pattern.matcher(type);
    if(matcher.find()){
      return matcher.group();
    }
    return "";
  }

}
