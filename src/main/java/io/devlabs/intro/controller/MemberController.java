package io.devlabs.intro.controller;

import io.devlabs.intro.domain.Member;
import io.devlabs.intro.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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


    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member); // 회원가입

        return "redirect:/"; // 홈 화면으로 redirect
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        // key: members에 memberList를 model에 담아서 전달
        model.addAttribute("members", members);
        // view template에 전달
        return "members/memberList";
    }
}
