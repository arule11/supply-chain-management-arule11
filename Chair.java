//package edu.ucalgary.ensf409;

public class Chair extends Furniture{

  private String legs;
  private String arms;
  private String seat;
  private String cushion;

  // possible manufacturers - 002, 003, 004, 005 - maybe add variable

  public Chair(String id, String type, String leg, String arm, String seat, String cush, int price){
    super(id, type, price);

    this.legs = leg;
    this.arms = arm;
    this.seat = seat;
    this.cushion = cush;
  }


}
