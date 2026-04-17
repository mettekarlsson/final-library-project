package author;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AuthorController {
        Scanner scanner = new Scanner(System.in);

    public void adminAuthorMenu() {
        boolean active = true;
        while (active) {
            System.out.println("---- Author Menu ----");
            System.out.println("1. Edit author");
            System.out.println("2. Add author");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    editAuthor();
                    break;
                }
                case 2: {
                    //addAuthor();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    //case 1
    public void editAuthor() {
        ArrayList<Author> authors = new ArrayList<>();
        System.out.println("Enter the author ID:");
        int authorId = Integer.parseInt(scanner.nextLine());
        boolean active = true;

        while (active) {
            System.out.println("Choose what to update");
            System.out.println("1. First name");
            System.out.println("2. Last name");
            System.out.println("3. Nationality");
            System.out.println("4. Birth date");
            System.out.println("5. Biography");
            System.out.println("6. Website");
            System.out.println("0. Return");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
//                case 1: {
//                    System.out.println("Enter the new first name:");
//                    String firstName = scanner.nextLine();
//                    authorService.editAuthor(authorId, "first_name", firstName);
//                    break;
//                }
//                case 2: {
//                    System.out.println("Enter the new last name:");
//                    String lastName = scanner.nextLine();
//                    authorService.editAuthor(authorId, "last_name", lastName);
//                    break;
//                }
//                case 3: {
//                    System.out.println("Enter the new nationality:");
//                    String nationality = scanner.nextLine();
//                    authorService.editAuthor(authorId, "nationality", nationality);
//                    break;
//                }
//                case 4: {
//                    System.out.println("Enter the new birth date:");
//                    String birthDate = scanner.nextLine();
//                    authorService.editAuthor(authorId, "birth_date", birthDate);
//                    break;
//                }
//                case 5: {
//                    System.out.println("Enter the new biography:");
//                    String biography = scanner.nextLine();
//                    authorService.editAuthor(authorId, "biography", biography);
//                    break;
//                }
//                case 6: {
//                    System.out.println("Enter the new website:");
//                    String website = scanner.nextLine();
//                    authorService.editAuthor(authorId, "website", website);
//                    break;
//                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }
}
