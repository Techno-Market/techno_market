package com.techno_market.techno_market.domain.user.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserForm {
    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String userName;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String nickName;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String password;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String email;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String name;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    private LocalDate birthDate;
}
