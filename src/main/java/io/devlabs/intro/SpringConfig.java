package io.devlabs.intro;

import io.devlabs.intro.repository.MemberRepository;
import io.devlabs.intro.repository.MemoryMemberRepository;
import io.devlabs.intro.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean // 스프링빈을 등록
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
