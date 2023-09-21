package hello.core.member;
import hello.core.AppConfig;
import org.junit.jupiter.api.BeforeEach;
class MemberServiceTest {
    MemberService memberService;
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }
}