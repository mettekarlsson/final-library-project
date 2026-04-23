package author;
import base.BaseController;
import exceptions.LibraryException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorController extends BaseController {
        AuthorService authorService = new AuthorService();

    public void adminAuthorMenu() {
        boolean active = true;
        while (active) {
            System.out.println("---- Author Menu ----");
            System.out.println("1. Edit author");
            System.out.println("2. Add author");
            System.out.println("0. Return");

            int choice = readInt();

            try {
                switch (choice) {
                    case 1: {
                        editAuthor();
                        break;
                    }
                    case 2: {
                        addAuthor();
                        break;
                    }
                    case 0: {
                        active = false;
                        break;
                    }
                }
            } catch (LibraryException e) {
                handleException(e);
            }
        }
    }

    //case 1
    public void editAuthor() {
        List<AuthorInfoDTO> authors = new ArrayList<>(authorService.getAllAuthors());
        for(AuthorInfoDTO a : authors) {
            System.out.println(a);
        }
        System.out.println("Enter the author ID:");
        int authorId = readInt();

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

            int choice = readInt();

            switch (choice) {
                case 1: {
                    System.out.println("Enter the new first name:");
                    String firstName = scanner.nextLine();
                    while (firstName.isBlank()) {
                        System.out.println("First name cannot be empty. Try again.");
                        firstName = scanner.nextLine();
                    }
                    String result = authorService.editAuthor(authorId, "first_name", firstName);
                    System.out.println(result);
                    break;
                }
                case 2: {
                    System.out.println("Enter the new last name:");
                    String lastName = scanner.nextLine();
                    while (lastName.isBlank()) {
                        System.out.println("Last name cannot be empty. Try again.");
                        lastName = scanner.nextLine();
                    }
                    String result = authorService.editAuthor(authorId, "last_name", lastName);
                    System.out.println(result);
                    break;
                }
                case 3: {
                    System.out.println("Enter the new nationality:");
                    String nationality = scanner.nextLine();
                    String result = authorService.editAuthor(authorId, "nationality", nationality);
                    System.out.println(result);
                    break;
                }
                case 4: {
                    System.out.println("Enter the new birth date:");
                    String birthDate = scanner.nextLine();
                    String result = authorService.editAuthor(authorId, "birth_date", birthDate);
                    System.out.println(result);
                    break;
                }
                case 5: {
                    System.out.println("Enter the new biography:");
                    String biography = scanner.nextLine();
                    String result = authorService.editAuthorDesc(authorId, "biography", biography);
                    System.out.println(result);
                    break;
                }
                case 6: {
                    System.out.println("Enter the new website:");
                    String website = scanner.nextLine();
                    String result = authorService.editAuthorDesc(authorId, "website", website);
                    System.out.println(result);
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    public void addAuthor() {
        System.out.println("Enter their first name:");
        String firstName = scanner.nextLine();
        while (firstName.isBlank()) {
            System.out.println("First name cannot be empty. Try again.");
            firstName = scanner.nextLine();
        }
        System.out.println("Enter their last name:");
        String lastName = scanner.nextLine();
        while (lastName.isBlank()) {
            System.out.println("Last name cannot be empty. Try again.");
            lastName = scanner.nextLine();
        }
        System.out.println("Enter their nationality:");
        String nationality = scanner.nextLine();
        System.out.println("Enter their birth date:");
        //kan krasha om användaren skriver fel format. kan använda en try/catch här
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter their biography:");
        String biography = scanner.nextLine();
        System.out.println("Enter their website:");
        String website = scanner.nextLine();

        NewAuthorDTO newAuthorDTO = new NewAuthorDTO(firstName, lastName, nationality, birthDate, biography, website);
            String result = authorService.addAuthor(newAuthorDTO);
            System.out.println(result);
    }
}
