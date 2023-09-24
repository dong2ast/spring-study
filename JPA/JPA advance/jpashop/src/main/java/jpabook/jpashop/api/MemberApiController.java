package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController //responsebody가 달려있는 어노테이션
@RequiredArgsConstructor //final이 붙은 필드의 생성자를 자동 생성
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList()); //dto객체의 리스트를 만듦
        //리스트로 json을 내보내면 확장성이 떨어지기 때문에
        return new Result(collect); //data태그에 감싸서 반환 , Allarg덕분에 data에 주입할 수 있는 생성자가 생김 List<MemberDto> = t

    }

    @Data
    @AllArgsConstructor
    static class MemberDto { //DTO가 기본적으로 엔티티에서 필요한 것은 넣고 필요 없는 것은 빼는식으로 정제해서 보내는 옵젝인듯
        private String name;
    }

    @Data
    @AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 만듦
    static class Result<T>{
        private T data; // List<MemberDto> data = new List<MemberDto>();
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member ) { //request body => json 데이터를 객체에 주입해줌
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName()); //DTO(request)를 통해 값을 받음

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id); //update한 엔티티 다시 조회
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());

    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest { //entity는 그 자체로만 사용하고 validation과 같은 것은 DTO내에서 해결 (요청이 들어오고 나가는 것은 절대 entity를 사용하지 않는다)
        private String name;
    }

    @Data //getter, setter, toString과 같은 기본적인 메서드 한번에 제공
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
