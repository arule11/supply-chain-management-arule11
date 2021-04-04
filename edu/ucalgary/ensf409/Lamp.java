package edu.ucalgary.ensf409;

public class Lamp extends Furniture{

  private String base;
  private String bulb;
  private static String[] manufacturersID = new String[]{"002", "004", "005"};

  /**
   * When the furniture type requested is a lamp, it calls the parent class Furniture to set the
   * ID of the lamp, the type of lamp, and then the price of the lamp, then the individual 
   * components of the lamp are assigned one of two characters, 'Y' if the lamp has said component
   * or a 'N' if said component is missing, based on the given arguments below. 
   * @param id : The ID used to identify the item
   * @param type : The type of lamp
   * @param base : Contains 'Y' if lamp has a base, 'N' if it doesn't
   * @param bulb : Contains 'Y' if lamp has a bulb, 'N' if it doesn't
   * @param price : Price of the item
   */
  public Lamp(String id, String type, String base, String bulb, int price){
    super(id, type, price);

    this.base = base;
    this.bulb = bulb;
  }

  /**
   * @return Returns a variable called base
   */
  public String getBase(){
    return this.base;
  }

  /**
   * @return Returns a variable called bulb
   */
  public String getBulb(){
    return this.bulb;
  }

  /**
   * @return Returns a String array called manufacturersID
   */
  public static String[] getManufacturers(){
    return manufacturersID;
  }
}
