/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : nicolas.teng@ucalgary.ca
          Ivan Lou Tompong : ivanlou.tompong@ucalgary.ca
          Alden Lien : alden.lien@ucalgary.ca
@version 1.7
@since 1.0
*/

package edu.ucalgary.ensf409;

import java.util.regex.*;
import java.util.Scanner;

public class Main{
  static String[] arguments = new String[3];

  public static void main(String[] args) throws InvalidOrderException {
    Scanner reader = new Scanner(System.in);
    System.out.println("\nWelcome to Group62 Supply Chain Management.\n Please type your furniture request <category> <furniture>, <amount>\n (example: mesh chair, 1)");

    boolean check = true;
    while(check){
      String order = "";
        try {
        	order = reader.nextLine();
        } catch (Exception e) {
          e.printStackTrace();
        }
        getSecondArg(order);

        boolean valid = true;
        for(int i = 0; i < arguments.length;i++){
          if(arguments[i] == null){
            valid = false;
          }
        }

        if(!valid){
          System.out.println("Invalid argument format. Please try again.\nPlease type your furniture request (example: mesh chair, 1)");
        }else{
          check = false;
        }
      }

      FurnitureDataBase item = new FurnitureDataBase(arguments[0], arguments[1], Integer.parseInt(arguments[2]));
      item.initializeConnection();
      item.addFurniture();
      item.run();
  }

  /**
  * Gets the first argument for the order (the specific furniture type) and removes it from the order param
  * @param type : the order as typed by the user
  */
  public static void getFirstArg(String order){
    Pattern pattern = Pattern.compile("^([a-zA-Z]+(\\s[a-zA-Z]+)*)");
    Matcher matcher = pattern.matcher(order);
    String arg0;

    if(matcher.find()){
      arg0 = matcher.group();
      arguments[0] = arg0;
      getThirdArg(matcher.replaceFirst(""));
    }
  }

  /**
  * Gets the second argument for the order (the general furniture type) and removes it from the order param
  * @param type : the cropped order as typed by the user
  */
  public static void getSecondArg(String order){
    Pattern pattern = Pattern.compile("[a-zA-Z]+,");
    Matcher matcher = pattern.matcher(order);
    String arg1;

    if(matcher.find()){
      arg1 = matcher.group();
      arguments[1] = arg1.substring(0, arg1.length() - 1);
      getFirstArg(matcher.replaceFirst(""));
    }
  }

  /**
  * Gets the third argument for the order (the reqested number of furniture)
  * @param type : the cropped order as typed by the user
  */
  public static void getThirdArg(String order){
    order = order.trim();
    if(order.matches("0|-?[1-9]+")){
      arguments[2] = order;
    }
  }

}
