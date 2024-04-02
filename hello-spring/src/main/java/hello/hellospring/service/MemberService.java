package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.*;


public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    //회원가입
    //1.중복이름이 아니면 추가
    public Long join(Member member)
    {
        //중복이름의 회원 허용x 함수 사용
        validateDuplicateMember(member);//1.중복 검증
        memberRepository.save(member); //2.회원 정보 저장(join)
        return member.getId(); //3.해당 회원정보 반환
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    //전체회원 조회
    public List<Member> findMembers()
    {
        return memberRepository.findAll(); //List로 반환
    }
    //한 멤버 조회
    public Optional<Member> findOne(Long memberId)
    {
        return memberRepository.findById(memberId);
    }
}