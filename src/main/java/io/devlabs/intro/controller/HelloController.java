package io.devlabs.intro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 정적 컨텐츠
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!"); // data 부분이 아래 hello.html의 data로 치환된다.
        return "hello"; // resources>templates>hello.html 찾아서 랜더링
    }

    // MVC 패턴 적용
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // API
    @GetMapping("hello-string")
    @ResponseBody // HTTP프로토콜에서 응답 body 부분에 직접 넣어주는 것을 의미
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // "hello spring"
    }

    // data를 내놓는 실제 많이 사용하는 API방식 -> 객체가 JSON 형식으로 변환된다.
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello(); // 객체생성
        hello.setName(name);
        return hello;
    }

    // JavaBean 규약, property 접근방식
    static class Hello {
        // private이라 외부에서 꺼낼 수 없다.
        private String name;

        // 라이브러리 등에서 사용하려면 아래와 같은 메서드(public) 등으로 접근해야 함
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

