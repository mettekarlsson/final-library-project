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
            System.out.println("3. Change my membership-type");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    showProfileInfo();
                    break;
                }
                case 2: {
                    updateProfileInfo();
                    break;
                }
                case 3: {
                    updateMembershipType();
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
    public void showProfileInfo() {
       MemberInfoDTO memberinfoDTO = memberService.getMemberById(loggedInUser.getId());
       System.out.println(memberinfoDTO);
    }

    //case 2
    public void updateProfileInfo() {
        boolean active = true;
            while (active) {
                System.out.println("---- Choose what to update ----");
                System.out.println("1. Update first name");
                System.out.println("2. Update last name");
                System.out.println("3. Update email");
                System.out.println("4. Update password");
                System.out.println("0. Return");
                int option = Integer.parseInt(scanner.nextLine().trim());

                    switch (option) {
                        case 1: {
                            System.out.println("New first name:");
                            String newValue = scanner.nextLine();
                            memberService.updateMemberInfo("first_name", newValue);
                            break;
                        }
                        case 2: {
                            System.out.println("New last name:");
                            String newValue = scanner.nextLine();
                            memberService.updateMemberInfo("last_name", newValue);
                            break;
                        }
                        case 3: {
                            System.out.println("New email:");
                            String newValue = scanner.nextLine();
                            memberService.updateMemberInfo("email", newValue);
                            break;
                        }
                        case 4: {
                            System.out.println("New password:");
                            String newValue = scanner.nextLine();
                            memberService.updateMemberInfo("password", newValue);
                            break;
                        }
                        case 0: {
                            active = false;
                            break;
                        }
                    }
            }
    }


    public void updateMembershipType() {
        String result = null;
        System.out.println("Current status:");
        MemberInfoDTO member = memberService.getMemberById(loggedInUser.getId());
        System.out.println(member.getMembershipType());
        if (member.getMembershipType().equals("standard")) {
            System.out.println("1. Change to premium");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                result = memberService.updateMemberInfo("membership_type", "premium");
            }
        } else {
            System.out.println("1. Change to standard");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                result = memberService.updateMemberInfo("membership_type", "standard");
            }
        }
        System.out.println(result);
    }

    public void adminMemberMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Member Menu ----");
            System.out.println("1. See all members");
            System.out.println("2. Add new member");
            System.out.println("3. Suspend member");
            System.out.println("4. Remove member");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    showAllMembers();
                    break;
                }
                case 2: {
                    addNewMember();
                    break;
                }
                case 3: {
                    suspendMember();
                    break;
                }
                case 4: {
                    removeMember();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    //case 1 admin
    public void showAllMembers() {
        ArrayList<MemberInfoDTO> members = memberService.getAllMembers();
        for (MemberInfoDTO member : members) {
            System.out.println(member.toString());
        }
    }

    //case 2 admin
    public void addNewMember() {
        System.out.println("The new member's first name:");
        String firstName = scanner.nextLine();
        System.out.println("The new member's last name:");
        String lastName = scanner.nextLine();
        System.out.println("The new member's email:");
        String email = scanner.nextLine();
        System.out.println("The new member's joining date:");
        LocalDate membershipDate = LocalDate.parse(scanner.nextLine());
        System.out.println("The new member's membership-type:");
        String membershipType = scanner.nextLine();
        System.out.println("The new member's status:");
        String status = scanner.nextLine();
        System.out.println("The new member's password:");
        String password = scanner.nextLine();
        NewMemberDTO member = new NewMemberDTO(firstName, lastName, email, membershipDate, membershipType, status, password);
        String result = memberService.addNewMember(member);
        System.out.println(result);
    }

    //case 3 admin
    public void suspendMember() {
        System.out.println("Enter the ID of the member you want to suspend:");
        int memberId = Integer.parseInt(scanner.nextLine());
        String result = memberService.suspendMember(memberId);
        System.out.println(result);
    }

    //case 4 admin
    public void removeMember() {
        System.out.println("Enter the ID of the member you want to remove:");
        int memberId = Integer.parseInt(scanner.nextLine());
        String result = memberService.removeMember(memberId);
        System.out.println(result);
    }
}
