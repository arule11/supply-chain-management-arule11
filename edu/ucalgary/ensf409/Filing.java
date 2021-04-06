/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : 
          Ivan Lou Tompong :
          Alden Lien : 
@version 1.3
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Filing extends Furniture{

  private String rails;
  private String drawers;
  private String cabinet;
  private static String[] manufacturersID = new String[]{"002", "004", "005"};

  /**
   *  When the furniture type requested is a filing, it calls the parent class Furniture to set the
   * ID of the filing, the type of filing, and then the price of the filing, then the individual 
   * components of the filing are assigned one of two characters, 'Y' if the filing has said component
   * or a 'N' if said component is missing, based on the given arguments below. 
   * 
   * @param id : ID used to identify the item
   * @param type : Type of filing
   * @param rail : Contains 'Y' if filing has a rail, 'N' if it doesn't
   * @param draw : Contains 'Y' if filing has a drawer, 'N' if it doesn't
   * @param cab : Contains 'Y' if filing has a cabinet, 'N' if it doesn't
   * @param price : Price of the item
   */
  public Filing(String id, String type, String rail, String draw, String cab, int price){
    super(id, type, price);

    this.rails = rail;
    this.drawers = draw;
    this.cabinet = cab;
  }

  /**
   * @return Returns a variable called rails
   */
  public String getRails(){
    return this.rails;
  }


  /**
   * @return Returns a variable called drawers
   */
  public String getDrawers(){
    return this.drawers;
  }

  /**
   * @return Returns a variable called cabinet
   */
  public String getCabinet(){
    return this.cabinet;
  }
  
  /**
   * @return Returns a String array called manufacturersID
   */
  public static String[] getManufacturers(){
    return manufacturersID;
  }
}
