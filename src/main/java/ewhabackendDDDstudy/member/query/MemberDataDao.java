package ewhabackendDDDstudy.member.query;

import ewhabackendDDDstudy.common.jpa.Rangeable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberDataDao extends Repository<MemberData, String> {

    MemberData findById(String memberId);

//    페이징
    Page<MemberData> findByBlocked(boolean blocked, Pageable pageable);
    List<MemberData> findByNameLike(String name, Pageable pageable);

    List<MemberData> findAll(Specification<MemberData> spec, Pageable pageable);

    List<MemberData> getRange(Specification<MemberData> spec, Rangeable rangeable);

//    findFirstN, findTopN: 처음 3개의 엔티티 반환
    List<MemberData> findFirst3ByNameLikeOrderByName(String name);
    Optional<MemberData> findFirstByNameLikeOrderByName(String name);
    MemberData findFirstByBlockedOrderById(boolean blocked);
}