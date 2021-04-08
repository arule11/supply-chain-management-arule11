/**
@author   Athena McNeil-Roberts : athena.mcneilrobe1@ucalgary.ca
          Nicolas Teng :
          Ivan Lou Tompong : ivanlou.tompong@ucalgary.ca
          Alden Lien :
@version 1.4
@since 1.0
*/
package UnitTests;

import edu.ucalgary.ensf409.*;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class FurnitureDataBaseTest {
    //Data Members
    FurnitureDataBase dataBase;

    @Test
    // Non-static call of data members to check proper initialization
    public void testConstructor3(){
        //Recieving values for the request, type and number of items.
        String expectedRequest = "chair";
        String resultedRequest = FurnitureDataBase.getFurnitureRequest();

        String resultedType = FurnitureDataBase.getRequestType();
        String expectedType = "mesh";

        int resultedNum = FurnitureDataBase.getRequestNum();
        int expectedNum = 2;

        //Checks three data members simultaneously
        assertEquals("Furniture number wasn't correctly stored", expectedNum, resultedNum);
        assertEquals("Type wasn't correctly stored", expectedType,resultedType);
        assertEquals("Request wasn't correctly stored", expectedRequest, resultedRequest);
    }

    @Test
    // setFurnitureRequest with 1 argument
    // setRequestType with 1 argument
    // setRequestNum with 1 argument
    // addFurniture with no arguments
    // getFoundFurniture with no arguments
    public void testSetters3Getter1AddFurniture() throws InvalidOrderException{
        dataBase.setFurnitureRequest("chair");
        dataBase.setRequestType("task");
        dataBase.setRequestNum(2);
        dataBase.addFurniture();
        //Loop through foundFurniture to make sure that all types are the same as specified
        boolean resultType = false;
        System.out.println(dataBase.getFoundFurniture().size());
        for (int i = 0; i < dataBase.getFoundFurniture().size(); i++){
            if  (dataBase.getFoundFurniture().get(i).getType().equalsIgnoreCase("task")){
                resultType = true;
            }else{
                resultType = false;
            }
        }
        assertTrue("Types are not the selected type", resultType);
    }

    @Test
    // addFurniture with no arguments
    // May change "mesh" and "Mesh" to any other type (so long as they're the same)
    // Can be tested with other items as well
    public void testConstructor3AddFurniture() throws InvalidOrderException{
        dataBase.addFurniture();
        //Loop through foundFurniture to make sure that all types are the same as specified
        boolean resultType = false;
        for (int i = 0; i < dataBase.getFoundFurniture().size(); i++){
            if  (dataBase.getFoundFurniture().get(i).getType().equalsIgnoreCase("Mesh")){
                resultType = true;
            }else{
                resultType = false;
            }
        }
        assertTrue("Types are not the selected type", resultType);
    }

    // test invalid order - with furniture that doesnt exist in the database
    @Test(expected = InvalidOrderException.class)
    public void testConstructor3AddFurnitureInvalid() throws InvalidOrderException{
      dataBase = new FurnitureDataBase("water", "bed", 2);
      dataBase.initializeConnection();
      dataBase.addFurniture();
    }

    // test invalid order - with mismatched combination between two different furniture categories
    @Test(expected = InvalidOrderException.class)
    public void testConstructor3AddFurnitureMisMatch() throws InvalidOrderException{
      dataBase = new FurnitureDataBase("Mesh", "Desk", 2);
      dataBase.initializeConnection();
      dataBase.addFurniture();
    }

    @Test
    // getSubsets with 1 argument
    public void testConstructor3GetSubsets1(){
        //Building set to be created into subsets
        ArrayList<Furniture> testSet = new ArrayList<Furniture>();
        Furniture set1 = new Furniture ("D0312", "wooden", 500);
        Furniture set2 = new Furniture ("D0313", "wooden", 200);
        testSet.add(set1);
        testSet.add(set2);

        //Obtaining test subsets
        ArrayList<ArrayList<Furniture>> testSubsets = FurnitureDataBase.getSubsets(testSet);

        int expectedSize = 4;
        int resultedSize = testSubsets.size();

        assertEquals("Expected size of all subsets is incorrect", expectedSize, resultedSize);
    }

    @Test
    // getValid with 1 argument
    public void testConstructor3GetValid1(){
        //Building a expected valid combation list
        ArrayList<ArrayList<Furniture>> expectedValidCombinations = new ArrayList<ArrayList<Furniture>>();
        Chair valid1 = new Chair ("A0104", "mesh" , "Y", "Y", "N", "N", 200);
        Chair valid2 = new Chair ("A0105", "mesh" , "N", "N", "Y", "Y", 200);
        Chair valid3 = new Chair ("A0106", "mesh" , "Y", "N", "Y", "Y", 200);

        //Building Valid list 1
        ArrayList<Furniture> validOne = new ArrayList<Furniture>();
        validOne.add(valid1);
        validOne.add(valid2);

        //Building Valid list 2
        ArrayList<Furniture> validTwo = new ArrayList<Furniture>();
        validTwo.add(valid1);
        validTwo.add(valid3);

        //Filling expected valid list
        expectedValidCombinations.add(validOne);
        expectedValidCombinations.add(validTwo);

        //Building an input list that will be filtered for valid combinations
        ArrayList<ArrayList<Furniture>> inputCombations = new ArrayList<ArrayList<Furniture>>();

        //Building invalid list
        ArrayList<Furniture> invalidOne = new ArrayList<Furniture>();
        invalidOne.add(valid2);
        invalidOne.add(valid3);

        //Filling input list
        inputCombations.add(validOne);
        inputCombations.add(invalidOne);
        inputCombations.add(validTwo);

        ArrayList<ArrayList<Furniture>> resultingValidCombinations = new ArrayList<ArrayList<Furniture>>();
        resultingValidCombinations = dataBase.getValid(inputCombations);

        assertEquals("Valid combations were not selected properly", expectedValidCombinations, resultingValidCombinations);
    }

    @Test
    // comparePrice with 1 argument
    public void testConstructor3ComparePrice(){
        //Building a expected valid combination list
        ArrayList<ArrayList<Furniture>> expectedSortedList = new ArrayList<ArrayList<Furniture>>();
        Chair valid1 = new Chair ("A0104", "mesh" , "Y", "Y", "N", "N", 200);
        Chair valid2 = new Chair ("A0105", "mesh" , "N", "N", "Y", "Y", 150);
        Chair valid3 = new Chair ("A0106", "mesh" , "Y", "N", "Y", "Y", 300);

        //Building Valid list 1 of price 350
        ArrayList<Furniture> validOne = new ArrayList<Furniture>();
        validOne.add(valid1);
        validOne.add(valid2);

        //Building Valid list 2 of price 500
        ArrayList<Furniture> validTwo = new ArrayList<Furniture>();
        validTwo.add(valid1);
        validTwo.add(valid3);

        //Filling expected valid list
        expectedSortedList.add(validOne);
        expectedSortedList.add(validTwo);

        //Building an input list that will be filtered for valid combinations
        ArrayList<ArrayList<Furniture>> inputCombations = new ArrayList<ArrayList<Furniture>>();

        //Filling input list in incorrect price order
        inputCombations.add(validTwo);
        inputCombations.add(validOne);

        ArrayList<ArrayList<Furniture>> resultingSortedList = new ArrayList<ArrayList<Furniture>>();
        resultingSortedList = dataBase.comparePrice(inputCombations);

        assertEquals("Valid combations were not sorted properly", expectedSortedList, resultingSortedList);
    }

    @Test
    // checkChairSubset with 1 argument
    public void testConstructor3CheckChairSubset1(){
        //Building valid combination
        ArrayList<Furniture> chairCombinations = new ArrayList<Furniture>();
        Chair chair1 = new Chair("A0102", "mesh", "N", "Y", "Y", "Y", 150);
        Chair chair2 = new Chair("A0103", "mesh", "Y", "N", "N", "N", 50);
        chairCombinations.add(chair1);
        chairCombinations.add(chair2);

        //Obtaining tested value
        boolean resultedCheck = dataBase.checkChairSubset(chairCombinations);
        boolean expectedCheck = true;

        assertEquals("Validity incorrect",expectedCheck,resultedCheck);
    }

    @Test
    // Constructor created with 3 argument
    // initializeConnection with no arguments
    // produceOrder with no arguments but 1 order
    public void testConstructor3ProduceOrder0(){
        //Initializing FurnitureDataBase with 1 item
        FurnitureDataBase dataBase = new FurnitureDataBase ("mesh", "chair", 1);
        dataBase.initializeConnection();

        //Building Found Furniture to be tested
        ArrayList<Furniture> createdFoundFurniture = new ArrayList<Furniture>();
        Chair chair1 = new Chair("A0102", "mesh", "N", "Y", "Y", "Y", 150);
        Chair chair2 = new Chair("A0103", "mesh", "Y", "N", "N", "N", 50);
        createdFoundFurniture.add(chair1);
        createdFoundFurniture.add(chair2);

        dataBase.setFoundFurniture(createdFoundFurniture);

        //Expected order
        ArrayList<ArrayList<Furniture>> expectedProduceOrder = new ArrayList<ArrayList<Furniture>>();
        expectedProduceOrder.add(createdFoundFurniture);

        //Tested order
        ArrayList<ArrayList<Furniture>> resultedProduceOrder = dataBase.produceOrder();
        assertEquals("Purchased orders do not match", expectedProduceOrder, resultedProduceOrder);
    }

    @Test
    // Check method comboPrice with 1 argumet
    // Checking 2 items
    public void testComboPrice2(){
        //Building subset to be tested
        ArrayList<Furniture> subset = new ArrayList<Furniture>();
        Chair chair1 = new Chair("A0102", "mesh", "N", "Y", "Y", "Y", 150);
        Chair chair2 = new Chair("A0103", "mesh", "Y", "N", "N", "N", 50);
        subset.add(chair1);
        subset.add(chair2);

        //Obtaining results from above subset
        int result = FurnitureDataBase.getComboPrice(subset);
        int expected = 200;
        assertEquals("Summation was incorrect", expected, result);
    }

    @Test
    // Check method comboPrice with 1 argumet
    // Checking 3 items
    public void testComboPrice3(){
        //Building subset to be tested
        ArrayList<Furniture> subset = new ArrayList<Furniture>();
        Chair chair1 = new Chair("A0102", "mesh", "N", "Y", "N", "Y", 150);
        Chair chair2 = new Chair("A0103", "mesh", "Y", "N", "N", "N", 50);
        Chair chair3 = new Chair("A0104", "mesh", "N", "N", "Y", "N", 50);
        subset.add(chair1);
        subset.add(chair2);
        subset.add(chair3);

        //Obtaining results from above subset
        int result = FurnitureDataBase.getComboPrice(subset);
        int expected = 250;
        assertEquals("Summation was incorrect", expected, result);
    }


    /*
    *  Pre- test processes
    */

  @Before
    // Constructor created with 3 arguments
    // initializeConnection with no arguments
  public void start() {
    //Initializing FurnitureDataBase
    dataBase = new FurnitureDataBase ("mesh", "chair", 2);
    dataBase.initializeConnection();
  }
}
