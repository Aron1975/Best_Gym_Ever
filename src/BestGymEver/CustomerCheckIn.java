package BestGymEver;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerCheckIn {

    private boolean isTest = false;

    public CustomerCheckIn(boolean isTest) {
        this.isTest = isTest;
    }

    public CustomerCheckIn() {
        String filename = "src/customers.txt";
        //String filename = "src/customersCorrupt.txt";
        List<Customer> customerList = readCustomerFile(filename);

        while(true) {
            String input = readReceptionistInput();
            int customerIndex = checkIfCustomerExists(customerList, input);
            printPermission(customerList, customerIndex);

            String[] options = {"JA", "NEJ"};
            int selection = JOptionPane.showOptionDialog(null, "Vill du ha info om en annan besökare?", "Best Gym Ever",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (selection == 1) {
                break;
            }
        }
    }


    public List<Customer> readCustomerFile(String filename) {
        String line, secNr, name, date = "";
        String[] split;

        List<Customer> list = new ArrayList<>();
        try (Scanner sc = new Scanner(new FileReader(filename))) {
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                split = line.split(",", 2);
                secNr = split[0].trim();
                name = split[1].trim();
                if (sc.hasNextLine()) {
                    date = sc.nextLine();
                }
                Customer c = new Customer(name, secNr, date);
                list = addCustomerToList(list, c);
            }
        } catch (FileNotFoundException e) {
            if (isTest) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, "Filen " + filename + " existerar inte.");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Customer> addCustomerToList(List<Customer> l, Customer c) {
        l.add(c);
        return l;
    }

    public String readReceptionistInput() {
        String input = "";
        if (!isTest) {
            boolean correctInput = false;
            while (!correctInput) {
                try {
                    input = JOptionPane.showInputDialog(null, "Ange besökarens namn/personnummer: ").trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                correctInput = checkReceptionistInput(input);
                if (!correctInput) {
                    JOptionPane.showMessageDialog(null, "Felaktig inmatning.");
                }
            }
        } else {
            input = "Test";
        }
        return input;
    }

    //Check if input is letters and spaces or 10digits, if so return true.
    public boolean checkReceptionistInput(String s) {
        if ((s.matches("[A-Za-z ÅåÄäÖö]+")) || ((s.matches("[0-9]+")) && (s.length() == 10))) {
            return true;
        }
        return false;
    }

    //Return place in List if exists, -1 if customer doesn't exist.
    public int checkIfCustomerExists(List<Customer> l, String s) {

        for (int i = 0; i < l.size(); i++) {
            if ((l.get(i).getName().equals(s)) || (l.get(i).getSocSecNr().equals(s))) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkCustomerStatus(Customer c) {
        String ptFilename = "src/PTFile.txt";
        String date = c.getLastPaidAnnualFee();
        String[] split;
        split = date.split("-", 3);
        try {
            int year = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int day = Integer.parseInt(split[2]);
            LocalDate dateBefore = LocalDate.of(year, month, day);
            LocalDate dateNow = LocalDate.now();
            Period p = Period.between(dateBefore, dateNow);
            if (p.getYears() == 0) {
                writeToPTFile(ptFilename,c);  //???? Ska jag ha den här???? <<--------------
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Fel datumformat, kunden finns men kan inte avgöra om kunden är behörig");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String printPermission(List<Customer> list, int index) {
        if (index == -1) {
            if (!isTest) {
                JOptionPane.showMessageDialog(null, "Besökaren är obehörig.");
            } else {
                return "Obehörig.";
            }
        }else{
            if(checkCustomerStatus(list.get(index))){
                if (!isTest) {
                    JOptionPane.showMessageDialog(null, "Nuvarande medlem.");

                } else {
                    return "Nuvarande kund.";
                }
            }else{
                if (!isTest) {
                    JOptionPane.showMessageDialog(null, "Medlemens behörighet har gått ut.");
                } else {
                    return "Föredetta kund.";
                }
            }
        }
        return "";
    }

    public String getDate() {
        return LocalDate.now().toString();
    }

    public void writeToPTFile(String filename, Customer c) throws IOException {
        String line1 = c.getName() + ", " + c.getSocSecNr() + ", " + getDate();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))){
            bw.write(line1);
            bw.newLine();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
