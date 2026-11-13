
class ParkingTicket {

    static int ticketCounter = 1000;
    static int totalTicketsIssued = 0;

    int ticketId;
    String vehicleNumber;

    ParkingTicket(String vehicleNumber) {
        ticketId = ++ticketCounter;
        this.vehicleNumber = vehicleNumber;
        totalTicketsIssued++;
    }

    void displayTicket() {
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Vehicle Number: " + vehicleNumber);
    }

    static void displayTotalTickets() {
        System.out.println("Total Tickets Issued: "
                + totalTicketsIssued);
    }
}

public class Main {
    public static void main(String[] args) {

        ParkingTicket t1 =
                new ParkingTicket("TN01AB1234");

        ParkingTicket t2 =
                new ParkingTicket("TN02CD5678");

        t1.displayTicket();
        t2.displayTicket();

        ParkingTicket.displayTotalTickets();
    }
}
