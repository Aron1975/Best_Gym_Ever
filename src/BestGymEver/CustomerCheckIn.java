package BestGymEver;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerCheckIn {

    //List<Customer> customerList = new ArrayList<>();

    public CustomerCheckIn(){

    }

    public List<Customer> readCustomerFile(String filename){
        String line, secNr, name, date;
        String[] split;

        List<Customer> list = new ArrayList<>();
        try(Scanner sc = new Scanner(new FileReader(filename))){
            while(sc.hasNextLine()){
                line=sc.nextLine();
                split = line.split(",", 2);
                secNr = split[0].trim();
                name = split[1].trim();
                date = sc.nextLine();
                Customer c = new Customer(name, secNr, date);
                list = addCustomerToList(list,c);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Customer> addCustomerToList(List<Customer> l, Customer c){
        l.add(c);
        return l;
    }

    public String readReceptionistInput(boolean isTest, String testInput){
        String input = "";
        if(!isTest) {
            boolean correctInput = false;
            while (!correctInput) {
                try {
                    input = JOptionPane.showInputDialog(null, "Ange kund: ").trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                correctInput = checkReceptionistInput(input);
                if (!correctInput) {
                    JOptionPane.showMessageDialog(null, "Felaktig inmatning.");
                }
            }
        }else {
            input = "Test";
        }
        return input;
    }

    public boolean checkReceptionistInput(String s){
        if((s.matches("[A-Za-z ÅåÄäÖö]+"))||((s.matches("[0-9]+"))&&(s.length()==10))){
            return true;
        }
            return false;
    }

    public int checkIfCustomerExists(List<Customer> l, String s){

        for(int i= 0; i<l.size();i++){
            if((l.get(i).getName().equals(s))||(l.get(i).getSocSecNr().equals(s))){
                return i;
            }
        }
        return -1;
    }

    public boolean checkCustomerStatus(Customer c){
        String date = c.getLastPaidAnnualFee();
        String[] split;
        split = date.split("-",3);
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        LocalDate dateBefore = LocalDate.of(year,month,day);
        LocalDate dateNow = LocalDate.now();
        Period p = Period.between(dateBefore, dateNow);
        if (p.getYears()==0){
            return true;
        }
        return false;
    }

    public String getDate(){
        return LocalDate.now().toString();
    }

    public void writeToPtFile(Customer c){

    }

}
