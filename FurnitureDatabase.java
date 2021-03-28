package edu.ucalgary.ensf409;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class FurnitureDatabase {
    private final String DBURL;
    private final String USERNAME = "scm";
    private final String PASSWORD = "ensf409";
    private Connection dbConnect;
    private ResultSet results;
    private LinkedList furnitureList = new LinkedList();


    public FurnitureDatabase(String URL){
        this.DBURL = URL;
    }

     

    public void initializeConnection(){
        try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
     
    public void createList(String Table){
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM "+Table);

            while(results.next()){
                StringBuffer ID = new StringBuffer();
                StringBuffer Type = new StringBuffer();
                StringBuffer Price = new StringBuffer();
                StringBuffer component1 = new StringBuffer();
                StringBuffer component2 = new StringBuffer();
                StringBuffer component3 = new StringBuffer();
                StringBuffer component4 = new StringBuffer();
                Node temp = new Node();
                ID.append(results.getString("ID")).toString();
                Type.append(results.getString("Type"));
                Price.append(results.getString("Price"));
                temp.ID = ID.toString();
                temp.Type = Type.toString();
                temp.Price = Price.toString();
                if(Table.equals("chair")){
                    component1.append(results.getString("Legs"));
                    component2.append(results.getString("Arms"));
                    component3.append(results.getString("Seat"));
                    component4.append(results.getString("Cushion"));
                    temp.component1 = component1.toString();
                    temp.component2 = component2.toString();
                    temp.component3 = component3.toString();
                    temp.component4 = component4.toString();
                }
                if(Table.equals("desk")){
                    component1.append(results.getString("Legs"));
                    component2.append(results.getString("Top"));
                    component3.append(results.getString("Drawer"));
                    temp.component1 = component1.toString();
                    temp.component2 = component2.toString();
                    temp.component3 = component3.toString();
                }
                if(Table.equals("lamp")){
                    component1.append(results.getString("Base"));
                    component2.append(results.getString("Bulb"));
                    temp.component1 = component1.toString();
                    temp.component2 = component2.toString();
                }
                if(Table.equals("filing")){
                    component1.append(results.getString("Rails"));
                    component2.append(results.getString("Drawers"));
                    component3.append(results.getString("Cabinet"));
                    temp.component1 = component1.toString();
                    temp.component2 = component2.toString();
                    temp.component3 = component3.toString();
                }
                furnitureList.add(temp);
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public int findAveragePrice(){
        int avgPrice = 0;
        this.furnitureList.currentElement = this.furnitureList.head;
        for(int i = 0; this.furnitureList.currentElement != null; i++){
            avgPrice += Integer.parseInt(this.furnitureList.currentElement.Price);
            this.furnitureList.currentElement = this.furnitureList.currentElement.nextElement;
        }
        return (avgPrice/this.furnitureList.size());
    }

    public void countComponents(Node arg){
        //Counts how many components a piece of furniture has
    }
   

    public static void main(String[] args){
        FurnitureDatabase test = new FurnitureDatabase("jdbc:mysql://localhost/inventory");
        test.initializeConnection();
        test.createList("filing");
        test.furnitureList.currentElement = test.furnitureList.head;
        for(int i = 0; test.furnitureList.currentElement != null; i++){
            System.out.print(test.furnitureList.currentElement.ID+" ");
            System.out.print(test.furnitureList.currentElement.Type+" ");
            System.out.print(test.furnitureList.currentElement.Price+" ");
            System.out.print(test.furnitureList.currentElement.component1+" ");
            System.out.print(test.furnitureList.currentElement.component2+" ");
            System.out.print(test.furnitureList.currentElement.component3+" ");
            System.out.print(test.furnitureList.currentElement.component4+" ");
            System.out.print('\n');
            test.furnitureList.currentElement = test.furnitureList.currentElement.nextElement;
        }
        System.out.println(test.findAveragePrice());

    }
}


class Node{
    protected String ID;
    protected String Type;
    protected String Price;
    protected String component1;
    protected String component2;
    protected String component3;
    protected String component4;
    private int componentCount;
    protected Node nextElement;     //Contains the element right after the current element
  
}

class LinkedList{
    protected Node head; //Points to first element
    protected Node currentElement; //Points to the current element you have access to
    protected Node lastElement; //Points to the last element of LinkedList
    private int size = 0; //size of list

    public LinkedList(){ //Creates an empty list
        this.head = null;
        this.currentElement = null;
        this.lastElement = null;
    }

    public int size(){ //Returns size of the list
        return this.size;
    }

    public boolean isEmpty(){ //Returns true if list is empty, false otherwise
        if(this.size == 0){ 
            //Returns true if stack is empty
            return true;
        } else {
            //Returns false otherwise
            return false;
        }
    }

    public void goToEnd(){ //Goes to the end of the list
        this.currentElement = this.lastElement;
    }

    public void next(){ //Goes to the next element
        if(this.currentElement != this.lastElement){
            this.currentElement = this.currentElement.nextElement;
        } else {
            System.out.println("You are currently on the last element of the list.");
            return;
        }
    }



    public void moveToPos(int position) throws IndexOutOfBoundsException{ //Moves to the position specified
        if((position < 0) || (position > this.size)){ 
            //Checks to see if the position specified is within the range of the list
            throw new IndexOutOfBoundsException("Illegal Position");
           
        } 
        
        if(position == 1){ //Checks if the 1st element is wanted
            this.currentElement=this.head;
        }
        
        else{
            this.currentElement = this.head;
            for(int i = 1; i<position; i++){
                this.currentElement = this.currentElement.nextElement;
            }
        }
    }

    public void add(Node arg){ //This version of add() adds a filled element to the end of the list 
        if(this.size == 0){ //If list is empty create the first element
            this.head = arg;
            this.currentElement = this.head;
            this.lastElement = this.head;
            this.currentElement.nextElement = null;
            this.size++;
            
        } 
         else if(this.size > 0){
            Node temp = arg; //Creates a new node
            temp.nextElement = null;
            this.lastElement.nextElement = temp;
            this.lastElement = temp;
            this.size++;  
           
    }
}
}

