package member;

import exceptions.MemberNotFoundException;
import exceptions.OperationFailedException;

import java.util.ArrayList;
import java.util.List;

public class MemberService {
    MemberRepository memberRepository = new MemberRepository();
    //for login(?)
    public Member getMemberByEmail(String email) {
        return memberRepository.getMemberByEmail(email);
    }

    //case 1
    public MemberInfoDTO getMemberById(int memberId) {
        Member member = memberRepository.getMemberById(memberId);
        if (member == null) {
            throw new MemberNotFoundException(memberId);
        }
        return MemberMapper.mapToDTO(member);
    }

    //case (2 och) 3
    public String updateMemberInfo(String column, String newValue, int memberId) {
        String result = memberRepository.updateMemberInfo(column, newValue, memberId);
        if (result == null) {
            throw new OperationFailedException("Failed to update member-info.");
        }
        return result;
    }

    //admin case 1
    public List<MemberInfoDTO> getAllMembers() {
        List<Member> members = memberRepository.getAllMembers();
        List<MemberInfoDTO> dtos = new ArrayList<>();
        for (Member m : members) {
            dtos.add(MemberMapper.mapToDTO(m));
        }
        return dtos;
    }

    public String addNewMember(NewMemberDTO member) {
        return memberRepository.addNewMember(member);
    }

    //admin case 3 - throwas rätt exception här?
    public String suspendMember(int memberId) {
        String result = memberRepository.suspendMember(memberId);
        if (result == null) {
            throw new MemberNotFoundException(memberId);
        }
        return result;
    }

    //admin case 4 - throwas rätt exception här?
    public String removeMember(int memberId) {
        String result = memberRepository.removeMember(memberId);
        if (result == null) {
            throw new MemberNotFoundException(memberId);
        }
        return result;
    }
}
