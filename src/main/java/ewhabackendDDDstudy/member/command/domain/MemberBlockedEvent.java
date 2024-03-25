package ewhabackendDDDstudy.member.command.domain;

public class MemberBlockedEvent extends Event {
    private String memberId;

    public MemberBlockedEvent(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}