/**
@author  Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng : nicolas.teng@ucalgary.ca
          Ivan Lou Tompong : ivanlou.tompong@ucalgary.ca
          Alden Lien :
@version 1.1
@since 1.0
*/

package edu.ucalgary.ensf409;
/**
* Class representing a InvalidOrderException
*/
public class InvalidOrderException extends Exception{
  public InvalidOrderException(){
    super("Order must contain a valid furniture and a corresponding category, with an amount");
  }
}
