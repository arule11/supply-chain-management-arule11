//package edu.ucalgary.ensf409;

public class Filing extends Furniture{

  private String rails;
  private String drawers;
  private String cabinet;
  private String availability;
  //private String[] manufacturers;

  // possible manufacturers - 005, 004, 002 - maybe add variable

  public Filing(String id, String type, String rail, String draw, String cab, int price, String availability){
    super(id, type, price);

    this.rails = rail;
    this.drawers = draw;
    this.cabinet = cab;
    this.availability = availability;
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
  
  public String getAvailability(){
    return this.availability;
  }
}
