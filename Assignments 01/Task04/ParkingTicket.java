
import java.time.LocalDateTime;

public class ParkingTicket {

    private String ticketId;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    // Default constructor
    public ParkingTicket() {
        this("T000", "NOT ASSIGNED", LocalDateTime.now(), 0.0);
    }

    // Parameterized constructor
    public ParkingTicket(String ticketId, String vehicleNumber) {
        this(ticketId, vehicleNumber, LocalDateTime.now(), 0.0);
    }

    // Overloaded constructor
    public ParkingTicket(String ticketId, String vehicleNumber,
                         LocalDateTime entryTime, double fee) {
        this.ticketId = ticketId;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.fee = fee;
        this.exitTime = null;
    }

    // Copy constructor
    public ParkingTicket(ParkingTicket other) {
        this(other.ticketId, other.vehicleNumber,
             other.entryTime, other.fee);
        this.exitTime = other.exitTime;
    }

    // Calculate parking fee
    public double calculateFee(double hours) {
        fee = hours * 20;
        return fee;
    }

    // Close ticket
    public void closeTicket() {
        exitTime = LocalDateTime.now();
    }

    // Display ticket details
    public void displayTicket() {
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Entry Time: " + entryTime);
        System.out.println("Exit Time: " + exitTime);
        System.out.println("Fee: Rs. " + fee);
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {

        // Default constructor
        ParkingTicket t1 = new ParkingTicket();

        // Parameterized constructor
        ParkingTicket t2 =
            new ParkingTicket("T101", "TN01AB1234");

        // Overloaded constructor
        ParkingTicket t3 =
            new ParkingTicket("T102", "TN02CD5678",
                              LocalDateTime.now(), 100.0);

        // Calculate fee
        t2.calculateFee(3);

        // Close ticket
        t2.closeTicket();

        // Copy constructor
        ParkingTicket t4 = new ParkingTicket(t2);

        System.out.println("DEFAULT CONSTRUCTOR");
        t1.displayTicket();

        System.out.println("PARAMETERIZED CONSTRUCTOR");
        t2.displayTicket();

        System.out.println("OVERLOADED CONSTRUCTOR");
        t3.displayTicket();

        System.out.println("COPY CONSTRUCTOR");
        t4.displayTicket();
    }
}
