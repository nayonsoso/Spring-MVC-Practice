
Spring의 핵심 기능들을 공부하며 연습한 코드입니다.

### **🔍목차**  
- Application.java
- controller
- exception
- validation

---

### 📌{project name}Application.java

```java
@SpringBootApplication
public class WebSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSampleApplication.class, args);
    }

}
```
- 스프링 프로젝트를 생성하면 자동으로 생성되는 코드
- `@SpringBootApplication`
  - 내부에 `@ComponentScan`이 있으므로 `@Component`류 어노테이션이 달린 클래스를 자동으로 빈으로 등록해줌
  - `(basePackageClasses="")` 옵션으로 스캔을 시작할 위치를 정해줄 수 있음
- `SpringApplication.run()`
  - 스프링 동작에 필요한 정보들을 생성해서 메모리에 올림

---

### 📌 Controller

>**Controller/RestController의 차이**
  - Controller : 응답값이 기본적으로 **HTML**을 주도록 되어 있음 
  - RestController : 응답값으로 Rest API 요청에 대한 응답(= **JSON**)을 주도록 되어 있음 
  - BE/FE가 분리된 요즘은 거의 RestController을 사용함``

> **@PathVariable, @RequestParam 어노테이션**
  - `@PathVariable`은 <U>경로를 변수처럼 사용</U>하여 URL로 값을 넘겨주는 것
    - /order/1 형식의 URL에 매핑됨
    - `@GetMapping("order/{id}")`가 선언된 함수에서 `@PathVariable("id")`로 값을 가져올 수 있음 
    -  GetMapping의 중괄호 안의 변수명과 @Pathvariable 소괄호의 값이 같으면 소괄호 생략 가능
  - `@RequestParam`은 <U>경로에 쿼리문을 넣어</U> URL로 값을 넘겨주는 것
    - e.g /order?orderId=1 형식의 URL에 매핑됨
    - `@GetMapping("order")`가 선언된 함수에서 `@RequestParam("orderId")`로 값을 가져올 수 있음
    - 이때 PathVariable과 달리 Mapping 어노테이션에는 별다른 표시가 없다는 것 헷갈림 주의!
  - `@PathVariable`과 `@RequestParam`의 공통점
    - Get, Delete 방법에서 주로 사용함
    - required=false 옵션으로 필수 여부를 설정할 수 있음 (디폴트는 true이며, 이때 값이 안오면 에러 발생)
    - defaultValue=xx 옵션을 주어 기본값을 설정할 수 있음

```java
@RestController // 응답으로 json을 리턴
public class SampleController {
    @GetMapping(value = "/order/{orderId}")
    public String getOrderWithPathVariable(@PathVariable String orderId) {
        return "orderID:" + orderId;
    }

    @GetMapping(value = "/order") // Mapping 어노테이션에는 추가적인 표시 없음
    public String getOrderWithRequestPram(@RequestParam("orderId") String id,
                                          @RequestParam(name = "orderAmount", required = false, defaultValue = "1000") String amount) {
        log.info("Get same order : " + id);
        return "orderId:" + id + ", orderAmount:" + amount;
    }
}
```

> **@RequestHeader, @RequestBody 어노테이션**
  - HTTP의 헤더, 바디로부터 직접 값을 받는 방법
  - HTTP의 헤더나 바디에 있는 값을 객체로 자동 매핑해줌
  - 어노테이션을 생략해도 자동으로 매핑해주긴 하나, 명시하는게 좋음

```java
@RestController // 응답으로 json을 리턴
public class SampleController {
    @PostMapping("/order")
    public String createOrder(
            @RequestBody OrderCreateRequest orderCreateRequest,
            @RequestHeader String userAccountId
    ) {
      return "orderCreateRequest:" +orderCreateRequest+" userAccountId:"+userAccountId;
    }
}
```
아래 HTTP 요청과 매핑됨
```http request
POST http://localhost:8080/order
Content-Type: application/json
userAccountId: account888

{"orderId": "123", "orderAmount": 8500}
```

---

### 📌 Exception

> **ExceptionHandler**
- 컨트롤러 기반 예외 처리 방법
- 컨트롤러 안에서`@ExceptionHandler(예외.class)` 어노테이션을 쓴 함수로 컨트롤러 기반 예외 처리를 할 수 있음
- HTTP Status code를 변경하는 방법 
  - @ResponseStatus(value=HttpStatus.xx) 활용
  - ResponseEntity 활용
    - Status뿐 아니라 header, body 부분을 build 방법으로 직접 설정할 수 있음 
    - ResponseEntity는 Warpper로서, 개발자가 만든 커스텀 에러 객체를 wrapping하는 역할도 함
      - e.g. ResponseEntity<CustomError>                                                                                . >
    - 일반적으로 커스텀 에러 dto를 만들어서, 거기에 errorCode, errorMessage를 담게 함
    - errorCode는 보통 Enum으로 만들어 관리

> **RestControllerAdvice**
- 전역 예외 일괄 처리 방식
- GlobalExceptionHandler.class에 `@RestControllerAdvice`를 씀으로써 어플리케이션에서 발생하는 모든 예외를 처리할 수 있음
  - cf. 보통 전역 예외처리GlobalExceptionHandler 클래스의 이름을 'GlobalExceptionHandler'이라고 짓는다.
  - GlobalExceptionHandler안의 메소드에 `@ExceptionHandler(예외.class)`를 적어 예외를 처리한다.
- 스프링 백엔드 개발에서 현재 가장 많이 활용되는 기술 (일관적인 예외 및 응답처리) 

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        log.error("Exception is occured");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("400","BAD_REQUEST is occured."));
        //
    }
}

```

---

### Validation

먼저 gradle에 dependency를 추가해야 함

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
  public ValidationDto check(@RequestBody @Valid Request request){
    ValidationDto validationDto = (ValidationDto) request.getAttribute("validationDto");
    return validationDto;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    System.out.println(e.getMessage());
    // ResponseEntity에 빌드 방법으로 상태, 헤더, 바디를 설정하는 부분
    return ResponseEntity.status(HttpStatus.BAD_REQUEST) 
            .header("newHeader", "Some Value")
            .body(new ErrorResponse("INVALID_ACCESS",
                    "IllegalAccessException is occured."));
  }
}
```

유효하지 않은 값이 들어오면 `MethodArgumentNotValidException`이 발생함

따라서, 예외 처리로 `MethodArgumentNotValidException`을 받아줘야 함

cf. RestController를 사용하면 안에 @ResponseBody가 적용되어 있어서 반환하는 객체를 자동으로 json 형태로 바꿔줌

cf. ResponseEntity에 빌드 방식으로 HTTP 상태, 헤더, 바디를 설정할 수 있음
