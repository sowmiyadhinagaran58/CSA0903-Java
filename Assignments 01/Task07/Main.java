
import java.util.Objects;

abstract class Vehicle {
    private final String registrationNumber;
    private String ownerName;

    Vehicle(String registrationNumber, String ownerName) {
        this.registrationNumber = registrationNumber;
        this.ownerName = ownerName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public abstract double calculateFee(int hours);

    @Override
    public String toString() {
        return "Registration Number: " + registrationNumber
                + ", Owner: " + ownerName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Vehicle)) {
            return false;
        }

        Vehicle other = (Vehicle) obj;

        return registrationNumber.equalsIgnoreCase(
                other.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                registrationNumber.toUpperCase());
    }
}

class TwoWheeler extends Vehicle {
    private static final double RATE = 10.0;

    TwoWheeler(String number, String owner) {
        super(number, owner);
    }

    @Override
    public double calculateFee(int hours) {
        return hours * RATE;
    }
}

class Car extends Vehicle {
    private static final double RATE = 20.0;

    Car(String number, String owner) {
        super(number, owner);
    }

    @Override
    public double calculateFee(int hours) {
        return hours * RATE;
    }
}

class ElectricCar extends Car {
    private static final double RATE = 15.0;

    ElectricCar(String number, String owner) {
        super(number, owner);
    }

    @Override
    public double calculateFee(int hours) {
        return hours * RATE;
    }
}

public class Main {
    public static void main(String[] args) {

        Vehicle v1 = new TwoWheeler("TN01AB1234", "Arun");
        Vehicle v2 = new Car("TN02CD5678", "Priya");
        Vehicle v3 = new ElectricCar("TN03EF9012", "Kumar");

        System.out.println("Vehicle Details:");
        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v3);

        System.out.println("\nParking Fees for 3 Hours:");
        System.out.println("Two Wheeler: Rs." +
                v1.calculateFee(3));
        System.out.println("Car: Rs." +
                v2.calculateFee(3));
        System.out.println("Electric Car: Rs." +
                v3.calculateFee(3));

        Vehicle v4 = new Car("TN02CD5678", "Another Owner");

        System.out.println("\nEquality Checking:");
        System.out.println("Are v2 and v4 equal? " +
                v2.equals(v4));

        System.out.println("Same hash code? " +
                (v2.hashCode() == v4.hashCode()));
    }
}
