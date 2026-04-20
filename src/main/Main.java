package main;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        MainController mainController = new MainController();

        boolean active = true;

        while (active) {
            System.out.println("---- Log in ----");
            System.out.println("1. Member");
            System.out.println("2. Admin");
            System.out.println("0. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    mainController.memberLogIn();
                    break;
                }
                case 2: {
                    mainController.adminMainMenu();
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
