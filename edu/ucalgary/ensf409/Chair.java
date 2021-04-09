/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : nicolas.teng@ucalgary.ca
          Ivan Lou Tompong :
          Alden Lien : alden.lien@ucalgary.ca
@version 1.3
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Chair extends Furniture{

  private String legs;  
  private String arms; 
  private String seat; 
  private String cushion; 
  private static String[] manufacturersID = new String[]{"002", "003", "004", "005"};

  /**
   * When the furniture type requested is a chair, it calls the parent class Furniture to set the
   * ID of the chair, the type of chair, and then the price of the chair, then the individual 
   * components of the chair are assigned one of two characters, 'Y' if the chair has said component
   * or a 'N' if said component is missing, based on the given arguments below. 
   * 
   * @param id : The ID used to identify the item
   * @param type : The type of chair
   * @param leg : Contains 'Y' if chair has legs, 'N' if it doesn't
   * @param arm : Contains 'Y' if chair has arms, 'N' if it doesn't
   * @param seat : Contains 'Y' if chair has a seat, 'N' if it doesn't
   * @param cush : Contains 'Y' if chair has a cushion, 'N' if it doesn't
   * @param price : How much the chair costs
   */
  public Chair(String id, String type, String leg, String arm, String seat, String cush, int price){
    super(id, type, price);

    this.legs = leg;
    this.arms = arm;
    this.seat = seat;
    this.cushion = cush;
  }
  /**
   * @return Returns the variable legs
   */
  public String getLegs(){
    return this.legs;
  }
  /**
   * @return Returns the variable arms
   */
  public String getArms(){
    return this.arms;
  }
  /**
   * @return Returns the variable seat
   */
  public String getSeat(){
    return this.seat;
  }
  /**
   * @return Returns the variable cushion
   */
  public String getCushion(){
    return this.cushion;
  }
  /**
   * @return Returns a String array called manufacturersID
   */
  public static String[] getManufacturers(){
    return manufacturersID;
  }


}
