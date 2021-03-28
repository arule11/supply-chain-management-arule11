package edu.ucalgary.ensf409;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;







public class FurnitureDatabase {
    private final String DBURL; //Database URL
    private final String USERNAME = "scm"; //MySQL username
    private final String PASSWORD = "ensf409"; //MySQL password
    private Connection dbConnect;
    private ResultSet results;
    private LinkedList furnitureList = new LinkedList(); 
    private LinkedList listSortedByType = new LinkedList();

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
     
    public void createList(String Table){ //Converts the specified database table into a linked list
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
                countComponents(temp);
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
        if(arg.component1 != (null)&&arg.component1.equals("Y")){
            arg.componentCount+=1;
        }
        if(arg.component2!= (null)&&arg.component2.equals("Y")){
            arg.componentCount+=1;
        }
        if(arg.component3!= (null)&&arg.component3.equals("Y")){
            arg.componentCount+=1;
        }
        if(arg.component4!= (null)&&arg.component4.equals("Y")){
            arg.componentCount+=1;
        }
        
    }
 

    public void sortByType(String type){ //Creates a linked list containing the furniture type specified
        for(int i = 1; i <= furnitureList.size(); i++){
            furnitureList.moveToPos(i);
            if(furnitureList.currentElement.Type.equals(type)){
                Node temp = new Node();
                temp.ID = furnitureList.currentElement.ID;
                temp.Type = furnitureList.currentElement.Type;
                temp.Price = furnitureList.currentElement.Price;
                temp.component1 = furnitureList.currentElement.component1;
                temp.component2 = furnitureList.currentElement.component2;
                temp.component3 = furnitureList.currentElement.component3;
                temp.component4 = furnitureList.currentElement.component4;
                temp.componentCount = furnitureList.currentElement.componentCount;
                listSortedByType.add(temp);
            }
        }
    }

    public void findCombinations(){ //W.I.P
        
    }
   

    public static void main(String[] args){
        FurnitureDatabase test = new FurnitureDatabase("jdbc:mysql://localhost/inventory");
        test.initializeConnection();
        test.createList("chair");
        test.sortByType("Task");
        test.furnitureList.currentElement = test.furnitureList.head;
        test.listSortedByType.currentElement = test.listSortedByType.head;
        for(int i = 0; test.furnitureList.currentElement != null; i++){
            System.out.print(test.furnitureList.currentElement.ID+" ");
            System.out.print(test.furnitureList.currentElement.Type+" ");
            System.out.print(test.furnitureList.currentElement.Price+" ");
            if(test.furnitureList.currentElement.component1!=null){
            System.out.print(test.furnitureList.currentElement.component1+" ");
            }
            if(test.furnitureList.currentElement.component2!=null){
            System.out.print(test.furnitureList.currentElement.component2+" ");
            }
            if(test.furnitureList.currentElement.component3!=null){
            System.out.print(test.furnitureList.currentElement.component3+" ");
            }
            if(test.furnitureList.currentElement.component4!=null){
            System.out.print(test.furnitureList.currentElement.component4+" ");
            }
            System.out.print(test.furnitureList.currentElement.componentCount+" ");
            System.out.print('\n');
            System.out.print('\n');
            
            test.furnitureList.currentElement = test.furnitureList.currentElement.nextElement;
        }
        for(int i = 0; test.listSortedByType.currentElement != null; i++){
            System.out.print(test.listSortedByType.currentElement.ID+" ");
            System.out.print(test.listSortedByType.currentElement.Type+" ");
            System.out.print(test.listSortedByType.currentElement.Price+" ");
            if(test.listSortedByType.currentElement.component1!=null){
            System.out.print(test.listSortedByType.currentElement.component1+" ");
            }
            if(test.listSortedByType.currentElement.component2!=null){
            System.out.print(test.listSortedByType.currentElement.component2+" ");
            }
            if(test.listSortedByType.currentElement.component3!=null){
            System.out.print(test.listSortedByType.currentElement.component3+" ");
            }
            if(test.listSortedByType.currentElement.component4!=null){
            System.out.print(test.listSortedByType.currentElement.component4+" ");
            }
            System.out.print(test.listSortedByType.currentElement.componentCount+" ");
            System.out.print('\n');
            test.listSortedByType.currentElement = test.listSortedByType.currentElement.nextElement;
        }

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
    protected int componentCount = 0;
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

