import BestGymEver.Customer;
import BestGymEver.CustomerCheckIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CustomerCheckInTest {

    CustomerCheckIn cci = new CustomerCheckIn();
    List<Customer> list = new ArrayList<>();
    Customer c1 = new Customer("Alhambra Aromes", "7703021234", "2023-07-01");
    Customer c2 = new Customer("Chamade Coriola", "8512021234", "2018-03-12");
    Customer c3 = new Customer("Ida Idylle", "8906138493", "2018-03-07");

    @Test
    void readCustomerFileTest() {
        list = cci.readCustomerFile("src/customers.txt");
        assert(list.size()==14);
        assert(list.size()!=5);
        assertEquals(list.get(2).getSocSecNr(), "8512021234");
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
        String input = "blablabla";
        String output = cci.readReceptionistInput(true, input);
        assertEquals(output, "Test");
        assertNotEquals(output, " ");
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
        String dateNow = LocalDate.now().toString();
        assert(cci.getDate().equals(dateNow));
    }

    @Test
    void printPermission(){

    }

    @Test
    void writeToPtFileTest() {
    }
}