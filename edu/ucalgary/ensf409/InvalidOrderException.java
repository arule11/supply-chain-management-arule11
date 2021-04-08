package edu.ucalgary.ensf409;
/**
* Class representing a InvalidOrderException
*/
public class InvalidOrderException extends Exception{
  public InvalidOrderException(){
    super("Order must contain a valid furniture and a corresponding category, with an amount");
  }
}
