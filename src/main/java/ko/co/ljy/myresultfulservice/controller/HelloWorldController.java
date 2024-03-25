package ko.co.ljy.myresultfulservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import ko.co.ljy.myresultfulservice.bean.HelloWorldBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloWorldController {

    private final MessageSource messageSource;

    //GET
    //URI - /hello-world
    //RequestMapping(value ="hello-world", Method = "GET")
    @GetMapping("/hello-world")
    public String helloWorld() {
        log.info("hello-world call");
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {

        return new HelloWorldBean("Hello World!");
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable("name") String name) {

        return new HelloWorldBean("Hello World "+ name);
    }

    @GetMapping("hello-world-internationalized")
    public String helloWorldInternationalized( HttpServletRequest request,
            @RequestHeader(name ="Accept-Language", required = false) Locale locale) {

        Enumeration<String> headers = request.getHeaderNames();
        Collections.list(headers).stream().forEach(name -> {
            Enumeration<String> values = request.getHeaders(name);
            Collections.list(values).stream().forEach(value -> System.out.println(name + "=" + value));
        });
        System.out.println("locale = " + Locale.getDefault());
        return messageSource.getMessage("greeting.message", null,locale);
    }
}


