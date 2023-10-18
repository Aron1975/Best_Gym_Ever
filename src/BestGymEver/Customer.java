package BestGymEver;

import java.util.List;

public class Customer {

    private String name;
    private String socSecNr;
    private String lastPaidAnnualFee;
    private List<String> workoutDates;

    public Customer(String name, String socSecNr, String lastPaidAnnualFee) {
        this.name = name;
        this.socSecNr = socSecNr;
        this.lastPaidAnnualFee = lastPaidAnnualFee;
    }

    public Customer(String name, String socSecNr, String lastPaidAnnualFee, List<String> workoutDates) {
        this.name = name;
        this.socSecNr = socSecNr;
        this.lastPaidAnnualFee = lastPaidAnnualFee;
        this.workoutDates = workoutDates;
    }

    public String getName() {
        return name;
    }

    public String getSocSecNr() {
        return socSecNr;
    }

    public String getLastPaidAnnualFee() {
        return lastPaidAnnualFee;
    }
}
