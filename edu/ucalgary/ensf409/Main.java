/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : 
          Ivan Lou Tompong :
          Alden Lien : 
@version 1.3
@since 1.0
*/

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
  }

  /**
  * Crops the argument of furniture type to just the name not including any commas
  * @param type : the specified argument to crop 
  * @return Returns a String
  */
  public static String createArg(String type){
    Pattern pattern = Pattern.compile("[a-zA-Z]{4,6}");
    Matcher matcher = pattern.matcher(type);
    if(matcher.find()){
      return matcher.group();
    }
    return "";
  }

}
