//package edu.ucalgary.ensf409;

public class Lamp extends Furniture{

  private String base;
  private String bulb;
  private static String[] manufacturersID = new String[]{"002", "004", "005"};


  public Lamp(String id, String type, String base, String bulb, int price){
    super(id, type, price);

    this.base = base;
    this.bulb = bulb;
  }

  public String getBase(){
    return this.base;
  }

  public String getBulb(){
    return this.bulb;
  }

  public static String[] getManufacturers(){
    return manufacturersID;
  }
}
