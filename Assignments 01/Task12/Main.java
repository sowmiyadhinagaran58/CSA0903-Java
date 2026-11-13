
import java.time.LocalTime;

// Abstract superclass
abstract class Vehicle {
    private final String regNo;

    public Vehicle(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid registration");
        }
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public abstract double calculateFee(int minutes, LocalTime entryTime);
}

// Existing vehicle categories
class TwoWheeler extends Vehicle {
    public TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(int minutes, LocalTime time) {
        return minutes == 0 ? 0 : ((minutes + 59) / 60) * 10;
    }
}

class Car extends Vehicle {
    public Car(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(int minutes, LocalTime time) {
        return minutes == 0 ? 0 : ((minutes + 59) / 60) * 30;
    }
}

class ElectricCar extends Car {
    public ElectricCar(String regNo) {
        super(regNo);
    }
}

// New e-bike category
class EBike extends Vehicle {
    public EBike(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(int minutes, LocalTime time) {
        return 20; // Flat fee per visit
    }
}

// New delivery van category
class DeliveryVan extends Vehicle {
    public DeliveryVan(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(int minutes, LocalTime entryTime) {
        if (minutes == 0) return 0;

        int hours = (minutes + 59) / 60;

        boolean peakTime =
            !entryTime.isBefore(LocalTime.of(9, 0)) &&
            entryTime.isBefore(LocalTime.of(17, 0));

        double rate = peakTime ? 60 : 30;

        return hours * rate;
    }
}

// One-day visitor pass
class VisitorPass {
    private final String registration;
    private final double fee = 100;

    public VisitorPass(String registration) {
        this.registration = registration;
    }

    public String getRegistration() {
        return registration;
    }

    public double getFee() {
        return fee;
    }
}

// Parking ticket
class ParkingTicket {
    private final Vehicle vehicle;
    private final VisitorPass pass;
    private final int floor;
    private final int slot;

    public ParkingTicket(Vehicle vehicle, int floor, int slot) {
        this(vehicle, null, floor, slot);
    }

    public ParkingTicket(Vehicle vehicle, VisitorPass pass,
                         int floor, int slot) {
        this.vehicle = vehicle;
        this.pass = pass;
        this.floor = floor;
        this.slot = slot;
    }

    public String getRegistration() {
        return vehicle.getRegNo();
    }

    public int getFloor() {
        return floor;
    }

    public int getSlot() {
        return slot;
    }

    public double calculateFee(int minutes, LocalTime time) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Invalid duration");
        }

        if (pass != null) {
            return pass.getFee();
        }

        return vehicle.calculateFee(minutes, time);
    }
}

// Parking lot with different capacities on each floor
class ParkingLot {
    private final ParkingTicket[][] floors;

    public ParkingLot(int[] capacities) {
        if (capacities == null || capacities.length != 5) {
            throw new IllegalArgumentException(
                "Parking lot must have five floors");
        }

        floors = new ParkingTicket[5][];

        for (int i = 0; i < 5; i++) {
            if (capacities[i] <= 0) {
                throw new IllegalArgumentException(
                    "Invalid floor capacity");
            }

            floors[i] = new ParkingTicket[capacities[i]];
        }
    }

    private ParkingTicket find(String regNo) {
        for (ParkingTicket[] floor : floors) {
            for (ParkingTicket ticket : floor) {
                if (ticket != null &&
                    ticket.getRegistration().equalsIgnoreCase(regNo)) {
                    return ticket;
                }
            }
        }
        return null;
    }

    public boolean enter(Vehicle vehicle, int floor, int slot) {
        if (vehicle == null ||
            floor < 1 || floor > floors.length ||
            slot < 1 || slot > floors[floor - 1].length) {
            return false;
        }

        if (find(vehicle.getRegNo()) != null ||
            floors[floor - 1][slot - 1] != null) {
            return false;
        }

        floors[floor - 1][slot - 1] =
            new ParkingTicket(vehicle, floor, slot);

        return true;
    }

    public boolean enterVisitor(String regNo, int floor, int slot) {
        if (regNo == null || regNo.trim().isEmpty() ||
            floor < 1 || floor > floors.length ||
            slot < 1 || slot > floors[floor - 1].length ||
            find(regNo) != null ||
            floors[floor - 1][slot - 1] != null) {
            return false;
        }

        Vehicle visitorVehicle = new Car(regNo);
        VisitorPass pass = new VisitorPass(regNo);

        floors[floor - 1][slot - 1] =
            new ParkingTicket(visitorVehicle, pass, floor, slot);

        return true;
    }

    public double exit(String regNo, int minutes, LocalTime time) {
        if (minutes < 0 || time == null) {
            return -1;
        }

        ParkingTicket ticket = find(regNo);

        if (ticket == null) {
            return -1;
        }

        double fee = ticket.calculateFee(minutes, time);

        floors[ticket.getFloor() - 1]
              [ticket.getSlot() - 1] = null;

        return fee;
    }
}

// Main class
public class Main {
    public static void main(String[] args) {

        // Five floors with different slot capacities
        ParkingLot lot = new ParkingLot(
            new int[]{2, 3, 1, 2, 4}
        );

        System.out.println("CAMPUS SMART PARKING SYSTEM");
        System.out.println();

        // E-bike test
        System.out.println("E-Bike Entry: " +
            lot.enter(new EBike("EB101"), 1, 1));

        System.out.println("E-Bike Fee: Rs. " +
            lot.exit("EB101", 90, LocalTime.of(10, 0)));

        // Delivery van test during peak time
        System.out.println("Delivery Van Entry: " +
            lot.enter(new DeliveryVan("DV201"), 2, 1));

        System.out.println("Delivery Van Fee: Rs. " +
            lot.exit("DV201", 60, LocalTime.of(10, 0)));

        // Delivery van test outside peak time
        lot.enter(new DeliveryVan("DV202"), 2, 2);

        System.out.println("Van Outside Peak Fee: Rs. " +
            lot.exit("DV202", 60, LocalTime.of(18, 0)));

        // Visitor pass test
        System.out.println("Visitor Entry: " +
            lot.enterVisitor("VIS301", 5, 1));

        System.out.println("Visitor Pass Fee: Rs. " +
            lot.exit("VIS301", 120, LocalTime.of(11, 0)));

        // Invalid floor test
        System.out.println("Invalid Floor Entry: " +
            lot.enter(new Car("CAR401"), 6, 1));

        // Duplicate registration test
        lot.enter(new Car("CAR501"), 3, 1);

        System.out.println("Duplicate Registration: " +
            lot.enter(new Car("CAR501"), 4, 1));

        System.out.println();
        System.out.println("All tests completed.");
    }
}
