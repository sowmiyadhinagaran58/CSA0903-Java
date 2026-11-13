import java.util.Objects;

// Abstract Vehicle class
abstract class Vehicle {

    private final String regNo;
    private String ownerName;

    Vehicle(String regNo, String ownerName) {
        setOwnerName(ownerName);

        if (regNo == null || regNo.isBlank()) {
            throw new IllegalArgumentException(
                "Invalid registration number");
        }

        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException(
                "Owner name cannot be empty");
        }

        this.ownerName = ownerName;
    }

    protected abstract double ratePerHour();

    public double calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                "Duration cannot be negative");
        }

        int hours = (minutes + 59) / 60;
        return hours * ratePerHour();
    }
}

// TwoWheeler class
class TwoWheeler extends Vehicle {

    TwoWheeler(String regNo, String ownerName) {
        super(regNo, ownerName);
    }

    @Override
    protected double ratePerHour() {
        return 10;
    }
}

// Car class
class Car extends Vehicle {

    Car(String regNo, String ownerName) {
        super(regNo, ownerName);
    }

    @Override
    protected double ratePerHour() {
        return 30;
    }
}

// Interface
interface Chargeable {
    double chargingFee(int kWh);
}

// ElectricCar class
class ElectricCar extends Car implements Chargeable {

    ElectricCar(String regNo, String ownerName) {
        super(regNo, ownerName);
    }

    @Override
    public double chargingFee(int kWh) {
        if (kWh < 0) {
            throw new IllegalArgumentException(
                "Invalid charging units");
        }

        return kWh * 12;
    }
}

// ParkingSlot class
class ParkingSlot {

    private Integer slotNumber;
    private boolean occupied;

    ParkingSlot(Integer slotNumber) {
        setSlotNumber(slotNumber);
        this.occupied = false;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(Integer slotNumber) {
        if (slotNumber == null || slotNumber <= 0) {
            throw new IllegalArgumentException(
                "Invalid parking slot number");
        }

        this.slotNumber = slotNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}

// ParkingService class
class ParkingService {

    public double calculateParkingFee(
            Vehicle vehicle, Integer minutes) {

        if (vehicle == null) {
            throw new IllegalArgumentException(
                "Vehicle cannot be null");
        }

        if (minutes == null || minutes < 0) {
            throw new IllegalArgumentException(
                "Invalid parking duration");
        }

        return vehicle.calculateFee(minutes);
    }
}

// Main class
public class Main {

    public static void main(String[] args) {

        ParkingService service = new ParkingService();

        Vehicle v1 = new TwoWheeler(
            "TN01A1", "Arun");

        Vehicle v2 = new Car(
            "TN02B2", "Priya");

        ElectricCar v3 = new ElectricCar(
            "TN03C3", "Kumar");

        ParkingSlot slot = new ParkingSlot(101);

        Integer duration = 130;

        System.out.println("Campus Smart Parking System");

        System.out.println("\nVehicle Details:");

        System.out.println("Registration: " + v1.getRegNo());
        System.out.println("Owner: " + v1.getOwnerName());

        System.out.println("Registration: " + v2.getRegNo());
        System.out.println("Owner: " + v2.getOwnerName());

        System.out.println("Registration: " + v3.getRegNo());
        System.out.println("Owner: " + v3.getOwnerName());

        System.out.println("\nParking Fees:");

        System.out.println("TwoWheeler: Rs " +
            service.calculateParkingFee(v1, duration));

        System.out.println("Car: Rs " +
            service.calculateParkingFee(v2, duration));

        System.out.println("ElectricCar: Rs " +
            service.calculateParkingFee(v3, duration));

        System.out.println("\nParking Slot:");

        System.out.println("Slot Number: " +
            slot.getSlotNumber());

        slot.setOccupied(true);

        System.out.println("Occupied: " +
            slot.isOccupied());

        System.out.println("\nCharging Fee:");

        System.out.println("ElectricCar: Rs " +
            v3.chargingFee(10));

        System.out.println("\nValidation:");

        System.out.println("Duration: " + duration + " minutes");

        System.out.println("Slot Number: " +
            slot.getSlotNumber());

        System.out.println("Integer comparison: " +
            Integer.valueOf(1000).equals(
                Integer.valueOf(1000)));
    }
}
