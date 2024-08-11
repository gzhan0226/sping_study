package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController //Rest API 스타일로 만드는 것(@ResponseBody: data를 json형태로 보냄)
@RequiredArgsConstructor
public class MemberApiController {
    /**
     * 회원 등록 api
     */
    private final MemberService memberService;
    //외부에 직접 노출
    @PostMapping("/api/v1/members")
    //@RequestBody: json으로 온 body를 멤버에에 그대로 매핑해서 넣어줌
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member)
    {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    //Dto로 노출
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request)
    {
        Member member=new Member();
        member.setName(request.name);

        Long id=memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //DTO 생성
    @Data
    static class CreateMemberRequest
    {
        //controller에서 @valid Member member 실행시 NotEmpty하게
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse
    {
        private Long id;
        //생성자
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @PatchMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request)
    {
        memberService.update(id,request.getName()); //id로 조회하고, 이름을 수정
        Member findMember=memberService.findOne(id); //수정한 행의 id를 불러옴
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
    //업데이트 용 request DTO
    @Data
    static class UpdateMemberRequest
    {
        private String name; // 이름 수정 요청
    }
    //업데이트용 응답 DTO
    @Data
    @AllArgsConstructor //롬복으로 둘의 값을 setting하는 생성자 생성
    static class UpdateMemberResponse
    {   private Long id; //아이디를 받아서
        private String name;
    }

    @GetMapping("api/v2/members")
    public Result memberV2()
    {
        List<Member> findMembers=memberService.findAllmembers();
        //엔티티 -> DTO 변환
        //조회된 회원 목록을 스트림으로 변환한 후 map() 메서드를 사용하여 각 회원을 MemberDto 객체로 변환.
        //변환된 객체들은 collect() 메서드를 사용하여 리스트로 수집함.
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(),collect);
    }

    //GDTO
    @Data
    @AllArgsConstructor
// 요청 결과를 담는 클래스로, 조회된 회원 수와 데이터를 포함한다.
    static class Result<T>
    {
        private int count;
        private T data; // 향후 필요한 필드(데이터)를 추가할 수 있다.
    }

    @Data
    @AllArgsConstructor
    static class MemberDto //이름만 조회
    {
        private String name;
    }
}
