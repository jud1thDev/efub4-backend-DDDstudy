package ewhabackendDDDstudy.member.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class MemberData {
    @Id
    @Column(name = "member_id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "blocked")
    private boolean blocked;

    protected MemberData() {
    }

    public MemberData(String id, String name, boolean blocked) {
        this.id = id;
        this.name = name;
        this.blocked = blocked;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBlocked() {
        return blocked;
    }
}
