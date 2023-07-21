package hello.hellospring.controller;

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
}
