package com.example.websample.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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