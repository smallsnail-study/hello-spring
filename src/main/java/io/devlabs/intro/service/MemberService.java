package io.devlabs.intro.service;

import io.devlabs.intro.domain.Member;
import io.devlabs.intro.repository.MemberRepository;

import java.util.List;
import java.util.Optional;


public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) { // DI(Dependency Injection)
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원X
        /* Optional로 감싸면 Optional 안에 member 객체가 있는 것으로 Optional 안의 여러 메서드를 사용가능
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } ); */

        /*
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        memberRepository.save(member);
         */

        // findByName에 해당하는 로직이 있는 경우 메서드로 따로 만들어주는 것이 좋다.
        // ctrl + Alt + Shift + t -> Extract Method
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
