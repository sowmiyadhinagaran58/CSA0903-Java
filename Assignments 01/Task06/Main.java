
public class Main {
    public static void main(String[] args) {

        ElectricCar e1 = new ElectricCar(
            "TN01EV1234", "Arun", 20.0, 5, 75.0
        );

        System.out.println("\nElectric Car Details:");
        e1.displayInfo();
    }
}