package member;

import exceptions.MemberNotFoundException;
import exceptions.OperationFailedException;
import exceptions.ValidationException;

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

    //case 2 och 3
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

    //admin case 2
    public String addNewMember(NewMemberDTO member) {
        if (!member.getMembershipType().equals("standard") && !member.getMembershipType().equals("premium")) {
            throw new ValidationException("Membership type must be 'standard' or 'premium'.");
        }
        if (!member.getStatus().equals("active") && !member.getStatus().equals("expired") && !member.getStatus().equals("suspended")) {
            throw new ValidationException("Status must be 'active', 'expired' or 'suspended'.");
        }
        return memberRepository.addNewMember(member);
    }

    //admin case 3
    public String suspendMember(int memberId) {
        Member member = memberRepository.getMemberById(memberId);
        if (member == null) {
            throw new MemberNotFoundException(memberId);
        }
        if (member.getStatus().equals("suspended")) {
            throw new ValidationException("Member # " + memberId + " is already suspended.");
        }
        String result = memberRepository.suspendMember(memberId);
        if (result == null) {
            throw new MemberNotFoundException(memberId);
        }
        return result;
    }

    //admin case 4
    public String removeMember(int memberId) {
        String result = memberRepository.removeMember(memberId);
        if (result == null) {
            throw new MemberNotFoundException(memberId);
        }
        return result;
    }
}