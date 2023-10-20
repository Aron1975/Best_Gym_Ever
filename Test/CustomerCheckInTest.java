import BestGymEver.Customer;
import BestGymEver.CustomerCheckIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CustomerCheckInTest {

    CustomerCheckIn cci = new CustomerCheckIn(true);
    List<Customer> list = new ArrayList<>();
    Customer c1 = new Customer("Alhambra Aromes", "7703021234", "2023-07-01");
    Customer c2 = new Customer("Chamade Coriola", "8512021234", "2018-03-12");
    Customer c3 = new Customer("Ida Idylle", "8906138493", "2018-03-07");

    @Test
    void readCustomerFileTest() {
        String existingCorrectFile = "src/customers.txt";
        String nonExistingFile = "src/custom.txt";
        String existingCorruptFile = "src/customersCorrupt.txt";
        list = cci.readCustomerFile(existingCorrectFile);
        assert(list.size()==14);
        assert(list.size()!=5);
        assertEquals(list.get(2).getSocSecNr(), "8512021234");
        assertNotEquals(list.get(0).getSocSecNr(), "0000000000");
        list = cci.readCustomerFile(nonExistingFile);
        assert(list.isEmpty());
        list = cci.readCustomerFile(existingCorruptFile);
        assert(list.size()==3);
        assert(list.size()!=5);
    }

    @Test
    void addCustomerToListTest() {
        List<Customer> list2 = new ArrayList<>();
        list2 = cci.addCustomerToList(list2, c1);
        assertEquals(list2.get(0).getName(),"Alhambra Aromes");
        assertNotEquals(list2.get(0).getName(),"Alba Aromes");
    }

    @Test
    void readReceptionistInputTest() {
        String output = cci.readReceptionistInput();
        assertEquals(output, "Test");
        assertNotEquals(output, "");
    }

    @Test
    void checkReceptionistInputTest() {
        String okInput1 = "Anders Andersson";
        String okInput2 = "1234567890";
        String okInput3 = "1212121234";
        String badInput1 = "Adam99";
        String badInput2 = "¤&/()¤vcffh";
        String badInput3 = "123456";

        assert(cci.checkReceptionistInput(okInput1));
        assert(cci.checkReceptionistInput(okInput2));
        assert(cci.checkReceptionistInput(okInput3));
        assert(!cci.checkReceptionistInput(badInput1));
        assert(!cci.checkReceptionistInput(badInput2));
        assert(!cci.checkReceptionistInput(badInput3));
    }

    @Test
    void checkDataFromFileTest(){

        String name = "adg";
        String socNr ="1234567890";
        String date = "2011-12-13";
        assert(cci.checkDataFromFile(name, 2));
        assert(cci.checkDataFromFile(socNr, 1));
        assert(cci.checkDataFromFile(date, 3));
        String badName = "adg*24";
        String badSocNr ="123456890";
        String badDate = "2011-1213";
        assert(!cci.checkDataFromFile(badName, 2));
        assert(!cci.checkDataFromFile(badSocNr, 1));
        assert(!cci.checkDataFromFile(badDate, 3));
    }

    @Test
    void checkIfCustomerExistsTest() {
        String correctSocNr = "7703021234";
        String inCorrectSocNr = "7510110000";
        String correctName = "Ida Idylle";
        String inCorrectName = "Mammut";
        list.add(c1);
        list.add(c2);
        list.add(c3);
        assert(cci.checkIfCustomerExists(list, correctName)>=0);
        assert(cci.checkIfCustomerExists(list, inCorrectName)==-1);
        assert(cci.checkIfCustomerExists(list, correctSocNr)>=0);
        assert(cci.checkIfCustomerExists(list, inCorrectSocNr)==-1);
    }

    @Test
    void checkCustomerStatusTest() {
        assert(cci.checkCustomerStatus(c1));
        assert(!cci.checkCustomerStatus(c2));
    }

    @Test
    void getDateTest() {
        String dateNow = (LocalDate.now()).toString();
        assert(cci.getDate().equals(dateNow));
    }

    @Test
    void printPermissionTest(){
        list.add(c1);
        list.add(c2);
        list.add(c3);
        int customerIndex = 2;
        assertEquals(cci.printPermission(list, customerIndex), "Föredetta kund.");
        customerIndex = 0;
        assertEquals(cci.printPermission(list, customerIndex), "Nuvarande kund.");
        assertEquals(cci.printPermission(list, -1), "Obehörig.");
    }

    @Test
    void writeToPTFileTest() throws IOException{
        String testFilename = "src/testFile.txt";
        String line = "";
        Path path = Paths.get(testFilename);
        String dateNow = (LocalDate.now()).toString();
        cci.writeToPTFile(testFilename, c1);
        assert(Files.exists(path));
        Scanner sc = new Scanner(new FileReader(testFilename));
        assert(sc.hasNextLine());
        while(sc.hasNextLine()) {
            line = sc.nextLine();
        }
        String stringToCompare = "Alhambra Aromes, 7703021234, " + dateNow;
        assertEquals(line,stringToCompare);
    }
}