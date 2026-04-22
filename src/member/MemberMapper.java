package member;

public class MemberMapper {

    public static MemberInfoDTO mapToDTO(Member member) {
            return new MemberInfoDTO(
                    member.getId(),
                    member.getFirstName(),
                    member.getLastName(),
                    member.getEmail(),
                    member.getMembershipType(),
                    member.getStatus());
        }
}
