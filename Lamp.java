//package edu.ucalgary.ensf409;

public class Lamp extends Furniture{

  private String base;
  private String bulb;


  // possible manufacturers - 005, 004, 002 - maybe add variable

  public Lamp(String id, String type, String base, String bulb, int price){
    super(id, type, price);

    this.base = base;
    this.bulb = bulb;
  }

}
