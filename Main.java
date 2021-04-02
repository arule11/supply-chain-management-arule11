public class Main{

  public static void main(String[] args) {

    FurnitureDataBase item = new FurnitureDataBase("chair", "Ergonomic", 1);
    item.initializeConnection();
    item.addFurniture();

    item.printFurn();
    System.out.println(".......");
    //item.findChairCombos();
    item.printSubSets();

    // FurnitureDataBase item2 = new FurnitureDataBase("Desk", "Adjustable", 1);
    // item2.initializeConnection();
    // item2.addFurniture();
    //
    // item2.printFurn();
    // item2.printSubSets();
  }

}
