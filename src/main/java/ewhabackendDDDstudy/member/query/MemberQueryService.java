package ewhabackendDDDstudy.member.query;

import ewhabackendDDDstudy.member.command.application.NoMemberException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberQueryService {
    private MemberDataDao memberDataDao;

    public MemberQueryService(MemberDataDao memberDataDao) {
        this.memberDataDao = memberDataDao;
    }

    public MemberData getMemberData(String memberId) {
        MemberData memberData = memberDataDao.findById(memberId);
        if (memberData == null) {
            throw new NoMemberException();
        }
        return memberData;
    }

    Pageable pageRequest = PageRequest.of(2, 3);
    Page<MemberData> page = memberDataDao.findByBlocked(false, pageRequest);
    List<MemberData> contet = page.getContent(); // 조회 결과 목록
    long totalElements = page.getTotalElements(); // 조건에 해당하는 전체 개수
    int totalPages = page.getTotalPages(); // 전체 페이지 번호
    int number = page.getNumber(); // 현재 페이지 번호
    int numberOfElements = page.getNumberOfElements(); // 조회 결과 개수
    int size = page.getSize(); // 페이지 크기
}
