package member;

import java.util.ArrayList;

public class MemberService {
    MemberRepository memberRepository = new MemberRepository();

    public MemberInfoDTO mapToMemberDTO(Member member) {
            MemberInfoDTO memberInfoDTO = new MemberInfoDTO(member.getId(), member.getFirstName(),
                    member.getLastName(), member.getEmail(), member.getMembershipType(), member.getStatus());

        return memberInfoDTO;
    }

    public ArrayList<MemberInfoDTO> mapToMemberDTO(ArrayList<Member> members) {
        ArrayList<MemberInfoDTO> dtos = new ArrayList<>();
        for (Member m : members) {
            MemberInfoDTO memberInfoDTO = new MemberInfoDTO(m.getId(), m.getFirstName(), m.getLastName(),
                    m.getEmail(), m.getMembershipType(), m.getStatus());
            dtos.add(memberInfoDTO);
        }
        return dtos;
    }

    public MemberInfoDTO getMemberById(int id) {
        return mapToMemberDTO(memberRepository.getMemberById(id)) ;
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.getMemberByEmail(email);
    }

    public void updateMemberInfo(String column, String newValue) {
        memberRepository.updateMemberInfo(column, newValue);
    }

    public ArrayList<MemberInfoDTO> getAllMembers() {
        return mapToMemberDTO(memberRepository.getAllMembers());
    }

    public String addNewMember(NewMemberDTO member) {
        return memberRepository.addNewMember(member);
    }
}
