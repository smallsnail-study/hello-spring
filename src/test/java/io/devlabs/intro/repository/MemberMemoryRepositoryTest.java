package io.devlabs.intro.repository;

import io.devlabs.intro.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

// Test는 각 기능별로도 할 수 있고 class 전체로 실행할 수도 있다.
// 다른곳에서 클래스를 사용하지 않기 때문에 public 없이 사용
class MemberMemoryRepositoryTest {

//    MemoryMemberRepository repository = new MemoryMemberRepository();
//
//    @AfterEach
//    public void afterEach() {
//        repository.clearStore();
//    }
//
//    // 기능 동작 테스트
//    @Test
//    public void save() {
//        // given
//        Member member = new Member();
//        member.setName("spring");
//
//        // when
//        repository.save(member);
//
//        // then
//        Member result = repository.findById(member.getId()).get();
//
//        /* 저장했던 member와 result가 일치하는지 검증
//            Assertions.assertThat(member).isEqualTo(result);
//            Assertions를 Add on demand static import 를 통해 아래와 같이 간단하게 사용할 수 있다.(Alt+Enter) */
//        assertThat(member).isEqualTo(result);
//    }
//
//    @Test
//    public void findByName() {
//        // given
//        Member member1 = new Member();
//        member1.setName("spring1");
//        repository.save(member1);
//
//        // 위에서 복사 후 shift + F6으로 member1을 member2로 한번에 rename 가능
//        Member member2 = new Member();
//        member2.setName("spring2");
//        repository.save(member2);
//
//        // when
//        // ctrl+Alt+v 변수추출
//        Member result = repository.findByName("spring1").get();
//
//        //then
//        assertThat(result).isEqualTo(member1);
//    }
//
//    @Test
//    public void findAll() {
//        //given
//        Member member1 = new Member();
//        member1.setName("spring1");
//        repository.save(member1);
//
//
//        Member member2 = new Member();
//        member2.setName("spring2");
//        repository.save(member2);
//
//        //when
//        List<Member> result = repository.findAll();
//
//        //then
//        assertThat(result.size()).isEqualTo(2);
//    }


        MemoryMemberRepository memberRepository = new MemoryMemberRepository();

        @Test
        @DisplayName("Member 생성")
        public void createMember() {
            // given
            Member member = new Member();
            member.setName("Member01");

            // when
            Member createdMember = this.memberRepository.save(member);

            // then
            Assertions.assertThat(createdMember.getId()).isEqualTo(member.getId());
            Assertions.assertThat(createdMember.getName()).isEqualTo(member.getName());
        }

        @Test
        @DisplayName("Id로 Member 조회")
        public void findMemberById() {
            // given
            Member member01 = saveMember("Member01");
            Member member02 = saveMember("Member02");
            Member member03 = saveMember("Member03");

            // when
            Member foundMember = this.memberRepository.findById(member02.getId()).get();

            // then
            Assertions.assertThat(foundMember.getId()).isEqualTo(member02.getId());
            Assertions.assertThat(foundMember.getName()).isEqualTo(member02.getName());
        }

        @Test
        @DisplayName("Id로 존재하지 않는 Member 조회")
        public void findNotExistsMemberByIdIsNull() {
            // given
            Member member = saveMember("Member01");

            // when
            boolean isExists = this.memberRepository.findById(member.getId() + 1).isPresent();

            // then
            Assertions.assertThat(isExists).isFalse();
        }

        @Test
        @DisplayName("이름으로 Member 조회")
        public void findMemberByName() {
            // given
            Member member01 = saveMember("Member01");
            Member member02 = saveMember("Member02");
            Member member03 = saveMember("Member03");

            // when
            Member foundMember = this.memberRepository.findByName(member02.getName()).get();

            // then
            Assertions.assertThat(foundMember.getId()).isEqualTo(member02.getId());
            Assertions.assertThat(foundMember.getName()).isEqualTo(member02.getName());
        }

        @Test
        @DisplayName("이름으로 존재하지 않는 Member 조회")
        public void findNotExistsMemberByNameIsNull() {
            // given
            Member member = saveMember("Member01");

            // when
            boolean isExists = this.memberRepository.findByName("invalidName").isPresent();

            // then
            Assertions.assertThat(isExists).isFalse();
        }

        @Test
        @DisplayName("Member 전체 리스트 조회")
        public void findAll() {
            // given
            Member member01 = saveMember("Member01");
            Member member02 = saveMember("Member02");
            Member member03 = saveMember("Member03");

            // when
            List<Member> members = this.memberRepository.findAll();

            // then
            Assertions.assertThat(members.size()).isEqualTo(3);
            Assertions.assertThat(members.contains(member01)).isTrue();
            Assertions.assertThat(members.contains(member02)).isTrue();
            Assertions.assertThat(members.contains(member03)).isTrue();
        }

        @Test()
        @DisplayName("멀티쓰레드로 동시에 다수 Member를 생성한 경우")
        public void createMemberWithConcurrency() throws InterruptedException {
            // given
            int numberOfThreads = 2048;
            ExecutorService service = Executors.newFixedThreadPool(numberOfThreads / 8);
            CountDownLatch latch = new CountDownLatch(numberOfThreads);

            // when
            for (int i = 0; i < numberOfThreads; i++) {
                int sequence = i;
                service.submit(() -> {
                    saveMember("Member" + sequence);
                    latch.countDown();
                });
            }
            latch.await();

            List<Member> members = this.memberRepository.findAll();
            Member lastMember = members.get(members.size() - 1);

            // then
            Assertions.assertThat(lastMember.getId()).isEqualTo(numberOfThreads);
            Assertions.assertThat(members.size()).isEqualTo(numberOfThreads);
        }

        private Member saveMember(String name) {
            Member member = new Member();
            member.setName(name);

            return this.memberRepository.save(member);
        }
}
