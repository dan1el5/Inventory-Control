/**
 * Inventory
 * Class that includes methods for checking inventory items that are too high/too low
 * Daniel Gomes
 * ICS 4U1
 * June 9, 2021
 */

import java.io.*;
public class Inventory {
  // Declare variables (these variables could be local, but in that case I would have no variables to declare or put in the constructor.)
  boolean low; // Variable to check if inventory count is low
  boolean high; // Variable to check if inventory count is high
  
  // Data Files
  BufferedReader in = null;
  String line = "Item, 10, 38.5";
  File f = new File("CPTInventory.txt");
  
  /*
   * Constructor
   * Precondition: N/A
   * Postcondition: Variables are initialized
   */
  public Inventory() { 
    boolean low = true;
    boolean high = true;
  }
  
  /*
   * checkLow
   * Precondition: N/A
   * Postcondition: low value is returned either true of false
   */
  public boolean checkLow(double quantity) {
    if (quantity <= 10){ // quantity is considered low if less than or equal to 10
      low = true; // returns true
    }else{
      low = false; // returns false
    }
    return (low); // returns value to main method
  }
  
  /*
   * checkHigh
   * Precondition: N/A
   * Postcondition: high value is returned either true or false
   */
  public boolean checkHigh(double quantity) {
    if (quantity >= 100){ // quantity is considered high if greater than or equal to 100
      high = true; // returns true
    }else{
      high = false; // returns false
    }
    return (high); // returns value to main method
  }
  
}
