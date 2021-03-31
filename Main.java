public class Main{

  public static void main(String[] args) {

    FurnitureDataBase item = new FurnitureDataBase("chair", "Ergonomic", 2);
    item.initializeConnection();
    item.addFurniture();

    item.printFurn();

  }

}
