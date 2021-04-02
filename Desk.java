//package edu.ucalgary.ensf409;

public class Desk extends Furniture{

  private String legs;
  private String top;
  private String drawer;
  private String availability;
  // possible manufacturers - 001, 002, 004, 005 - maybe add variable

  public Desk(String id, String type, String leg, String top, String draw, int price, String availability){
    super(id, type, price);

    this.legs = leg;
    this.top = top;
    this.drawer = draw;
    this.availability = availability;
  }

  public String getLegs(){
    return this.legs;
  }

  public String getTop(){
    return this.top;
  }

  public String getDrawer(){
    return this.drawer;
  }
  public String getAvailability(){
    return this.availability;
  }
}
