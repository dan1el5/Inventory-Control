/**
 * Inventory Control CPT
 * Program with multiple features that allows user to view, control and manipulate an inventory of items
 * Daniel Gomes
 * ICS 4U1
 * June 8,2021
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryControlCPT implements ActionListener {
  Inventory check = new Inventory(); // Inventory class
  
  // Variables and GUI objects declaration
  JFrame frame;
  JPanel contentPane;
  
  // Buttons
  static JButton valueInvButton; // value inventory button
  static JButton lowInvButton; // low inventory button
  static JButton highInvButton; // high inventory button
  static JButton saveButton; // save button
  static JButton updatePriceButton; // update price button
  static JButton meanQButton; // mean quantity button
  static JButton meanPButton; // mean price button
  static JButton[] aButton; // add inventory button
  static JButton[] dButton; // delete inventory button
  static JButton[] vButton; // value item button
  
  // Labels
  static JLabel[] lblItem; // item label
  static JLabel[] lblItemQ; // item quantity label
  static JLabel[] lblItemP; // item price label
  static JLabel[] lblHeadings; // headings label
  
  // Text Fields
  static JTextField[] txtQChange; // text field for user to input quantities
  
  // Arrays
  static double[] itemQ = new double[6]; // item quantity array (the reason I have quantity as a double type is because of my mean quantity button. if i left it as int, the mean quantity would have been an int value which would result in the wrong value.)
  static double[] itemP = new double[6]; // item price array
  String [] itemName = new String[6]; // item name array
  String [] fields; // fields array
  
  // Item Variables
  static String name = " "; // variable to store name string
  static double quantity = 0; // variable to store quantity value
  static double price = 0; // variable to store price value
  
  // Data Files
  BufferedReader in = null;
  String line = "Item, 10.0, 38.5";
  File f = new File("CPTInventory.txt");
  
  public InventoryControlCPT(){
    // open data file for reading
    try{
      in = new BufferedReader(new FileReader(f));
      System.out.println("File opening");
    } catch (FileNotFoundException e) {
      System.out.println("Problem opening file");
    }
    
    // read a line
    do {
      try{
        line = in.readLine();
      } catch (IOException e) {
        System.out.println("Problem reading data from file");
      }
      
      // read data from records - data all on 1 line, 6 records with 3 fields per record
      if (line!=null){
        for (int i=0;i<6;i++){   
          fields=line.split(",");
          if (i==0){
            itemName[i]=fields[i];
            itemQ[i]=Double.parseDouble(fields[i+1]);
            itemP[i]=Double.parseDouble(fields[i+2]);
          }else{
            itemName[i]=fields[i+(i*2)];
            itemQ[i]=Double.parseDouble(fields[(i+(i*2))+1]);
            itemP[i]=Double.parseDouble(fields[(i+(i*2))+2]);          
          }
        }
      }
    }while (line!=null);
    
    // close file, check if there is a problem closing
    try {
      in.close();
      System.out.println("Closing File");
    } catch (IOException e) {
      System.out.println("Problem Closing " + e);
    }
    
    // Headings
    String[] headings = {"Item", "Quantity", "Price Per Unit", "Enter Whole Quantity", " ", " ", " ",}; // column headings
    
    // Create objects in constructor
    frame = new JFrame("Inventory Control"); // frame title
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close button
    
    // Create Borders, Columns and Rows
    contentPane = new JPanel();
    contentPane.setLayout(new GridLayout(itemName.length+2,6,10,5)); // set up grid layout
    contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // set up borders
    contentPane.setBackground(Color.lightGray); // changes background colour to light gray
    
    // Create an array of GUI elements
    aButton = new JButton[itemName.length];
    dButton = new JButton[itemName.length];
    vButton = new JButton[itemName.length];
    txtQChange = new JTextField[itemName.length];
    lblItem = new JLabel[itemName.length];
    lblItemQ = new JLabel[itemName.length];
    lblItemP = new JLabel[itemName.length];
    lblHeadings = new JLabel[headings.length];
    
    // Create Buttons
    valueInvButton = new JButton("Inventory Value");
    lowInvButton = new JButton("Low Inventory");
    highInvButton = new JButton("High Inventory");
    saveButton = new JButton("Save Inventory");
    updatePriceButton = new JButton("Update Prices");
    meanQButton = new JButton("Mean Quantity");
    meanPButton = new JButton("Mean Price");
    
    // For loop to display headings
    for (int i = 0; i < headings.length; i++){
      lblHeadings[i] = new JLabel(headings[i]);
      contentPane.add(lblHeadings[i]);
    }
    
    // for loop to create and add labels, text fields and buttons to the content pane and action listener
    for (int i = 0; i < itemName.length; i++){
      lblItem[i] = new JLabel(itemName[i]);
      lblItemQ[i] = new JLabel(String.valueOf(itemQ[i]));
      lblItemP[i] = new JLabel(String.valueOf(itemP[i]));
      txtQChange[i] = new JTextField();
      aButton[i] = new JButton("Add"); 
      dButton[i] = new JButton("Delete");
      vButton[i] = new JButton(itemName[i] + " Value"); // individual item name to also be displayed on button
      aButton[i].addActionListener(this);
      dButton[i].addActionListener(this);
      vButton[i].addActionListener(this);
      contentPane.add(lblItem[i]);
      contentPane.add(lblItemQ[i]);
      contentPane.add(lblItemP[i]);
      contentPane.add(txtQChange[i]);
      contentPane.add(aButton[i]);
      contentPane.add(dButton[i]);
      contentPane.add(vButton[i]);
    }
    
    // Add buttons to action listener
    saveButton.addActionListener(this);
    updatePriceButton.addActionListener(this);
    valueInvButton.addActionListener(this);
    lowInvButton.addActionListener(this);
    highInvButton.addActionListener(this);
    meanQButton.addActionListener(this);
    meanPButton.addActionListener(this);
    
    // Add Buttons to content pane
    contentPane.add(saveButton);
    contentPane.add(updatePriceButton);
    contentPane.add(valueInvButton);
    contentPane.add(lowInvButton);
    contentPane.add(highInvButton);
    contentPane.add(meanQButton);
    contentPane.add(meanPButton);
    
    // Create frame
    frame.setContentPane(contentPane); // create frame
    frame.setSize(1000,300); // frame dimensions
    frame.setVisible(true); // make frame visible
  }
  
  // Action performed for add, delete and value inventory buttons
  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < itemQ.length; i++){
      if (e.getSource()== aButton[i]){ // if add quantity button is clicked
        addInventory(i); // calls add inventory method
      } else if (e.getSource()== dButton[i]){ // if delete quantity button is clicked
        deleteInventory(i); // calls delete inventory method
      } else if (e.getSource()==vButton[i]){ // if value item button is clicked
        valueItem(i); // calls value item method
      }
    }
    
    // if save button is pressed
    if (e.getSource()==saveButton){
      int a = JOptionPane.showConfirmDialog(null, "Confirm inventory overwrite?"); // prompts user to confrim save
      if(a==JOptionPane.YES_OPTION){ // if user confirms inventory overwrite
        updateInventory(); // calls update inventory method
        JOptionPane.showMessageDialog(null, "Changes saved."); // displays changes have been saved
      }
    }
    
    // if low inventory button is pressed
    if (e.getSource()==lowInvButton){
      for (int i=0; i<itemQ.length; i++){
        quantity = itemQ[i]; // stores item quantity array in quantity variable
        name = itemName[i]; // stores item name array in name variable
        
        boolean lowInv = check.checkLow(quantity); // calls checkLow() method from Inventory class
        
        if (lowInv == true){ // if inventory count is low
          JOptionPane.showMessageDialog(null, name + " inventory is low on stock."); // displays item is low on stock
        }else{ // if inventory count is not low
          JOptionPane.showMessageDialog(null, name + " inventory is above the minimum standard."); // display item is not low on stock
        }
      }
    }
    
    // if high inventory button is pressed
    if (e.getSource()==highInvButton){
      for (int i=0; i<itemQ.length; i++){
        quantity = itemQ[i]; // stores item quantity array in quantity variable
        name = itemName[i]; // stores item name array in name variable
        
        boolean highInv = check.checkHigh(quantity); // calls checkHigh() method from Inventory class
        
        if (highInv == true){ // if inventory count is high
          JOptionPane.showMessageDialog(null, name + " inventory is high on stock."); // displays item is high on stock
        }else{ // if inventory count is not high
          JOptionPane.showMessageDialog(null, name + " inventory is below the maximum standard."); // displays item is not high on stock
        }
      }
    }
    
    // if value total inventory button is pressed
    if (e.getSource()==valueInvButton){
      valueTotalInventory(); // calls value total inventory method
    }
    
    // if value mean quantity button is pressed
    if (e.getSource()==meanQButton){
      meanQuantity(); // calls mean quantity method
    }
    
    // if value mean price button is pressed
    if (e.getSource()==meanPButton){
      meanPrice(); // calls mean price method
    }
    
    // if update price button is pressed
    if (e.getSource()==updatePriceButton){
      updatePrice(); // calls update price methpd
    }
  }
  
  /*
   * Update Inventory
   * Precondition: N/A
   * Postcondition: Inventory changes are saved, when the user closes and opens the program the changes will remain changed.
   */
  public void updateInventory() {
    PrintWriter out = null;
    String line = "";
    File source = new File("CPTInventory.txt");
    try{
      out = new PrintWriter(new BufferedWriter(new FileWriter(source, false)), true);
      System.out.println("File Opening");
    } catch (IOException e){
      System.out.println("Problem opening file");
    }
    
    for (int i=0; i<itemQ.length; i++){
      name = itemName[i]; // stores item quantity array in quantity variable
      quantity = itemQ[i]; // stores item name array in name variable
      price = itemP[i]; // stores item price array in price variable
      line = name + "," + quantity + "," + price + ","; // updates data file with new values through variables
      out.print(line);
    }
    out.close();
  }
  
  /*
   * Add inventory
   * Precondition: N/A
   * Postcondition: Inventory quantity is added and changed in the data file
   */
  public void addInventory(int k){
    int quantity = 0; // quantity variable
    quantity = Integer.parseInt(txtQChange[k].getText());  // stores text field input in quantity variable
    itemQ[k] = itemQ[k]+quantity; // adds quantity to item quantity array
    lblItemQ[k].setText(String.valueOf(itemQ[k])); // updates label
  }
  
  /*
   * Delete inventory
   * Precondition: N/A
   * Postcondition: Inventory quantity is deleted and changed in the data file
   */
  public void deleteInventory(int k){
    int quantity = 0; // quantity variable
    quantity = Integer.parseInt(txtQChange[k].getText()); // stores text field input in quantity variable
    itemQ[k] = itemQ[k]-quantity; // deletes quantity from item quantity array
    if (itemQ[k] < 0){ // if the item quantity goes below zero
      JOptionPane.showMessageDialog(null, "The quantity you are trying to delete goes below 0. The item count will now be changed to 0."); // dispays that quantity goes below zero
      itemQ[k] = 0; // sets item quantity back to zero
    }
    lblItemQ[k].setText(String.valueOf(itemQ[k])); // updates label
  }
  
  /*
   * Value of an item in the inventory
   * Precondition: N/A
   * Postcondition: Value of singular item is displayed
   */
  public void valueItem(int k){
    double value; // value variable
    value = itemQ[k] * itemP[k]; // value is item quantity times item price
    value = Math.round(value * 100); // round decimal place
    value = value/100; // round decimal place
    JOptionPane.showMessageDialog(null, "The value of this item is: $" + value); // displays value of item
  }
  
  /*
   * Value of the total inventory
   * Precondition: N/A
   * Postcondition: Value of total inventory is displayed
   */
  public void valueTotalInventory(){
    double totalValue = 0; // total value variable
    for (int i=0; i < itemQ.length; i++){
      totalValue = totalValue + itemQ[i] * itemP[i]; // adds value of all items to totalValue
    }
    totalValue = Math.round(totalValue * 100); // round decimal place
    totalValue = totalValue/100; // round decimal place
    JOptionPane.showMessageDialog(null, "The total value of the inventory is: $" + totalValue); // displays total value of inventory
  }
  
  /*
   * Mean quantity of the inventory
   * Precondition: N/A
   * Postcondition: Mean quantity is displayed
   */
  public void meanQuantity(){
    double meanQ = 0; // mean quantity variable
    for (int i=0; i < itemQ.length; i++){
      meanQ += itemQ[i]/6; // adds total inventory quantity to meanQ and divideds that value by 6
    }
    meanQ = Math.round(meanQ * 100); // round decimal place
    meanQ = meanQ/100; // round decimal place
    JOptionPane.showMessageDialog(null, "The mean of the total quantity of items is " + meanQ); // displays mean quantity
  }

  /*
   * Mean price of the list of prices
   * Precondition: N/A
   * Postcondition: Mean price is displayed
   */
  public void meanPrice(){
    double meanP = 0; // mean price variable
    for (int i=0; i < itemQ.length; i++){
      meanP += itemP[i]/6; // adds list of inventory prices to meanP and divideds that value by 6
    }
    meanP = Math.round(meanP * 100); // round decimal place
    meanP = meanP/100; // round decimal place
    JOptionPane.showMessageDialog(null, "The mean of the list of prices is $" + meanP); // displays mean price
  }
  
  /*
   * Update the price of an item
   * Precondition: N/A
   * Postcondition: Item price is updated
   */
  public void updatePrice(){
    double price = 0; // price variable
    for (int i=0; i < itemQ.length; i++){
      name = itemName[i]; // item name array
      String newPrice = JOptionPane.showInputDialog(null, "Enter new price for " + name + ":"); // prompts user for new item price
      price = Double.parseDouble(newPrice); // stores newPrice value in price variable
      lblItemP[i].setText(String.valueOf(price)); // update label
      itemP[i] = price; // stores item price in price so that the data file updates
    }
  }
  
  // main method
  public static void main(String[] args) { 
    new InventoryControlCPT(); // runs inventory control class
  }
}
