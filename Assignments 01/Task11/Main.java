
class Vehicle {
    private final String registrationNumber;
    private final String ownerName;

    public Vehicle(String registrationNumber, String ownerName) {
        this.registrationNumber = registrationNumber;
        this.ownerName = ownerName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getRatePerHour() {
        return 0;
    }
}

class TwoWheeler extends Vehicle {
    public TwoWheeler(String reg, String owner) {
        super(reg, owner);
    }

    @Override
    public double getRatePerHour() {
        return 10;
    }
}

class Car extends Vehicle {
    public Car(String reg, String owner) {
        super(reg, owner);
    }

    @Override
    public double getRatePerHour() {
        return 30;
    }
}

class ElectricCar extends Car {
    public ElectricCar(String reg, String owner) {
        super(reg, owner);
    }
}

class VehicleFactory {
    public static Vehicle create(String type, String reg, String owner) {
        switch (type.toUpperCase()) {
            case "TWO_WHEELER":
                return new TwoWheeler(reg, owner);
            case "CAR":
                return new Car(reg, owner);
            case "ELECTRIC_CAR":
                return new ElectricCar(reg, owner);
            default:
                return null;
        }
    }
}

class ParkingTicket {
    private final Vehicle vehicle;
    private final int slotNumber;

    public ParkingTicket(Vehicle vehicle, int slotNumber) {
        this.vehicle = vehicle;
        this.slotNumber = slotNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public double calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Invalid parking duration");
        }

        if (minutes == 0) {
            return 0;
        }

        int hours = (minutes + 59) / 60;
        return hours * vehicle.getRatePerHour();
    }
}

class ParkingLot {
    private final ParkingTicket[] activeTickets;
    private final boolean[] occupiedSlots;
    private double totalCollection;

    public ParkingLot(int capacity) {
        activeTickets = new ParkingTicket[capacity];
        occupiedSlots = new boolean[capacity + 1];
        totalCollection = 0;
    }

    public boolean enter(String type, String reg, String owner,
                         int slotNumber) {

        Vehicle vehicle = VehicleFactory.create(type, reg, owner);

        if (vehicle == null) {
            return false;
        }

        if (slotNumber < 1 || slotNumber >= occupiedSlots.length) {
            return false;
        }

        if (occupiedSlots[slotNumber]) {
            return false;
        }

        if (findTicket(reg) != -1) {
            return false;
        }

        for (int i = 0; i < activeTickets.length; i++) {
            if (activeTickets[i] == null) {
                activeTickets[i] =
                    new ParkingTicket(vehicle, slotNumber);

                occupiedSlots[slotNumber] = true;
                return true;
            }
        }

        return false;
    }

    private int findTicket(String reg) {
        for (int i = 0; i < activeTickets.length; i++) {
            if (activeTickets[i] != null &&
                activeTickets[i].getVehicle()
                    .getRegistrationNumber().equalsIgnoreCase(reg)) {
                return i;
            }
        }

        return -1;
    }

    public double exit(String reg, int minutes) {
        if (minutes < 0) {
            return -1;
        }

        int index = findTicket(reg);

        if (index == -1) {
            return -1;
        }

        ParkingTicket ticket = activeTickets[index];
        double fee = ticket.calculateFee(minutes);

        occupiedSlots[ticket.getSlotNumber()] = false;
        activeTickets[index] = null;

        totalCollection += fee;
        return fee;
    }

    public double getTotalCollection() {
        return totalCollection;
    }
}

public class Main {

    static void test(String name, String expected, String actual) {
        String result = expected.equals(actual) ? "PASS" : "FAIL";

        System.out.printf("%-32s %-15s %-15s %s%n",
                name, expected, actual, result);
    }

    public static void main(String[] args) {

        System.out.println("CAMPUS SMART PARKING SYSTEM");
        System.out.println("TEST RESULTS");
        System.out.println();

        System.out.printf("%-32s %-15s %-15s %s%n",
                "Test Case", "Expected", "Actual", "Result");

        System.out.println(
                "---------------------------------------------------------------------");

        // Test 1: Normal operation and daily collection
        ParkingLot lot1 = new ParkingLot(2);

        lot1.enter("TWO_WHEELER", "TN01A1", "Arun", 1);
        lot1.enter("CAR", "TN02B2", "Priya", 2);

        double bikeFee = lot1.exit("TN01A1", 60);
        double carFee = lot1.exit("TN02B2", 120);

        test("Normal operation",
                "10 + 60 = 70",
                bikeFee + " + " + carFee + " = "
                        + lot1.getTotalCollection());

        // Test 2: Last free slot
        ParkingLot lot2 = new ParkingLot(2);

        lot2.enter("CAR", "TN03C3", "Kumar", 1);
        boolean lastSlot = lot2.enter(
                "TWO_WHEELER", "TN04D4", "Ravi", 2);

        test("Last free slot",
                "true", String.valueOf(lastSlot));

        // Test 3: Zero-minute parking
        ParkingLot lot3 = new ParkingLot(2);

        lot3.enter("TWO_WHEELER", "TN05E5", "Anu", 1);
        double zeroFee = lot3.exit("TN05E5", 0);

        test("Zero-minute parking",
                "0.0", String.valueOf(zeroFee));

        // Test 4: Exact fee-slab boundary
        ParkingLot lot4 = new ParkingLot(2);

        lot4.enter("CAR", "TN06F6", "John", 1);
        double boundaryFee = lot4.exit("TN06F6", 60);

        test("Exactly 60 minutes",
                "30.0", String.valueOf(boundaryFee));

        // Test 5: Unknown vehicle type
        ParkingLot lot5 = new ParkingLot(2);

        boolean unknown = lot5.enter(
                "BUS", "TN07G7", "Sam", 1);

        test("Unknown vehicle type",
                "false", String.valueOf(unknown));

        // Test 6: Duplicate registration number
        ParkingLot lot6 = new ParkingLot(2);

        lot6.enter("CAR", "TN08H8", "Meena", 1);

        boolean duplicate = lot6.enter(
                "TWO_WHEELER", "TN08H8", "Ravi", 2);

        test("Duplicate registration",
                "false", String.valueOf(duplicate));

        // Test 7: Invalid slot number
        ParkingLot lot7 = new ParkingLot(2);

        boolean invalidSlot = lot7.enter(
                "CAR", "TN09J9", "Kiran", 0);

        test("Invalid slot number",
                "false", String.valueOf(invalidSlot));

        System.out.println();
        System.out.println("DAILY COLLECTION CALCULATION");
        System.out.println("TwoWheeler: 60 minutes = Rs. 10");
        System.out.println("Car: 120 minutes = Rs. 60");
        System.out.println("-----------------------------");
        System.out.println("Total collection = Rs. "
                + lot1.getTotalCollection());

        System.out.println();
        System.out.println("All tests completed.");
    }
}
