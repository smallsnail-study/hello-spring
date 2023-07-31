package io.devlabs.intro.repository;

import io.devlabs.intro.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //회원을 저장

    // id,name으로 조회 시 null일경우 Optional로 감싸서 반환
    Optional<Member> findById(Long id); // id로 회원조회
    Optional<Member> findByName(String name); // name으로 조회
    List<Member> findAll(); // 모든회원 리스트 반환
}
