package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


public class MemoryMemberRepositoryTest {
    //1. 인터페이스 객체 생성(아직 어떤 형태의 저장소를 쓸줄 모르니)
    MemoryMemberRepository repository=new MemoryMemberRepository();
    //2. @AfterEach: 각 함수가 끝난후에 실행되는 콜백 메서드를 지정.
    //이를 통해 테스트 후 DB에 남은 데이터를 삭제한다.
    @AfterEach
    public void afterEach()
    {
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member=new Member();
        member.setName("spring");

        repository.save(member);
        //repository.findById(member.getId()): Opitonal형태로 반환 하므로, 여기에 "get()"을 해야 "실제 값"이 반환.
        Member result=repository.findById(member.getId()).get();
        //3.1 ★Assertions.assertEquals(기대,실제): Junit의 테스트 방법 중 하나. 둘이랑 같은지 확인.
        //Assertions.assertEquals(member,result);
        //3.2.★★assert(실제).isEqualTo(기대): 이문장이 가독성이 더 좋아 자주 사용된다.
        assertThat(result).isEqualTo(member);
    }
    @Test
    public void findByname()
    {
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        //★★★shift+F6키 누르면 같은 이름 한방에 변경 가능.★★★
        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);


        //Member result= repository.findByName(member1.getName()).get();
        Member result= repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }
    @Test
    public void findAll()
    {
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);


        Member member2=new Member();
        member2.setName("spring1");
        repository.save(member2);

        List<Member> result=repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }

}