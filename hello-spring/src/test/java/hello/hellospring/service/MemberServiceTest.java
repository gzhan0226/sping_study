package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    //★★★의존성 주입 디자인 패턴(테스트의 용이성을 편하게함)★★★★
    //설명: MemberService 클래스가 MemoryMemberRepository에 "의존"한다.
    //의존성 주입은 이 의존 관계를 외부에서 설정하여, MemberService가 특정 구현에 종속되지 않도록 한다.
    //밑의 코드들은 의존성 주입들 중 생성자 주입이다.
    MemberService memberService;
    //clear해야하는 서비스클래스 객체밖에 없다. 따라서 memorymemberRepo클래스 객체생성
    MemoryMemberRepository memberRepository;
    //실행 전에 테스트끼리 같은 메모리 리포지토리를 사용하기위해 세팅한다.
    //+a) store가 static으로 선언되어 있으므로 같은 "메모리(data section)"를 공유한다.
    @BeforeEach
    public void beforeEach()
    {
        memberRepository=new MemoryMemberRepository();
        memberService=new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach()
    {
        memberRepository.clearStore();
    }

    //테스트 코드는 한글로 작성가능(테스트 기본 3가지 과정: given,when,then)
    @Test
    void 회원가입() {
        //given(주어진 상황 속)
        Member member = new Member();
        member.setName("hello");

        //when(이것을 실행했을 때,검증해야할 것)
        Long savedId = memberService.join(member);

        //then(결과(검증확인 구현))
        //리포지토리에 저장이 됐는지 확인.
        Member findmember = memberService.findOne(savedId).get();
        assertThat(member.getName()).isEqualTo(findmember.getName());//실제 이름이 리포의 이름과 같은지 테스트
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
/*      오류 확인 방법1...try catch문
        //when(중복상황이라면)
        memberService.join(member1);
        try {
            memberService.join(member2);
        }
        catch (IllegalStateException e)
        {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
 */
        //오류 확인 방법2...assertThrows(예외.class, 예외 발생 특정 코드 블록 로직)
        //()->: JAVA의 람다 표현식(함수형 프로그래밍)
        // 1. ():빈 매개변수,2. ->:매개변수와 본문 구분, 3.그뒤: 함수의 본문. 즉, { }안의 내용
        memberService.join(member1);
        //assertThrows의 사용은 함수의 동작에 중점을 두어 테스트하는 함수형 프로그래밍 스타일에 부합하다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}