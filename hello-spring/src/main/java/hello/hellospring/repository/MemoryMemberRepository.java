package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
//3. 회원 리포지토리 메모리 구현체
public class MemoryMemberRepository implements MemberRepository{
    //회원 저장 키,벨류 매핑
    private static Map<Long, Member> store =new HashMap<>();//로컬 메모리에 저장
    //sequence: 0,1,2 순으로 키값 생성
    private static long sequence=0L;
    @Override
    public Member save(Member member) {
        //1. id 세팅
        member.setId(++sequence);
        //2. 스토어 저장
        store.put(member.getId(),member); //키:value값
        return member;

    }
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //null이어도 반환 가능하게 하는 메소드: Optional.ofNullable
    }
    //★★★저장소에 저장되어있는 값중에 전체를 탐색해서 찾는 방법
    @Override
    public Optional<Member> findByName(String name) {

        return  store.values().stream() //1.저장소의 값들을 스트림으로 변환
                .filter(member -> member.getName().equals(name)) //2. member의 이름과 완전히 일치하는지 필터
                .findAny(); //3. 조건을 만족하는 첫번째 멤버 찾기. 없으면 Optional을 반환
    }

    @Override
    public List<Member> findAll() {
        //store에 있는 멤버 각각 Map의 모든 값들을 ArrayList형태로 반환.
        return new ArrayList<>(store.values());
    }
    //store data를 모두 삭제하는 법
    public void clearStore() {
        store.clear();
    }
}