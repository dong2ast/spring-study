package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //보관소
    private static long sequence = 0L; //id생성기

    private static final MemberRepository instance = new MemberRepository(); //리턴값 생성 (싱글톤 인스턴스 생성, 어떤 객체에서 가져가도 값 동일)

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() { //아무나 생성하지 못하게 생성자를 private로 막음 (싱글톤 빈 역할)

    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
