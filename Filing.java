//package edu.ucalgary.ensf409;

public class Filing extends Furniture{

  private String rails;
  private String drawers;
  private String cabinet;
  private static String[] manufacturersID = new String[]{"002", "004", "005"};

  public Filing(String id, String type, String rail, String draw, String cab, int price){
    super(id, type, price);

    this.rails = rail;
    this.drawers = draw;
    this.cabinet = cab;
  }

  public String getRails(){
    return this.rails;
  }

  public String getDrawers(){
    return this.drawers;
  }

  public String getCabinet(){
    return this.cabinet;
  }

  public static String[] getManufacturers(){
    return manufacturersID;
  }
}
