package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 1. mvc(파라미터가 없는 경우)
    // "/hello-changuk" 값이 입력되면 "hello"라는 뷰이름을 반환함.
    // 추후, "hello.html"을 뷰 리졸버가 찾아 템플릿엔진이
    // SSR(서버사이드 랜더링)을 한다.
    @GetMapping("/hello-caps")
    public String hello(Model model) {
        //model은 data라는 키값과 changuk!!이라는 내용으로 구성되어 있다.
        model.addAttribute("data", "CAPS!!");
        return "hello"; //ctrl+click은 해당 소스로 넘어감.
    }

    //2.MVC와 템플릿엔진
    //내장 tomcat 서버가 콘트롤러에 hello-mvc로 넣어줌. ★템플릿엔진이 viewResolver에게 던짐★
    @GetMapping("hello-mvc")
    //@RequestParam는"name"이란 키값으로 parameter 받아서  name이란 이름으로 모델에 넘기기
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template"; //view resolver가 이 값을 받아서 찾아서 html로 변환(tymeleaf엔진이 처리
    }

    @ResponseBody//★★★http의 body에 직접 넣는것★★★
    @GetMapping("hello-string")
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    //3.2 ★★★Json데이터 반환 방식(Json: key,value 한쌍의 데이터)★★★제일 자주 쓰임.
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //{"name":"request parameter"}의 객체 반환
    }

    //한 클래스내에 또다른 클래스-> static class
    static class Hello {
        private String name;

        //getter,setter 는 우클릭 Generate 클릭하면 생성가능
        //이러한 getter,setter를 넣는 방식을 property방식이라고 한다.
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    //1.(★★★Responsebody 형태로 그냥 넣음.(viewResolver가 안함)★★★)
    //2.HttpMessageConverter가 동작->jsonConverter(json이면),StringConverter(String이면) 가 웹 브라우저에 띄움
}