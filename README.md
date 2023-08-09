~~# Spring-MVC-Practice

---

Spring의 핵심 기능들을 공부하며 연습한 코드입니다.

### **목차**  
- Application.java
- controller
- dto
- exception
- validation
- log config
- filter, interceptor

---

### {project name} Application.java

```java
@SpringBootApplication
public class WebSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSampleApplication.class, args);
    }

}
```

- `@SpringBootApplication`
  - 내부에 `@ComponentScan`이 있으므로 `@Component`류 어노테이션이 달린 클래스를 자동으로 빈으로 등록해줌
  - `(basePackageClasses="")` 옵션으로 스캔을 시작할 위치를 정해줄 수 있음
- `SpringApplication.run()`
  - 스프링 동작에 필요한 정보들을 생성해서 메모리에 올림

---

### Validation

먼저 디펜던씨를 추가해야 함

`implementation 'org.springframework.boot:spring-boot-starter-validation'`

```java
@Getter
@Setter
@Component
public class ValidationDto {
    @NotNull(message = "아이디를 입력해주세요.")
    String id;

    @Size(min=8, message = "비밀번호는 최소 8자리여야 합니다.")
    String pwd;

    @Email(message = "올바른 이메일을 입력해주세요.")
    String email;
}
```

```java
@RestController
public class ValidationController {
    @PostMapping("/valid")
    public ValidationDto check(@RequestBody @Valid ValidationDto validationDto){
        return validationDto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println(e.getMessage());
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
```

유효하지 않은 값이 들어오면 `MethodArgumentNotValidException`이 발생함

따라서, 예외 처리로 `MethodArgumentNotValidException`을 받아줘야 함

cf. RestController를 사용하면 안에 @ResponseBody가 적용되어 있어서 객체를 자동으로 json 형태로 바꿔줌
