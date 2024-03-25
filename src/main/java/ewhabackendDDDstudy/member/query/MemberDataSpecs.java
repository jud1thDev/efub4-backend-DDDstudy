package ewhabackendDDDstudy.member.query;

import ewhabackendDDDstudy.common.jpa.SpecBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class MemberDataSpecs {

    public static Specification<MemberData> nonBlocked() {
        return (root, query, cb) -> cb.equal(root.<Boolean>get("blocked"), false);
    }

    public static Specification<MemberData> nameLike(String keyword) {
        return (root, query, cb) -> cb.like(root.<String>get("name"), keyword + "%");
    }

    Specification<MemberData> spec = SpecBuilder.builder(MemberData.class)
            .ifTrue(searchRequest.isOnlyNotBlocked(),
                    () -> MemberDataSpecs.nonBlocked())
            .ifHasText(searchRequest.getName(),
                    name -> MemberDataSpecs.nameLike(searchRequest.getName()))
            .toSpec();
    List<MemberData> result = memberDataDao.findAll(spec, PageRequest.of(0, 5));
}
