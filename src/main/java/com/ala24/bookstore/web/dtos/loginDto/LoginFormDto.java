package com.ala24.bookstore.web.dtos.loginDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class LoginFormDto {

	@NotEmpty(message = "아이디는 필수입니다.")
	private String loginId;

	@NotEmpty(message = "비밀번호는 필수입니다.")
	private String password;

}