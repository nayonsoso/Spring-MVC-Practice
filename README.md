# Spring-MVC-Practice

---

Spring의 핵심 기능들을 공부하며 연습한 코드입니다.

### **목차**  
- Application.java
- controller
- dto
- exception
- log config
- filter, interceptor

---

### {project name} Application.java

```
@SpringBootApplication
public class WebSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSampleApplication.class, args);
    }

}
```

- `@SpringBootApplication`
  - 내부에 `@ComponentScan`이 있으므로 `@Component`류 어노테이션이 달린 클래스를 자동으로 빈으로 등록해줌
  - (basePackageClasses="") 옵션으로 스캔을 시작할 위치를 정해줄 수 있음
- `SpringApplication.run()`
  - 스프링 동작에 필요한 정보들을 생성해서 메모리에 올림

---
