
import book.BookController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //MainController mainController = new MainController();
        BookController bookController = new BookController();
        boolean active = true;

        while (active) {
            System.out.println("---- Log in ----");
            System.out.println("1. Member");
            System.out.println("2. Admin");
            System.out.println("0. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    bookController.memberBookMenu();

                   // mainController.memberLogIn();
                    break;
                }
                case 2: {
                    //mainController.adminMainMenu();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }

            }
        }
    }
}
