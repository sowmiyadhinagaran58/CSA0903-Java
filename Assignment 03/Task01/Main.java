
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class ATM {
    private static final long BALANCE = 10_000;
    private FileInputStream cardReader;

    public void withdraw(String cardFile, Scanner input)
            throws InsufficientFundsException, IOException {

        try {
            cardReader = new FileInputStream(cardFile);

            int firstByte = cardReader.read();

            if (firstByte == -1) {
                throw new IOException("Card-reader file is empty");
            }

            System.out.print("Enter account number: ");
            long accountNumber = input.nextLong();

            System.out.print("Enter withdrawal amount: ");
            long amount = input.nextLong();

            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Withdrawal amount must be positive");
            }

            if (amount > BALANCE) {
                throw new InsufficientFundsException(
                    "Insufficient account balance");
            }

            System.out.println("Account number: " + accountNumber);
            System.out.println(
                "Withdrawal successful. Please collect your cash.");
            System.out.println(
                "Remaining balance: Rs. " + (BALANCE - amount));

        } finally {
            if (cardReader != null) {
                cardReader.close();
                System.out.println("Card-reader resource closed.");
            }

            System.out.println("ATM transaction process completed.");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("--uncaught")) {
            throw new IllegalStateException(
                "Card reader is in an invalid state");
        }

        String cardFile = args.length > 0
                ? args[0] : "card-reader.txt";

        Scanner input = new Scanner(System.in);
        ATM atm = new ATM();

        try {
            atm.withdraw(cardFile, input);

        } catch (FileNotFoundException e) {
            System.out.println(
                "ATM error: Card-reader file not found.");

        } catch (InputMismatchException |
                 IllegalStateException e) {
            System.out.println(
                "ATM error: Please enter numeric values only.");
            input.nextLine();

        } catch (IllegalArgumentException e) {
            System.out.println("ATM error: " + e.getMessage());

        } catch (InsufficientFundsException e) {
            System.out.println("ATM error: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("ATM file error: " + e.getMessage());
        }
    }
}
