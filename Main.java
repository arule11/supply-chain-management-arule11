public class Main{

  public static void main(String[] args) {
    
    FurnitureDataBase item = new FurnitureDataBase("lamp", "Desk", 10);
    FurnitureDataBase item2 = new FurnitureDataBase("lamp", "Study", 2);
    FurnitureDataBase.addAvailabilityColToTable(); //
    item.addFurniture();
    item2.addFurniture();
    item.printFurn();
    System.out.println(".......");
    //item.findChairCombos();
    item.printSubSets();
    System.out.println(".......");
    item2.printFurn();
    item2.printSubSets();

    // FurnitureDataBase item2 = new FurnitureDataBase("Desk", "Adjustable", 1);
    // item2.initializeConnection();
    // item2.addFurniture();
    //
    // item2.printFurn();
    // item2.printSubSets();
  }

}
