package member;

import java.util.ArrayList;

public class MemberService {
    MemberRepository memberRepository = new MemberRepository();

    public MemberInfoDTO mapToMemberDTO(Member member) {
            MemberInfoDTO memberInfoDTO = new MemberInfoDTO(member.getId(), member.getFirstName(),
                    member.getLastName(), member.getEmail(), member.getMembershipType(), member.getStatus());

        return memberInfoDTO;
    }

    public MemberInfoDTO getMemberById(int id) {
        return mapToMemberDTO(memberRepository.getMemberById(id)) ;
    }

    public MemberInfoDTO getMemberByEmail(String email) {
        return mapToMemberDTO(memberRepository.getMemberByEmail(email));
    }
}
