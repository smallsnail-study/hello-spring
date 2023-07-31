package io.devlabs.intro.controller;

import io.devlabs.intro.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

// 스프링 컨테이너가 @Controller 애너테이션이 있으면 MemberController 객체를 생성해서 넣어두고 스프링이 관리한다. ->스프링 컨테이너에서 스프링 빈이 관리된다.
@Controller
public class MemberController {

    // 스프링이 관리하게 되면, 스프링컨테이너에 등록을 하고 컨테이너로부터 받아서 쓰도록 변경해야 한다.
    // 이유 : new MemberService로 하면 다른 여러 Controller들이 MemberService를 가져다 쓸 수 있게 된다.
    //기능이 많지 않으면 여러 개의 인스턴스를 생성할 필요 없이 1개를 생성하고 공유하면 된다.
    //-> 스프링컨테이너에 등록하고 사용하게 한다.
//    private final MemberService memberService = new MemberService();
    private final MemberService memberService;

    @Autowired // 스프링이 스프링컨테이너에 있는 memberService 가져와서 연결시켜준다. DI(Dependency Injection 의존관계 주입)
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
