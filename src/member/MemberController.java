package member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import static Main.MainController.loggedInUser;


public class MemberController {

    Scanner scanner = new Scanner(System.in);
    MemberService memberService = new MemberService();

    public void memberProfileMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Your profile ----");
            System.out.println("1. Show my profile info");
            System.out.println("2. Update my profile info");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    showProfileInfo();
                    break;
                }
//                case 2: {
//                    boolean current = true;
//                    while (current) {
//                        System.out.println("---- Update member info ----");
//                        System.out.println("1. Update first name");
//                        System.out.println("2. Update last name");
//                        System.out.println("3. Update email");
//                        System.out.println("4. Update password");
//                        System.out.println("0. Return");
//                        int option = Integer.parseInt(scanner.nextLine().trim());
//
//                        switch (option) {
//                            case 1: {
//                                System.out.println("New first name:");
//                                String newValue = scanner.nextLine();
//                                memberService.updateMemberInfo("first_name", newValue);
//                                break;
//                            }
//                            case 2: {
//                                System.out.println("New last name:");
//                                String newValue = scanner.nextLine();
//                                memberService.updateMemberInfo("last_name", newValue);
//                                break;
//                            }
//                            case 3: {
//                                System.out.println("New email:");
//                                String newValue = scanner.nextLine();
//                                memberService.updateMemberInfo("email", newValue);
//                                break;
//                            }
//                            case 4: {
//                                System.out.println("New password:");
//                                String newValue = scanner.nextLine();
//                                memberService.updateMemberInfo("password", newValue);
//                                break;
//                            }
//                            case 0: {
//                                current = false;
//                                break;
//                            }
//                        }
//                    }
//                    break;
//                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    //case 1
    public void showProfileInfo() {
       MemberInfoDTO memberinfoDTO = memberService.getMemberById(loggedInUser.getId());
       System.out.println(memberinfoDTO);
    }

    public void adminMemberMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Member Menu ----");
            System.out.println("1. See all members");
            System.out.println("2. Add new member");
            System.out.println("3. Suspend member");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
//                case 1: {
//                    ArrayList<Member> members = memberService.getAllMembers();
//                    for (Member member : members) {
//                        System.out.println(member.toString());
//                    }
//                    break;
//                }
//                case 2: {
//                    System.out.println("The new member's first name:");
//                    String firstName = scanner.nextLine();
//                    System.out.println("The new member's last name:");
//                    String lastName = scanner.nextLine();
//                    System.out.println("The new member's email:");
//                    String email = scanner.nextLine();
//                    System.out.println("The new member's joining date:");
//                    LocalDate membershipDate = LocalDate.parse(scanner.nextLine());
//                    System.out.println("The new member's membership-type:");
//                    String membershipType = scanner.nextLine();
//                    System.out.println("The new member's status:");
//                    String status = scanner.nextLine();
//                    System.out.println("The new member's password:");
//                    String password = scanner.nextLine();
//                    Member member = new Member(firstName, lastName, email, membershipDate, membershipType, status, password);
//                    memberService.addNewMember(member);
//                    break;
//                }
//                case 3: {
//                    System.out.println("What member-id do you want to suspend?");
//                    int memberId = Integer.parseInt(scanner.nextLine());
//                    memberService.suspendMember(memberId);
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
