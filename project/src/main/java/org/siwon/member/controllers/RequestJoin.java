package org.siwon.member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestJoin { // 커맨드 객체 구성
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min=8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String userName;

    @NotBlank
    private String mobile;

    @AssertTrue // 필수항목 지정 true가 되어야만 통과된다.
    private boolean agree;
}
