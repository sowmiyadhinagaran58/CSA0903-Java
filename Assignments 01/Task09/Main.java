interface Chargeable {

    double chargingFee(int kWh);

    // Default method
    default void chargingStatus() {
        System.out.println("Charging supported");
    }

    // Static method
    static double standardChargingRate() {
        return 12.0;
    }
}

// Abstract class
abstract class Vehicle {

    private final String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    // Abstract method
    protected abstract double ratePerHour();

    // Concrete method
    public double calculateFee(int minutes) {
        int hours = (minutes + 59) / 60;
        return hours * ratePerHour();
    }
}

// TwoWheeler class
class TwoWheeler extends Vehicle {

    TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 10;
    }
}

// Car class
class Car extends Vehicle {

    Car(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 30;
    }
}

// ElectricCar class
class ElectricCar extends Car implements Chargeable {

    ElectricCar(String regNo) {
        super(regNo);
    }

    @Override
    public double chargingFee(int kWh) {
        return kWh * Chargeable.standardChargingRate();
    }
}

// Main class
public class Main {

    public static void main(String[] args) {

        Vehicle v1 = new TwoWheeler("TN01A1");
        Vehicle v2 = new Car("TN02B2");
        ElectricCar v3 = new ElectricCar("TN03C3");

        System.out.println("Campus Smart Parking System");

        System.out.println("\nVehicle Parking Fees:");

        System.out.println(v1.getRegNo() +
                " -> Rs " + v1.calculateFee(130));

        System.out.println(v2.getRegNo() +
                " -> Rs " + v2.calculateFee(130));

        System.out.println(v3.getRegNo() +
                " -> Rs " + v3.calculateFee(130));

        System.out.println("\nElectric Vehicle Details:");

        System.out.println("Registration: " + v3.getRegNo());

        v3.chargingStatus();

        System.out.println("Charging Fee: Rs " +
                v3.chargingFee(10));

        System.out.println("Standard Charging Rate: Rs " +
                Chargeable.standardChargingRate() + " per kWh");
    }
}