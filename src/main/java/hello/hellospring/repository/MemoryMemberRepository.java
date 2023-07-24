package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    /**
     * Map의 key는 id, value는 Member로 저장
     * 동시성 문제가 고려되어 있지 않음, 실무에서는 공유되는 변수일 때는 ConcurrentHashMap으로 사용
     */
    private static Map<Long, Member> store = new HashMap<>();
    /**
     * sequence는 0,1,2... key값을 생성해준다.
     * 동시성 문제가 고려되어 있지 않음, 실무에서는 AtomicLong 사용 고려
     */
    private static long sequence = 0L;
    @Override

    public Member save(Member member) {
        // id값을 셋팅하고
        member.setId(++sequence);
        // store에 저장
        store.put(member.getId(), member);
        // 저장된 결과를 반환
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 결과값이 null인 경우 Optional로 감싸서 반환 -> Client에서 할 수 있는 방법이 있다.(차후 설명예정)
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        /**
         * member.getName()이 parameter로 넘어온 name과 일치하는지 확인 -> 같은경우에만 필터링, 찾으면 반환
         * findAny()는 하나라도 찾는 것
         * 값이 없으면 Optional에 null이 포함되어 반환
         */
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // store에 있는 memeber들이 반환된다.
        return new ArrayList<>(store.values());
    }
}
