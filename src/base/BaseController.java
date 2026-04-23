package base;

import java.util.Scanner;

public abstract class BaseController {

    protected Scanner scanner = new Scanner(System.in);

    protected void handleException(Exception e) {
        System.out.println(e.getMessage());
    }

    protected int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

}
