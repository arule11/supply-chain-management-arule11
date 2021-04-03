//package edu.ucalgary.ensf409;


public class Furniture{
  private final String ID;
  private String type;
  private int price;

  public Furniture(String id, String type, int price){
    this.ID = id;
    this.type = type;
    this.price = price;
  }

  public String getID(){
    return this.ID;
  }

  public String getType(){
    return this.type;
  }

  public int getPrice(){
    return this.price;
  }

}
