package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.*;

//2. 회원 리포지토리 인터페이스
//인터페이스 생성(아직 어떤 형태의 저장소를 쓸줄 모르니)
public interface MemberRepository {
    Member save(Member member); //멤버 저장
    Optional<Member> findById(Long id);//회원 조회
    Optional<Member> findByName(String name);//회원 조회
    List<Member> findAll();//모든 회원 리스트 반환
}
