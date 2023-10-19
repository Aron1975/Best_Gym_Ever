package BestGymEver;

import java.util.ArrayList;
import java.util.List;

public class BestGymMain {

    //List<Customer> customerList = new ArrayList<>();

    public BestGymMain(){
/*
        CustomerCheckIn cci = new CustomerCheckIn();
        String filename = "src/customers.txt";
        customerList = cci.readCustomerFile(filename);*/
    }

    public static void main(String[] args) {
        //String filename = "src/customers.txt";
        CustomerCheckIn cci = new CustomerCheckIn();

       /* List<Customer> customerList= new ArrayList<>();
        customerList = cci.readCustomerFile(filename);
        String input = cci.readReceptionistInput(false, null);
        String input1 = "Maja Andersson";
        String input2 = "8204021234";
        String input3 = "Diamanda Djedi";
        String input4 = "8204021200";
        int customerNr;
        customerNr = cci.checkIfCustomerExists(customerList, input);
        if (customerNr == -1){
            System.out.println("Obehörig.");
        }else if(cci.checkCustomerStatus(customerList.get(customerNr))){
            System.out.println("Behörig");
        }else{
            System.out.println("Behörighet har gått ut.");
        }*/
    }
}
