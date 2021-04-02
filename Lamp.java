//package edu.ucalgary.ensf409;

public class Lamp extends Furniture{

  private String base;
  private String bulb;
  private String availability;

  // possible manufacturers - 005, 004, 002 - maybe add variable

  public Lamp(String id, String type, String base, String bulb, int price, String availability){
    super(id, type, price);

    this.base = base;
    this.bulb = bulb;
    this.availability = availability;
  }

  public String getBase(){
    return this.base;
  }

  public String getBulb(){
    return this.bulb;
  }

  public String getAvailability(){
    return this.availability;
  }
}
