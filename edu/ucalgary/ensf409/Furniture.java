/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : 
          Ivan Lou Tompong :
          Alden Lien : 
@version 1.3
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Furniture{
  private final String ID;
  private String type;
  private int price;


  /**
   * Sets the variables ID, type, and price with the following corresponding arguments.
   * @param id : ID used to identify the furniture
   * @param type : The type of furniture requested; Can be a chair, desk, filing, or lamp
   * @param price : The price of the item
   */
  public Furniture(String id, String type, int price){
    this.ID = id;
    this.type = type;
    this.price = price;
  }



  /**
   * @return Returns the variable ID
   */
  public String getID(){
    return this.ID;
  }

  /**
   * @return Returns the variable type
   */
  public String getType(){
    return this.type;
  }

  /**
   * @return Returns the price
   */
  public int getPrice(){
    return this.price;
  }

}
