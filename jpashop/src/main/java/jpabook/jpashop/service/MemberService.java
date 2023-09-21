package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기는 readOnly를 달면 리소스 최적화 가능
@RequiredArgsConstructor //final 들어간 객체 자동 주입
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional //개별적으로 특이한 거 하나에만 달아주고 다수의 것은 큰 클래스 범주에서 적용
    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers()
    {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional //끝나는 시점에 flush, 영속성 컨텍스트 commit
    public void update(Long id, String name) { //member를 반환해도 좋지만, 그러면 기능상 커맨드와 쿼리가 같이 있는 꼴이 됨 (member조회) / update는 가급적 끝내버리거나 id 반환정도 추천
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }

}
