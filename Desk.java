//package edu.ucalgary.ensf409;

public class Desk extends Furniture{

  private String legs;
  private String top;
  private String drawer;

  // possible manufacturers - 001, 002, 004, 005 - maybe add variable

  public Desk(String id, String type, String leg, String top, String draw, int price){
    super(id, type, price);

    this.legs = leg;
    this.top = top;
    this.drawer = draw;
  }
  
  
  public String getLegs(){
    return this.legs;
  }
  
  public String getArms(){
    return this.arms;
  }
  
  public String getDrawer(){
    return this.drawer;
  }
  
}
